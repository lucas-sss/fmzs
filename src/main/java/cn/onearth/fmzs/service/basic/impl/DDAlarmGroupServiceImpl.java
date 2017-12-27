package cn.onearth.fmzs.service.basic.impl;

import cn.onearth.fmzs.dao.DDAlarmGroupMapper;
import cn.onearth.fmzs.model.pojo.DDAlarmGroup;
import cn.onearth.fmzs.service.basic.DDAlarmGroupService;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2017/12/26 0026
 */
@Service
public class DDAlarmGroupServiceImpl implements DDAlarmGroupService {

    @Autowired
    private DDAlarmGroupMapper ddAlarmGroupMapper;

    @Override
    public List<DDAlarmGroup> getAlramGroup(Integer bookId) {
        Example example = new Example(DDAlarmGroup.class);
        example.createCriteria().andEqualTo("bookId",bookId);
        List<DDAlarmGroup> ddAlarmGroups = ddAlarmGroupMapper.selectByExample(example);
        return ddAlarmGroups;
    }
}
