package cn.onearth.fmzs.utils.common;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 钉钉机器人配置缓存工具
 * key：为书籍id
 * value：对应钉钉机器人tocken
 *
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2017/12/27 0027
 */
public class RobotConfigCacheUtil {

    private static ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

    public static void setValue(Integer bookId, String tocken) {
        if (bookId != null && tocken != null) {
            map.put(bookId, tocken);
        }
    }

    public static String getValue(Integer bookId) {
        if (map.containsKey(bookId)) {
            return map.get(bookId);
        }
        return null;
    }
}
