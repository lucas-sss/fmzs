package cn.onearth.fmzs.spider.service;

import cn.onearth.fmzs.model.business.LatestBookInfoDO;
import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.model.business.SectionsDrawerDO;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.spider.processor.SpiderProcessor;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by wliu on 2017/11/26 0026.
 */
public interface BasicTracer {


    LatestBookInfoDO traceLatestBookInfo(SpiderProcessor processor, String bookName);

    /**
     * 获取最新的章节
     *
     * @param book
     * @return
     */
    TreeSet<SectionContextDO> traceLatestSection(Book book);

    /**
     * 获取所有章节链接
     *
     * @param book
     * @return
     */
    SectionsDrawerDO getAllSection(Book book);

    SectionContextDO pullSectionContext(SectionContextDO sectionContext);

    /**
     * 获取书籍根路径
     *
     * @param bookName
     * @return
     */
    String getBookRootPath(String bookName);



    Book collectBookInfo(String bookName);
}
