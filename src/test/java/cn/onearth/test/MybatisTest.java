package cn.onearth.test;

import cn.onearth.fmzs.model.pojo.*;
import cn.onearth.fmzs.service.basic.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by wliu on 2017/11/18 0018.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class MybatisTest {

    @Autowired
    BookService bookService;

    @Autowired
    PersonService personService;


    @Autowired
    BookSectionService bookSectionService;

    @Autowired
    private DDAlarmGroupService ddAlarmGroupService;

    @Autowired
    private RobotConfigService robotConfigService;

    @Test
    public void robotConfigTest(){

        RobotConfig configByBook = robotConfigService.getConfigByBook(102);
        System.out.println(configByBook);

    }


    @Test
    public void ddalarmTest(){

        List<DDAlarmGroup> list = ddAlarmGroupService.getAlramGroup(102);

        if (list.size() > 0){
            System.out.println(list.get(0).toString());
        }
        System.out.println("123");
    }

    @Test
    public void bookNameLikeTest(){

        List<Book> bookByNamelike = bookService.getBookByNamelike("大唐");
        if (bookByNamelike.size() > 0){
            System.out.println(bookByNamelike.get(0).toString());
        }
        System.out.println("123");
    }





    @Test
    public void lastSectionTest(){

        Integer localLatestSection = bookSectionService.getLocalLatestSection(111);

        System.out.println(localLatestSection);
    }



    @Test
    public void sectionTest(){

        List<BookSection> allSectionByBook = bookSectionService.getAllSectionByBook(102);

        for (BookSection bookSection : allSectionByBook) {
            System.out.println(bookSection);
        }

        /*BookSection bookSectionByBookAndSite = bookSectionService.getBookSectionByBookAndSite(102, 655);
        System.out.println(bookSectionByBookAndSite);*/

        /*BookSection bookSection = new BookSection();
        bookSection.setBookId(102);
        bookSection.setSequenceSite(668);
        bookSection.setSectionName("668");
        bookSection.setSectionPath("668");
        bookSection.setSectionContent("测试内容");

        bookSectionService.saveBookSection(bookSection);
        Integer id = bookSection.getId();
        System.out.println(id);*/
    }


    @Test
    public void bookTest(){

//        Book book = bookService.getBookById(1);

//        List<Book> list = bookService.getBookByNamelike("主宰");
//        Book book = list.get(0);
//        System.out.printf(book.toString());


        Person person = personService.getPersonByName("lucas");
//        Person person = personService.getPersonById(4);

        System.out.printf(person.toString());



    }



}
