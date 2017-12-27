package cn.onearth.fmzs.service.basic;

import cn.onearth.fmzs.model.pojo.RobotConfig;

import java.util.List;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2017/12/27 0027
 */
public interface RobotConfigService {

    List<RobotConfig> getAll();

    RobotConfig getConfigByBook(Integer bookId);
}
