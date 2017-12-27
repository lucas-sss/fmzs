package cn.onearth.test;

import cn.onearth.fmzs.utils.HttpClientUtil;
import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.model.business.SectionsDrawerDO;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.service.core.CoreService;
import cn.onearth.fmzs.service.taskjob.TracerTaskJob;
import cn.onearth.fmzs.spider.processor.SpiderProcessor;
import cn.onearth.fmzs.spider.service.impl.N31xsTracerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by wliu on 2017/11/29 0029.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class CoreTest {

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private SpiderProcessor processor;

    @Autowired
    private N31xsTracerService n31xsTracerService;
    @Autowired
    private TracerTaskJob tracerTaskJob;

    @Autowired
    private CoreService coreService;

    @Test
    public void forkJoinTest() {

        String bookName = "大唐风华路";
        String rootPath = n31xsTracerService.getBookRootPath(bookName);
        if (null == rootPath) return;
        System.out.println("");
        System.out.println(rootPath);

        Book bookInfo = new Book();
        bookInfo.setName(bookName);
        bookInfo.setRootPath(rootPath);

        SectionsDrawerDO allSection = n31xsTracerService.getAllSection(bookInfo);
        long time1 = System.currentTimeMillis();
//        SectionsDrawerDO allSection = new SectionsDrawerDO();
//
//        SectionContextDO section1 = new SectionContextDO();
//        section1.setSectionName("621-再见你时，生死之敌");
//        section1.setSectionPath("http://www.31xs.net/7/7792/9278635.html");
//        section1.setIndex(621);
//        SectionContextDO section2 = new SectionContextDO();
//        section2.setSectionName("622-打到草原去，灭亡西突厥");
//        section2.setSectionPath("http://www.31xs.net/7/7792/9280707.html");
//        section2.setIndex(622);
//        SectionContextDO section3 = new SectionContextDO();
//        section3.setSectionName("623-个头超过车轮者，一律斩杀？");
//        section3.setSectionPath("http://www.31xs.net/7/7792/9280970.html");
//        section3.setIndex(623);
//
//        allSection.addSection(section1);
//        allSection.addSection(section2);
//        allSection.addSection(section3);

        List<SectionContextDO> list = coreService.collectNewBook(allSection);
        long time2 = System.currentTimeMillis();
        System.out.println("时间2：" + System.currentTimeMillis());
        for (SectionContextDO sectionContextDO : list) {
            System.out.println("");
            System.out.println("章节：" + sectionContextDO.getSectionPath());
            System.out.println(sectionContextDO.getSectionContext().substring(0,20));
        }

        System.out.println("主程序结束,耗时：" + (time2 - time1));
    }


}
