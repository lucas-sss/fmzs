package cn.onearth.fmzs.aop;

import java.lang.annotation.*;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2018/1/13 0013
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SendSmsNotice {

    public BizTypeEnum bizType() default BizTypeEnum.AUDIT_ENT;

    /**
     * 操作类型
     * @return
     */
    public OpTypeEnum opType() default OpTypeEnum.AUDIT_SUCCESS;
}
