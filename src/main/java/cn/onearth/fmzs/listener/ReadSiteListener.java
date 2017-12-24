package cn.onearth.fmzs.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wliu on 2017/12/15 0015.
 */
public class ReadSiteListener implements ServletContextListener {


    /**
     * 初始化阅读进度容器
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        ConcurrentHashMap<Integer, HashMap<Integer, Integer>> conMap = new ConcurrentHashMap<>();
        servletContext.setAttribute("readSiteMap", conMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /**
         * 在容器销毁前要先把阅读进度同步完成
         */
    }
}
