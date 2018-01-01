package cn.onearth.fmzs.model.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2017/12/28 0028
 */
@Data
@Table(name="book_source_add")
@ToString
public class BookSourceAdd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer status;

    private String author;

    private String rootSource;

    private Integer userId;

    private Date createTime;

    private Date modifyTime;
}
