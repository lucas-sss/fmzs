package cn.onearth.test;

import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.model.business.SectionsDrawerDO;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.spider.service.impl.NBiqugeTracerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.TreeSet;

/**
 * Created by wliu on 2017/12/10 0010.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class BiqugeTest {

    @Autowired
    private NBiqugeTracerService nBiqugeTracerService;


    @Test
    public void newSectionTest() {
        String rootPath = nBiqugeTracerService.getBookRootPath("大唐风华路");
        Book book = new Book();
        book.setRootPath(rootPath);
        TreeSet<SectionContextDO> newSection = nBiqugeTracerService.traceLatestSection(book);
        for (SectionContextDO section : newSection) {
            nBiqugeTracerService.pullSectionContext(section);
            System.out.println(section.getSectionContext());
        }

    }


    @Test
    public void allSectionTest() {
        String rootPath = nBiqugeTracerService.getBookRootPath("大唐风华路");
        Book book = new Book();
        book.setRootPath(rootPath);
        SectionsDrawerDO allSection = nBiqugeTracerService.getAllSection(book);
        TreeSet<SectionContextDO> sections = allSection.getSections();
        for (SectionContextDO section : sections) {
            System.out.println(section.getSectionName());
        }

    }

    @Test
    public void tt() {

        String rootPath = nBiqugeTracerService.getBookRootPath("大唐风华路");
        System.out.println(rootPath);


    }

}
