package cn.onearth.fmzs.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2017/12/27 0027
 */
@Data
@ToString
@Table(name = "ddrobot_config")
public class RobotConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer bookId;

    private String tocken;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date modifyTime;
}
