package cn.onearth.fmzs.service.basic.impl;

import cn.onearth.fmzs.dao.TracerTaskMapper;
import cn.onearth.fmzs.model.pojo.TracerTask;
import cn.onearth.fmzs.service.basic.TracerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 追书任务service
 *
 * Created by wliu on 2017/12/15 0015.
 */
@Service(value = "tracerService")
public class TracerServiceImpl implements TracerService {

    @Autowired
    private TracerTaskMapper tracerTaskMapper;

    @Override
    public List<TracerTask> selectAll(){
        return tracerTaskMapper.select(null);
    }

    @Override
    public int saveTracerTask(TracerTask tracerTask) {
        return tracerTaskMapper.insert(tracerTask);
    }


}
