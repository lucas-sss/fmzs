package cn.onearth.fmzs.log;

import cn.onearth.fmzs.log.annotation.SystemServiceLog;
import cn.onearth.fmzs.model.pojo.Person;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * 自定义日志切面
 *
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2018/1/3 0003
 */
@Aspect
@Component("systemLogAspect")
public class SystemLogAspect {

    private static final Logger LOGGER = Logger.getLogger(SystemLogAspect.class);

    @Pointcut("@annotation(cn.onearth.fmzs.log.annotation.SystemServiceLog)")
    private void serviceAspecrt() {
    }

    @Pointcut("@annotation(cn.onearth.fmzs.log.annotation.SystemControllerLog)")
    private void controllerAspect() {
    }


    @Before("serviceAspecrt()")
    public void doBefore(JoinPoint joinPoint) {

        LOGGER.info("-------------前置通知。。。。。");
        System.out.println("------------------------进入前置通知了---------------");
    }

    @AfterThrowing(pointcut = "serviceAspecrt()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e){
        LOGGER.info("----------------进入异常通知切面了--------------------------------------------");
        //通过session获取用户信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Person user = (Person)session.getAttribute("user");
        String userName = "无登录状态";
        if (user != null){
            userName = user.getUserName();
        }
        //获取ip地址
        String remoteAddr = request.getRemoteAddr();
        String methodSig = joinPoint.getSignature().getName();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        try {
            Method method = joinPoint.getTarget().getClass().getMethod(methodSig, parameterTypes);
            String description = method.getAnnotation(SystemServiceLog.class).description();
            System.out.println(userName + ":用户, ip: "+ remoteAddr +",操作：" + description + ",出现异常, errorMsg: " + e.getMessage());
            LOGGER.warn(userName + ":用户, ip: "+ remoteAddr +",操作：" + description + ",出现异常, errorMsg: " + e.getMessage());
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        }
    }
}
