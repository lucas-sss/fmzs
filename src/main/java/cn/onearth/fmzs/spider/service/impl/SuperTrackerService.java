package cn.onearth.fmzs.spider.service.impl;

import cn.onearth.fmzs.model.business.LatestBookInfoDO;
import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.model.business.SectionsDrawerDO;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.spider.processor.SpiderProcessor;
import cn.onearth.fmzs.spider.service.BasicTracer;

import java.util.TreeSet;

/**
 * 超级内容抓取服务，不在按照某一固定网站爬取
 *
 * Created by wliu on 2017/12/18 0018.
 * 3.0版本后开始
 */
public class SuperTrackerService implements BasicTracer {
    @Override
    public LatestBookInfoDO traceLatestBookInfo(SpiderProcessor processor, String bookName) {
        return null;
    }

    @Override
    public TreeSet<SectionContextDO> traceLatestSection(Book book) {
        return null;
    }

    @Override
    public SectionsDrawerDO getAllSection(Book book) {
        return null;
    }

    @Override
    public SectionContextDO pullSectionContext(SectionContextDO sectionContext) {
        return null;
    }

    @Override
    public String getBookRootPath(String bookName) {
        return null;
    }

    @Override
    public Book collectBookInfo(String bookName) {
        return null;
    }
}
