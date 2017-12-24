package cn.onearth.fmzs.spider.service.impl;

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
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by wliu on 2017/12/4 0004.
 */
@Component(value = "nBiqugeTracerService")
public class NBiqugeTracerService extends AbstractBasicTracer {

    private static final String NBIQUGE_HOST = "http://www.biquge5200.com/";

    private static final String NBIQUGE_SERCH = "http://www.biquge5200.com/modules/article/search.php?searchkey=";

    @Autowired
    private SpiderProcessor processor;

    @Override
    public SectionsDrawerDO getAllSection(Book book) {

        Request request = createBasicRequest(book.getRootPath(), null);
        Response response = processor.sendRequest(request, true);
        String content = response.getContent();

        Document document = Jsoup.parse(content);

        Elements select = document.select("#list > dl > dt ~ dd");
        SectionsDrawerDO sectionsDrawer = null;
        if (select != null && select.size() > 0) {
            sectionsDrawer = new SectionsDrawerDO();
            sectionsDrawer.setBookName(book.getName());
            for (Element dd : select) {
                SectionContextDO sectionContext = new SectionContextDO();
                Element a = dd.select("a").first();

                String name = a.text();
                if (pattern.matcher(name).find()) {
                    String path = a.attr("href");
                    if (!StringUtils.contains(path, NBIQUGE_HOST)) {
                        if (path.startsWith("/")) {
                            path = path.substring(1);
                        }
                        path = NBIQUGE_HOST + path;
                    }
                    sectionContext.setSectionName(convertSectionName(name));
                    sectionContext.setIndex(getSectionIndex(name));
                    sectionContext.setSectionPath(path);
                    sectionsDrawer.addSection(sectionContext);
                }
            }
        }

        return sectionsDrawer;
    }

    @Override
    public SectionContextDO pullSectionContext(SectionContextDO sectionContext) {

        Request request = createBasicRequest(sectionContext.getSectionPath(), null);
        Response response = processor.sendRequest(request, true);

        Document doc = Jsoup.parse(response.getContent());

        Element element = doc.getElementById("content");
        if (element != null){
            sectionContext.setSectionContext(element.text());
        }
        return sectionContext;
    }

    @Override
    public String getBookRootPath(String bookName) {
        String url = "";
        try {
            url = NBIQUGE_SERCH + URLEncoder.encode(bookName, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }

        Request request = createBasicRequest(url, url);
        Response response = processor.sendRequest(request, true);
        Document doc = Jsoup.parse(response.getContent());
        Elements find = doc.select("#hotcontent tr:gt(0)");
        if (find != null && find.size() > 0) {
            Elements tds = find.get(0).select("td");
            String rootPath = tds.get(0).select("a").first().attr("href");
            return rootPath;
        }
        return null;
    }


    /**
     * 创建笔趣阁小说的基本请求
     *
     * @param url
     * @return
     */
    private Request createBasicRequest(String url, String referer) {
        Request request = new Request(url, Method.get);
        request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        request.addHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
        request.addHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.8");
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        if (StringUtils.isNotEmpty(referer)) {
            request.addHeader(HttpHeaders.REFERER, referer);
        }
        return request;
    }

}
