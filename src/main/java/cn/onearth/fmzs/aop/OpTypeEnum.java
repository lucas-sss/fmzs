package cn.onearth.fmzs.aop;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2018/1/13 0013
 */
public enum OpTypeEnum {

    AUDIT_SUCCESS("审核通过");

    private String info;

    OpTypeEnum(String info){
        this.info = info;
    }

    public String getInfo(){
        return this.info;
    }
}
