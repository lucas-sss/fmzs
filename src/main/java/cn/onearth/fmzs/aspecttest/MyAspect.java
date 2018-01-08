package cn.onearth.fmzs.aspecttest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2018/1/3 0003
 */
@Component("myAspect")
@Aspect
public class MyAspect {

    private static final Logger LOGGER = Logger.getLogger(MyAspect.class);

    @Pointcut("@annotation(cn.onearth.fmzs.aspecttest.SysServiceLog)")
    private void myPointcut() {
    }

    @Before("myPointcut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("============================================================");
        System.out.println("-------------前置通知。。。。。");
        LOGGER.info("-------------前置通知。。。。。");
    }

}
