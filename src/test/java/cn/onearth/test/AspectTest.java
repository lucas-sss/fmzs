package cn.onearth.test;

import cn.onearth.fmzs.aspecttest.ConsumeService;
import cn.onearth.fmzs.model.pojo.BookSection;
import cn.onearth.fmzs.service.basic.BookSectionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2018/1/3 0003
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class AspectTest {

    @Autowired
    private ConsumeService consumeService;

    @Autowired
    private BookSectionService bookSectionService;

    @Test
    public void aaTest(){

        consumeService.save();

    }


    @Test
    public void ssTest(){

        BookSection bookSection = new BookSection();

        bookSection.setBookId(102);
        bookSection.setSectionName("aop测试");
        bookSection.setSectionPath("http://xxxx/page/3242.html");
        bookSection.setSequenceSite(111);

        bookSectionService.saveBookSection(bookSection);

    }



}
