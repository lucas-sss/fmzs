package cn.onearth.fmzs.aspecttest;

import org.springframework.stereotype.Service;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2018/1/3 0003
 */
@Service("consumeService")
public class ConsumeServiceImpl implements ConsumeService {

    @SysServiceLog
    @Override
    public void save() {
        System.out.println("进入保存了");
    }
}
