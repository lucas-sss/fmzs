package cn.onearth.fmzs.spider.service.impl;

import cn.onearth.fmzs.model.business.LatestBookInfoDO;
import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.model.business.SectionsDrawerDO;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.spider.processor.SpiderProcessor;
import cn.onearth.fmzs.spider.req.Method;
import cn.onearth.fmzs.spider.req.Request;
import cn.onearth.fmzs.spider.resp.Response;
import cn.onearth.fmzs.spider.service.AbstractBasicTracer;
import com.google.common.net.HttpHeaders;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http://www.31xs.net 小说网的针对性爬取服务
 * <p>
 * Created by wliu on 2017/11/26 0026.
 */
@Service(value = "n31xsTracerService")
public class N31xsTracerService extends AbstractBasicTracer {

    private static final String WEBSITE_HOST = "http://www.31xs.org";

    private static final String N31XS_SERCH = "http://zhannei.baidu.com/cse/search?s=7845455592055299828&q=";

    @Autowired
    private SpiderProcessor processor;


    public LatestBookInfoDO traceLatestBookInfo(String url, String bookName) {

        Request request = createBasicRequest(url);
        Response response = processor.sendRequest(request, true);

        String content = response.getContent();
        Document doc = Jsoup.parse(content);

        Elements dds = doc.select("dt:contains(最新章节) ~ dd:lt(7)");

        if (dds != null && dds.size() > 0) {
            TreeSet<SectionContextDO> set = new TreeSet<>();
            for (Element dd : dds) {
                Element a = dd.select("a").first();
                SectionContextDO sectionContext = new SectionContextDO();
                String name = a.text();
                if (pattern.matcher(name).find()) {
                    String path = a.attr("href");
                    if (!StringUtils.contains(path, WEBSITE_HOST)) {
                        path = WEBSITE_HOST + path;
                    }
                    sectionContext.setSectionName(convertSectionName(name));
                    sectionContext.setIndex(getSectionIndex(name));
                    sectionContext.setSectionPath(path);
                    set.add(sectionContext);
                }
            }

            LatestBookInfoDO latestBookInfo = new LatestBookInfoDO();

            if (set.size() > 0) {
                SectionContextDO sectionContextDO = set.last();
                latestBookInfo.setRootPath(url);
                latestBookInfo.setName(bookName);
                latestBookInfo.setLatestSection(sectionContextDO);

                for (SectionContextDO contextDO : set) {
                    latestBookInfo.addNewSection(contextDO);
                }
            }
            return latestBookInfo;
        }
        return null;
    }


    @Override
    public String getBookRootPath(String bookName) {
        Request request = createBasicRequest(N31XS_SERCH + bookName);
        request.addHeader(HttpHeaders.REFERER, "http://www.31xs.net/");

        Response response = processor.sendRequest(request, true);
        Document document = Jsoup.parse(response.getContent());
        Elements select = document.select("div.result-game-item-detail a");
        String rootPath = null;
        for (Element temp : select) {
            String name = temp.text();
            if (StringUtils.equals(name, bookName)) {
                String href = temp.attr("href");
                Pattern pattern = Pattern.compile("http://www\\.31xs\\.(com|net|org)(.+)");
                Matcher matcher = pattern.matcher(href);
                if (matcher.find()) {
                    rootPath = matcher.group(2);
                    break;
                }
            }
        }
        return rootPath == null ? null : WEBSITE_HOST + rootPath;
    }


    @Override
    public SectionsDrawerDO getAllSection(Book book) {

        Request request = createBasicRequest(book.getRootPath());
        Response response = processor.sendRequest(request, true);

        assembleResponse(response, book.getName());

        String content = response.getContent();
        Document doc = Jsoup.parse(content);

        Elements elements = doc.select("#list dd");
        SectionsDrawerDO sectionsDrawer = null;
        if (elements != null && elements.size() > 0) {
            sectionsDrawer = new SectionsDrawerDO();
            sectionsDrawer.setBookName(book.getName());
            for (Element temp : elements) {
                Element a = temp.select("a").first();
                if (a != null) {
                    SectionContextDO sectionContext = new SectionContextDO();
                    String name = a.text();
                    if (!pattern.matcher(name).find()){
                        /**
                         * 这里章节名称后可能不含空格，简单处理
                         */
                        if (StringUtils.contains(name, "章")){
                            name = name.replace("章", "章 ");
                        }
                    }
                    if (pattern.matcher(name).find()) {

                        String path = a.attr("href");
                        if (!StringUtils.contains(path, WEBSITE_HOST)) {
                            path = WEBSITE_HOST + path;
                        }
                        sectionContext.setSectionName(convertSectionName(name));
                        sectionContext.setIndex(getSectionIndex(name));
                        sectionContext.setSectionPath(path);
                        sectionsDrawer.addSection(sectionContext);
                    }
                }
            }
        }
        return sectionsDrawer;
    }

    /**
     * 获取指定章节的内容
     *
     * @param sectionContext
     * @return
     */
    @Override
    public SectionContextDO pullSectionContext(SectionContextDO sectionContext) {

        Request request = createBasicRequest(sectionContext.getSectionPath());
        Response response = processor.sendRequest(request, true);
        String content = response.getContent();
        Document doc = Jsoup.parse(content);
        Elements select = doc.select("#content p");
        StringBuilder sb = new StringBuilder("&emsp;&emsp;");
        for (Element temp : select) {
            String text = temp.text();
            sb.append(text).append("<br/>").append("&emsp;&emsp;");

        }
        sectionContext.setSectionContext(sb.toString());
        return sectionContext;
    }

    /**
     * 创建31小说的基本请求
     *
     * @param url
     * @return
     */
    private Request createBasicRequest(String url) {
        Request request = new Request(url, Method.get);
        request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        request.addHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
        request.addHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.8");
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        return request;
    }


    /**
     * 针对部分页面返回数据无编码格式，先设置默认utf-8
     * 如果不匹配查询书名则改为gbk
     *
     * @param response
     * @param bookName
     */
    private void assembleResponse(Response response, String bookName) {
        if (null == response.getChartset()) {
            response.setChartset("uft-8");
            Document parse = Jsoup.parse(response.getContent());
            String title = parse.select("title").first().text();
            if (!StringUtils.contains(title, bookName)) {
                response.setChartset("gbk");
            }
        }
    }

}
