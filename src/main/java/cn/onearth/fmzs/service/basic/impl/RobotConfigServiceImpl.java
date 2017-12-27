package cn.onearth.fmzs.service.basic.impl;

import cn.onearth.fmzs.dao.RobotConfigMapper;
import cn.onearth.fmzs.model.pojo.RobotConfig;
import cn.onearth.fmzs.service.basic.RobotConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2017/12/27 0027
 */
@Service
public class RobotConfigServiceImpl implements RobotConfigService {

    @Autowired
    private RobotConfigMapper robotConfigMapper;

    @Override
    public List<RobotConfig> getAll() {
        List<RobotConfig> select = robotConfigMapper.select(null);
        return select;
    }

    @Override
    public RobotConfig getConfigByBook(Integer bookId) {
        RobotConfig config = new RobotConfig();
        config.setBookId(bookId);
        return robotConfigMapper.selectOne(config);
    }
}
