package cn.onearth.fmzs.Utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wliu on 2017/11/25 0025.
 */
public class ConvertUtil {

    private static Map<String, Integer> map = new HashMap<>();

    static {
        map.put("一", 1);
        map.put("二", 2);
        map.put("两", 2);
        map.put("三", 3);
        map.put("四", 4);
        map.put("五", 5);
        map.put("六", 6);
        map.put("七", 7);
        map.put("八", 8);
        map.put("九", 9);
        map.put("零", 0);
    }

    public static Integer getArabicNumber(String chinese) {

        if (chinese == null) {
            return null;
        }

        if (chinese.matches("\\d+")){
            return Integer.valueOf(chinese);
        }


        String temp = chinese.trim().replace(" ", "");

        Pattern pattern = Pattern.compile("([一二两三四五六七八九]+万)?([一二两三四五六七八九]+千)?(零?[一二两三四五六七八九]+百)?(零?[一二三四五六七八九]?十)?(零?[一二三四五六七八九]+)?");
        Matcher matcher = pattern.matcher(temp);
        if (matcher.find()) {
            String[] strs = new String[]{matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5)};
            IntSummaryStatistics summary = Arrays.asList(strs).stream().map(y -> changeToNum(y)).mapToInt(x -> x).summaryStatistics();
//            System.out.println(summary.getSum());
            return Integer.valueOf((int) summary.getSum());
        }

        return null;
    }

    public static Integer changeToNum(String str) {
        Integer num = 0;
        if (str == null) {
            return num;
        }
        str = str.replaceAll("零", "");
        if (StringUtils.contains(str, "万")) {
            num = getNum(str, "万") * 10000;
        } else if (StringUtils.contains(str, "千")) {
            num = getNum(str, "千") * 1000;
        } else if (StringUtils.contains(str, "百")) {
            num = getNum(str, "百") * 100;
        } else if (StringUtils.contains(str, "十")) {
            if ("十".equals(str)) {
                return 10;
            }
            num = getNum(str, "十") * 10;
        } else {
            num = getNum(str, null);
        }
        return num;
    }


    private static int getNum(String s, String unit) {
        if (unit != null) {
            s = s.replaceAll(unit, "");
        }
        if (map.containsKey(s)) {
            return map.get(s);
        } else {
            throw new IllegalArgumentException("不合法的转换参数 s=" + s);
        }
    }

}
