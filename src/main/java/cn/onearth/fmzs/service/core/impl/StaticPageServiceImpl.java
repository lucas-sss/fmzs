package cn.onearth.fmzs.service.core.impl;

import cn.onearth.fmzs.model.pojo.BookSection;
import cn.onearth.fmzs.service.core.StaticPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.*;
import java.net.URL;
import java.util.Map;

/**
 *
 *
 * @author liuwei
 */
@Component(value = "staticPageService")
public class StaticPageServiceImpl implements StaticPageService, ServletContextAware{

    static final Logger LOGGER = Logger.getLogger(StaticPageServiceImpl.class.getName());

    private Configuration conf;

    private ServletContext servletContext;

    private String rootPath;

    @PostConstruct
    public void init(){
        URL resource = this.getClass().getResource("/");
        String path = resource.getPath();
        File file = new File(path);
        File parentFile = file.getParentFile();

        conf = new Configuration(Configuration.VERSION_2_3_23);
        conf.setDefaultEncoding("utf-8");
        rootPath = parentFile.getPath();
        try {
            conf.setDirectoryForTemplateLoading(new File(rootPath + "/ftl"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("根路径rootPath：" + rootPath);
    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }



    /**
     * rootMap数据结构
     * book        ：book
     * section     ：section
     * nextSection ：nextSection
     * lastSection ：lastSection
     *
     * @param rootMap 数据
     */
    @Override
    public String index(Map<String, Object> rootMap) {
        BookSection section = (BookSection) rootMap.get("section");
        if (null == section) {
            //没有静态化数据
            return null;
        }

        Integer bookId = section.getBookId();
        Integer sequenceSite = section.getSequenceSite();
        if (bookId == null || sequenceSite == null){
            return null;
        }
        // 模板 + 数据 = 输出
        // 配置写出文件 /html/section/102/1.html
        String outPath = "/html/section/" + String.valueOf(bookId) + "/" + sequenceSite + ".html";
        String realPath = rootPath + outPath;
        LOGGER.info("存储位置：" + realPath);
        File file = new File(realPath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        Writer writer = null;
        try {
            Template template = conf.getTemplate("section.html");
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
//            writer = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            template.process(rootMap, writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
                writer = null;
            }
        }
        //这里返回访问的路径
        return realPath;
    }


}
