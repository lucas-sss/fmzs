package cn.onearth.fmzs.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wliu on 2017/11/28 0028.
 */
@Data
@AllArgsConstructor
@Table(name = "tracer_group")
public class TracerGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="tracer_task_id")
    private Integer tracerTaskId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="user_phone")
    private String userPhone;

    @Column(name="create_time")
    private Date createTime;

    @Column(name="modify_time")
    private Date modifyTime;

    public TracerGroup(Integer tracerTaskId, Integer userId) {
        this.tracerTaskId = tracerTaskId;
        this.userId = userId;
    }

    public TracerGroup() {
    }
}
