package cn.onearth.test;

import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.model.pojo.BookSection;
import cn.onearth.fmzs.service.core.StaticPageService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wliu on 2017/12/14 0014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class StaticPageTest {

    @Autowired
    private StaticPageService staticPageService;

    @org.junit.Test
    public void indexTest() {

        Map<String, Object> rootMap = new HashMap<>();

        Book book = new Book();
        book.setId(102);
        book.setName("大唐风华路");

        BookSection bookSection = new BookSection();
        bookSection.setBookId(102);
        bookSection.setId(84);
        bookSection.setSequenceSite(655);
        bookSection.setSectionName("655-长孙皇后出手，直接吊死禄东赞");
        bookSection.setSectionContent("测试内容");


        rootMap.put("book",book);

        rootMap.put("section", bookSection);

        rootMap.put("lastSection","#");
        rootMap.put("nextSection","#");


        staticPageService.index(rootMap);

    }

}
