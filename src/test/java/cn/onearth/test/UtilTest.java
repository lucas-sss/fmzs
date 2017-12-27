package cn.onearth.test;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by wliu on 2017/11/25 0025.
 */
public class UtilTest {


    public static void main(String[] args) {


        String join = StringUtils.join(new String[]{"122"}, ",");
        System.out.println(join);

        /*String chinese = "一千两百三十三";

        Integer arabicNumber = ConvertUtil.getArabicNumber(chinese);

        System.out.println(arabicNumber);*/


    }
}
