package cn.onearth.fmzs.model.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by wliu on 2017/11/26 0026.
 */
@Data
@AllArgsConstructor
public class SectionContextDO implements Comparable {

    /**
     * 章节的路径
     */
    private String sectionPath;

    /**
     * 152-不留痕迹 这种形式存储
     */
    private String sectionName;

    //章节所在的位置
    private int index;

    /**
     * 章节的内容
     */
    private String sectionContext;

    public SectionContextDO() {
    }

    public SectionContextDO(String sectionPath, String sectionName) {
        this.sectionPath = sectionPath;
        this.sectionName = sectionName;
    }

    @Override
    public int hashCode() {
        int result = sectionPath != null ? sectionPath.hashCode() : 0;
        result = 31 * result + (sectionName != null ? sectionName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj){

        if (null == obj){
            return false;
        }

        if (obj == this){
            return true;
        }
        if (obj.getClass() != SectionContextDO.class){
            return false;
        }
        SectionContextDO section = (SectionContextDO)obj;

        if (StringUtils.equals(this.getSectionName(), section.getSectionName()) && StringUtils.equals(this.getSectionPath(), section.getSectionPath())){
            return true;
        }
        return false;
    }


    @Override
    public int compareTo(Object o) {
        SectionContextDO section = (SectionContextDO)o;
        int a = Integer.valueOf(this.getSectionName().split("-")[0]);
        int b = Integer.valueOf(section.getSectionName().split("-")[0]);
        if (a > b){
            return 1;
        }else if (a < b){
            return -1;
        }else {
            return 0;
        }
    }

    @Override
    public String toString(){
        return "章节名称：" + this.getSectionName() + ",url：" + this.getSectionPath();
    }
}
