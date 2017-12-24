package cn.onearth.fmzs.spider.service;

import cn.onearth.fmzs.Utils.ConvertUtil;
import cn.onearth.fmzs.model.business.LatestBookInfoDO;
import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.model.business.SectionsDrawerDO;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.spider.processor.SpiderProcessor;
import cn.onearth.fmzs.spider.req.Method;
import cn.onearth.fmzs.spider.req.Request;
import cn.onearth.fmzs.spider.resp.Response;
import com.google.common.net.HttpHeaders;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wliu on 2017/11/26 0026.
 */
public class AbstractBasicTracer implements BasicTracer {

    private static final String SOUGOU_QUERY_PATH = "http://www.sogou.com/web?query=";

    protected static final Pattern pattern = Pattern.compile("第(.+)章 (.+)");

    @Override
    public LatestBookInfoDO traceLatestBookInfo(SpiderProcessor processor, String bookName) {

        String url = SOUGOU_QUERY_PATH + bookName;

        Request request = new Request(url, Method.get);

        request.addHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        request.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
        request.addHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.8");
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");

        Response response = processor.sendRequest(request, true);
        Document document = Jsoup.parse(response.getContent());

        /****最新章节*****/
        Elements select = document.select("div.vrwrap:eq(1) p:contains(新：) a");
        Elements title = document.select("div.vrwrap:eq(1) h3.vrTitle a");
        /*******部分最新章节列表********/
        Elements aList = document.select("div.vrwrap:eq(1) ul.vr-novel-list a");

        LatestBookInfoDO latestBookInfo = new LatestBookInfoDO();

        if (title != null && title.size() > 0) {
            String rootPath = title.first().attr("href");
            latestBookInfo.setRootPath(rootPath);
        }
        if (select != null && select.size() > 0) {
            String href = select.first().attr("href");
            String latterSection = select.first().text();
            Matcher m = pattern.matcher(latterSection);
            if (m.find()) {
                latestBookInfo.setLatestSection(new SectionContextDO(href, m.group(1) + "-" + m.group(2)));
            }
        }
        if (aList != null && aList.size() > 0) {
            for (Element temp : aList) {
                String text = temp.text();
                if (null != text) {
                    Matcher m = pattern.matcher(text);
                    if (m.find()) {
                        SectionContextDO sectionContext = new SectionContextDO();
                        sectionContext.setSectionPath(temp.attr("href"));
                        Integer index = ConvertUtil.getArabicNumber(m.group(1));
                        String info = index + "-" + m.group(2);
                        sectionContext.setIndex(index);
                        sectionContext.setSectionName(info);
                        latestBookInfo.addNewSection(sectionContext);
                    }
                }
            }
        }
        return latestBookInfo;
    }

    @Override
    public TreeSet<SectionContextDO> traceLatestSection(Book book) {
        SectionsDrawerDO allSection = getAllSection(book);
        TreeSet<SectionContextDO> sections = allSection.getSections();
        TreeSet<SectionContextDO> newSection = new TreeSet<>();
        for (int i = 0; i < 6; i++) {
            if (!sections.isEmpty()) {
                newSection.add(sections.pollLast());
            }
        }
        return newSection;
    }

    @Override
    public SectionsDrawerDO getAllSection(Book book) {
        return null;
    }

    @Override
    public SectionContextDO pullSectionContext(SectionContextDO sectionContext) {
        return null;
    }

    @Override
    public String getBookRootPath(String bookName) {
        return null;
    }

    /**
     * 返回 157-决一死战  这种形式
     *
     * @param name
     * @return
     */
    protected String convertSectionName(String name) {
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            Integer index = ConvertUtil.getArabicNumber(matcher.group(1));
            return index + "-" + matcher.group(2);
        }
        return null;
    }

    protected Integer getSectionIndex(String name) {
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            return ConvertUtil.getArabicNumber(matcher.group(1));
        }
        throw new RuntimeException("无法转换的章节");
    }
}
