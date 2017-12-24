package cn.onearth.fmzs.model.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by wliu on 2017/11/18 0018.
 */
@Data
@ToString
public class Book {

    private Integer id;

    private String name;

    private String author;

    private String status;

    private String source;

    //封面图片
    private String faceImage;

    private String lasterSection;

    private String lasterPath;

    private String rootPath;

    private Date createTime;

    private Date modifyTime;

}
