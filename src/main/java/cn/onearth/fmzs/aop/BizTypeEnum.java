package cn.onearth.fmzs.aop;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2018/1/13 0013
 */
public enum BizTypeEnum {

    AUDIT_ENT(200, "审核企业");

    private int code;

    private String info;

    BizTypeEnum(int code, String info){
        this.code = code;
        this.info = info;
    }

    public int getCode(){
        return this.code;
    }
    public String getInfo(){
        return this.info;
    }
}
