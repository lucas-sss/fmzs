package cn.onearth.fmzs.model.pojo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Created by wliu on 2017/11/28 0028.
 */
@Data
@Table(name = "tracer_task")
public class TracerTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="book_id")
    private Integer bookId;

    @Column(name="create_time")
    private Date createTime;

    @Column(name="modify_time")
    private Date modifyTime;


    public TracerTask(Integer bookId) {
        this.bookId = bookId;
    }

    public TracerTask() {

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId);
    }

    @Override
    public boolean equals(Object obj){

        if (null == obj){
            return false;
        }

        if (obj == this){
            return true;
        }
        if (obj.getClass() != TracerTask.class){
            return false;
        }
        TracerTask section = (TracerTask)obj;

        if (this.getBookId() == section.getBookId()){
            return true;
        }
        return false;
    }


}
