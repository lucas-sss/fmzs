package cn.onearth.fmzs.utils.common;

import cn.onearth.fmzs.model.pojo.DDAlarmGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2017/12/26 0026
 */
public class AlarmGroupCacheUtil {

    private static ConcurrentHashMap<Integer, List<DDAlarmGroup>> map = new ConcurrentHashMap<Integer, List<DDAlarmGroup>>();

    public static void setValue(Integer bookId, DDAlarmGroup ddAlarmGroup) {
        if (bookId != null && ddAlarmGroup != null) {
            List<DDAlarmGroup> list = map.get(bookId);
            if (list == null){
                list = new ArrayList<DDAlarmGroup>();
                map.put(bookId, list);
            }
            list.add(ddAlarmGroup);
        }
    }

    public static List<DDAlarmGroup> getValue(Integer bookId) {
        if (map.containsKey(bookId)) {
            return map.get(bookId);
        }
        return null;
    }
}
