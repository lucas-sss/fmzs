package cn.onearth.fmzs.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wliu on 2017/11/18 0018.
 */
@Data
@ToString
public class Book implements Serializable {

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

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date modifyTime;

}
