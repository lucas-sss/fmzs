package cn.onearth.fmzs.aop;

import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.model.pojo.BookSection;
import cn.onearth.fmzs.service.basic.BookSectionService;
import cn.onearth.fmzs.service.basic.BookService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2018/1/13 0013
 */
@Aspect
@Component("smsEmailAspect")
public class SmsEmailAspect {


    @Autowired
    private BookService bookService;

    @Pointcut("@annotation(cn.onearth.fmzs.aop.SendSmsNotice)")
    private void smsPointcut(){}

    @AfterReturning(value = "smsPointcut()", returning = "returnVal")
    public void AfterReturning(JoinPoint joinPoint, Object returnVal){

        Object[] args = joinPoint.getArgs();
        BookSection bookSection = null;
        for (int i = 0; i < args.length; i++){
            if (args[i] instanceof BookSection){
                bookSection = (BookSection)args[i];
                break;
            }
        }
        Book book = bookService.getBookById(bookSection.getBookId());
        System.out.println("--------------------------------------");
        System.out.println(book);

    }
}
