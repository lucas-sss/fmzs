package cn.onearth.fmzs.Utils.common;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wliu on 2017/12/25 0025.
 */
public class ConstantCacheUtil {

    private static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();

    public static void setValue(String key, String value) {
        if (key != null && value != null) {
            map.put(key, value);
        }
    }

    public static String getValue(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }
}
