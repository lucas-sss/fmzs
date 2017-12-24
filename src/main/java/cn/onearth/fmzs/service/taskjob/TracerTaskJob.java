package cn.onearth.fmzs.service.taskjob;

import cn.onearth.fmzs.model.business.PushBean;
import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.model.pojo.BookSection;
import cn.onearth.fmzs.model.pojo.TracerTask;
import cn.onearth.fmzs.service.basic.BookSectionService;
import cn.onearth.fmzs.service.basic.BookService;
import cn.onearth.fmzs.service.basic.TracerService;
import cn.onearth.fmzs.service.core.StaticPageService;
import cn.onearth.fmzs.service.push.impl.DDAlarmService;
import cn.onearth.fmzs.spider.service.BasicTracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 追书任务，半小时执行一次
 * <p>
 * Created by wliu on 2017/11/28 0028.
 */
@Component(value = "tracerTaskJob")
public class TracerTaskJob {

    //任务集合
    private Set<TracerTask> syncSet = Collections.synchronizedSet(new HashSet<TracerTask>());

    @Autowired
    @Qualifier(value = "n31xsTracerService")
    private BasicTracer basicTracer;

    @Autowired
    private DDAlarmService ddAlarmService;
    @Autowired
    private StaticPageService staticPageService;
    @Autowired
    BookSectionService bookSectionService;
    @Autowired
    private TracerService tracerService;
    @Autowired
    private BookService bookService;


    @PostConstruct
    public void init() {
        List<TracerTask> tasks = tracerService.selectAll();
        if (tasks != null && tasks.size() > 0) {
            for (TracerTask task : tasks) {
                this.syncSet.add(task);
            }
        }
    }


    public void getTracerTask() {
        if (this.syncSet.size() == 0) {
            return;
        }
        //去数据库查询任务的书籍名称和根路径
        List<Book> books = new ArrayList<>();
        for (TracerTask tracerTask : syncSet) {
            Book book = bookService.getBookById(tracerTask.getBookId());
            books.add(book);
        }

        /**
         * 后面考虑拆分为多线程，因为相关处理信息太多
         */
        for (Book book : books) {
            TreeSet<SectionContextDO> newSections = basicTracer.traceLatestSection(book);

            SectionContextDO last = newSections.last();

            Integer loacalLatestSection = bookSectionService.getLocalLatestSection(book.getId());
            loacalLatestSection = loacalLatestSection == null ? 0 : loacalLatestSection;
            for (SectionContextDO newSection : newSections) {
                //和本地最新章节比较
                if (newSection.getIndex() > loacalLatestSection) {
                    basicTracer.pullSectionContext(newSection);

                    /**
                     * 保存数据到数据库
                     */
                    BookSection insert = new BookSection();
                    insert.setBookId(book.getId());
                    insert.setSectionName(newSection.getSectionName());
                    insert.setSectionPath(newSection.getSectionPath());
                    insert.setSequenceSite(newSection.getIndex());
                    insert.setSectionContent(newSection.getSectionContext());
                    bookSectionService.saveBookSection(insert);

                    /**
                     * 构建静态化需要的数据
                     */
                    Map<String, Object> rootMap = new HashMap<>();
                    rootMap.put("book", book);

                    BookSection bookSection = new BookSection();
                    bookSection.setBookId(book.getId());
                    bookSection.setId(insert.getId());
                    bookSection.setSequenceSite(insert.getSequenceSite());
                    bookSection.setSectionName(insert.getSectionName());
                    bookSection.setSectionContent(insert.getSectionContent());

                    rootMap.put("section", bookSection);
                    //当前章节位置的名称
                    rootMap.put("current", book.getId() + "_" + insert.getSequenceSite());
                    String localPath = staticPageService.index(rootMap);
                    insert.setLocalPath(localPath);
                    //更新数据库中对章节静态化后的真实路径
                    bookSectionService.updateLocalPath(insert);
                    /**
                     * 这里要更新上一章节的信息（下一章节）
                     */
                    BookSection findSection = bookSectionService.getBookSectionByBookAndSite(book.getId(), newSection.getIndex() - 1);
                    if (findSection != null){
                        findSection.setNextSectionId(insert.getSequenceSite());
                        bookSectionService.updateSectionById(findSection);
                    }

                    /**
                     * 对数据进行更新推送
                     */
                    PushBean pushBean = new PushBean();
                    pushBean.setTitle("更新：" + book.getName());
                    pushBean.setText(newSection.getSectionName());
                    String link = "http://120.55.63.232/page/" + book.getId() + "/" + insert.getSequenceSite();
//                    pushBean.setLink(newSection.getSectionPath());
                    pushBean.setLink(link);
                    pushBean.setPicLink(book.getFaceImage());
                    //对每一个更新章节进行推送
                    boolean flag = ddAlarmService.shareLink(pushBean);
                    if (!flag) {
                        //如果推送失败
                    }
                }
            }
            /**
             * 对本地书籍最新章节进行更新, 已经改为触发器完成
             */
//            book.setLasterSection(last.getSectionName());
//            String readPath = "/page/" + book.getId() + "/" + last.getIndex();
//            book.setLasterPath(readPath);
//            bookService.updateBook(book);
        }
    }
}
