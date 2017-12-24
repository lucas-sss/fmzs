package cn.onearth.fmzs.model.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wliu on 2017/11/18 0018.
 */
@Data
@ToString
@Table(name="person")
public class Person implements Serializable {

    @Id
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    private String password;

    private String phone;

    @Column(name = "dd_count")
    private String ddCount;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;
}
