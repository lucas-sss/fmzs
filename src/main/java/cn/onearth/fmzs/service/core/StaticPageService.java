package cn.onearth.fmzs.service.core;

import java.util.Map;

/**
 * 静态化页面service，对章节内容进行静态化处理
 * <p>
 * Created by wliu on 2017/12/14 0014.
 */
public interface StaticPageService {

    /**
     * rootMap 中包含完整的章节美容
     *
     * @param rootMap 数据
     */
    String index(Map<String, Object> rootMap);
}
