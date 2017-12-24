package cn.onearth.fmzs.Utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wliu on 2017/12/15 0015.
 */
@Component(value = "cacheUtil")
public class CacheUtil {

    private Map<String, Object> map = new HashMap<>();

    public Object getValue(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }

    public void setValue(String key, Object value) {
        this.map.put(key, value);
    }
}
