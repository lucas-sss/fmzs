package cn.onearth.fmzs.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 *
 *
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2017/12/26 0026
 */
@Data
@Table(name="ddalarm_group")
@ToString
public class DDAlarmGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "create_time")
    @JsonIgnore
    private Date createTime;

    @Column(name = "modify_time")
    @JsonIgnore
    private Date modifyTime;

    public void DDAlarmGroup(){

    }

    public void DDAlarmGroup(Integer bookId, Integer userId){
        this.bookId = bookId;
        this.userId = userId;
    }
}
