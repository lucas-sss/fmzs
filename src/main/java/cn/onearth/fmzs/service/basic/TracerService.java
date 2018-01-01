package cn.onearth.fmzs.service.basic;

import cn.onearth.fmzs.model.pojo.TracerTask;

import java.util.List;

/**
 *
 * Created by wliu on 2017/12/15 0015.
 */
public interface TracerService {
    /**
     * 查询所有任务
     *
     * @return
     */
    List<TracerTask> selectAll();

    int saveTracerTask(TracerTask tracerTask);
}
