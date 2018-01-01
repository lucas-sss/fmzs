package cn.onearth.test;

import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.model.business.SectionsDrawerDO;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.spider.service.impl.N31xsTracerService;
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
public class N31xsTest {

    @Autowired
    private N31xsTracerService n31xsTracerService;


    @Test
    public void collectBookInfoTest(){
        String name = "大主宰";
        Book book = n31xsTracerService.collectBookInfo(name);
        if (book != null){
            System.out.println(book);
        }else {
            System.out.println("没有找到数据");
        }
    }



}
