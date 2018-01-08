package cn.onearth.fmzs.aspecttest;

import java.lang.annotation.*;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2018/1/3 0003
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysServiceLog {
}
