package cn.onearth.fmzs.model.pojo;

import lombok.Data;

import java.util.Date;

/**
 * Created by wliu on 2017/11/18 0018.
 */
@Data
public class BookSection {

    private Integer id;

    private Integer bookId;

    private String sectionName;

    private String sectionPath;

    private Integer sequenceSite;

    private Integer prevSectionId;

    private Integer nextSectionId;

    private String sectionContent;

    private String localPath;

    private Date createTime;

    private Date modifyTime;

    @Override
    public String toString() {
        return "bookSection: id=" + this.getId() + ", name=" + this.getSectionName() + ", bookId=" + this.getBookId();
    }
}
