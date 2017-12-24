package cn.onearth.fmzs.model.business;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by wliu on 2017/11/26 0026.
 */

@Data
@AllArgsConstructor
public class SectionsDrawerDO {

    //书籍名称
    private String bookName;

    //书籍路径
    private String rootPath;

    /**
     * 所有章节以 74-掠夺民族 的形式存储
     */
    private TreeSet<SectionContextDO> sections = new TreeSet<>();

    public SectionsDrawerDO() {
    }

    public SectionsDrawerDO(String bookName, String rootPath) {
        this.bookName = bookName;
        this.rootPath = rootPath;
    }

    public void addSection(SectionContextDO sectionContext) {
        this.sections.add(sectionContext);

    }
}
