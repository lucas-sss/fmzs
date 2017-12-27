package cn.onearth.fmzs.listener;

import cn.onearth.fmzs.utils.common.ConstantCacheUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * Created by wliu on 2017/12/25 0025.
 */
public class ConstantCacheListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (event.getApplicationContext().getParent() != null) {
            return;
        }

        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/AppConstant.properties"));

            for (Object o : properties.keySet()) {
                String key = (String)o;
                ConstantCacheUtil.setValue(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            //TODO 日志记录启动错误信息
        }
    }

}
