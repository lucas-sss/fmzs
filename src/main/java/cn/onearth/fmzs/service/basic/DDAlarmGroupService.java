package cn.onearth.fmzs.service.basic;

import cn.onearth.fmzs.model.pojo.DDAlarmGroup;

import java.util.List;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2017/12/26 0026
 */
public interface DDAlarmGroupService {

    List<DDAlarmGroup> getAlramGroup(Integer bookId);
}
