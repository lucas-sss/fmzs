package cn.onearth.fmzs.model.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * Created by wliu on 2017/12/9 0009.
 */
@Data
@ToString
public class Bookrack {


    private Integer id;

    private Integer userId;

    private Integer bookId;

    private String readProgress;

    private String readPath;

    private String tracerStatus;

    private Book book;

}
