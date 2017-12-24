package cn.onearth.fmzs.model.business;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by wliu on 2017/11/26 0026.
 */
@Data
@AllArgsConstructor
public class LatestBookInfoDO {

    //书籍名称
    private String name;

    //书籍的路径
    private String rootPath;

    private SectionContextDO latestSection;

    //最新章节
    private TreeSet<SectionContextDO> newSections = new TreeSet<>();

    public void addNewSection(SectionContextDO sectionContext){
        this.newSections.add(sectionContext);
    }

    public LatestBookInfoDO() {
    }

    @Override
    public String toString(){
        return "书名：" + name + ",最新章节：" + latestSection.getSectionName();
    }
}
