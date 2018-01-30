package cn.onearth.test;

import cn.onearth.fmzs.Utils.HttpClientUtil;
import cn.onearth.fmzs.model.business.LatestBookInfoDO;
import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.model.business.SectionsDrawerDO;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.service.taskjob.TracerTaskJob;
import cn.onearth.fmzs.spider.processor.SpiderProcessor;
import cn.onearth.fmzs.spider.req.Method;
import cn.onearth.fmzs.spider.req.Request;
import cn.onearth.fmzs.spider.resp.Response;
import cn.onearth.fmzs.spider.service.impl.N31xsTracerService;
import com.google.common.net.HttpHeaders;
import com.sun.javadoc.Doc;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wliu on 2017/11/18 0018.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class HttpClientTest {


    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private SpiderProcessor processor;

    @Autowired
    private N31xsTracerService n31xsTracerService;


    @Autowired
    private TracerTaskJob tracerTaskJob;

    @Test
    public void TracerTest(){


        tracerTaskJob.getTracerTask();

    }



    @Test
    public void traceLatestBookInfoTest(){


        String bookName = "大唐风华路";
        String url = "http://www.31xs.net/7/7792/";

        LatestBookInfoDO bookInfoDO = n31xsTracerService.traceLatestBookInfo(url, bookName);
        System.out.println(bookInfoDO.toString());

        TreeSet<SectionContextDO> newSections = bookInfoDO.getNewSections();
        for (SectionContextDO newSection : newSections) {
            System.out.println(newSection.toString());
        }
    }




    @Test
    public void allTest(){

        String bookName = "龙王传说";
        String rootPath = n31xsTracerService.getBookRootPath(bookName);
        if (null == rootPath) return;
        System.out.println("");
        System.out.println(rootPath);

        Book bookInfo = new Book();
        bookInfo.setName(bookName);
        bookInfo.setRootPath(rootPath);

        SectionsDrawerDO allSection = n31xsTracerService.getAllSection(bookInfo);
        TreeSet<SectionContextDO> sections = allSection.getSections();
        if (sections != null && sections.size() > 0) {
            for (SectionContextDO section : sections) {
                System.out.println(section.getSectionName() + "----" + section.getSectionPath());
            }
        }
        SectionContextDO sectionContext = sections.pollFirst();
        SectionContextDO sectionContextDO = n31xsTracerService.pullSectionContext(sectionContext);
        System.out.println(sectionContextDO.getSectionContext());
    }


    @Test
    public void testZhannei(){
        String url = "http://zhannei.baidu.com/cse/search?s=7845455592055299828&q=大主宰";

        Request request = new Request(url, Method.get);

        request.addHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        request.addHeader(HttpHeaders.REFERER, "http://www.31xs.net/");
        request.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
        request.addHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.8");
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");


        Response response = processor.sendRequest(request, true);

        Document document = Jsoup.parse(response.getContent());

        Elements select = document.select("div.result-game-item-detail a");

        for (Element temp : select) {


            String name = temp.text();
            if (StringUtils.equals(name, "大主宰")){
                String href = temp.attr("href");
                Pattern pattern = Pattern.compile("http://www\\.31xs\\.(com|net)(.+)");
                Matcher matcher = pattern.matcher(href);
                if (matcher.find()){
                    String rootPath = matcher.group(2);
                    break;
                }
            }


        }


        System.out.println(response.getContent());

    }



    @Test
    public void tracerServiceTest() {

        Book bookInfo = new Book();
//        bookInfo.setName("大唐风华路");
//        bookInfo.setRootPath("http://www.31xs.net/7/7792/");
        bookInfo.setName("大主宰");
        bookInfo.setRootPath("http://www.31xs.net/0/101/");

        SectionsDrawerDO allSection = n31xsTracerService.getAllSection(bookInfo);


        TreeSet<SectionContextDO> sections = allSection.getSections();

        if (sections != null && sections.size() > 0) {

            for (SectionContextDO section : sections) {

                System.out.println(section.getSectionName() + "----" + section.getSectionPath());

            }
        }

        SectionContextDO sectionContext = sections.pollFirst();

        SectionContextDO sectionContextDO = n31xsTracerService.pullSectionContext(sectionContext);
        System.out.println(sectionContextDO.getSectionContext());
    }


    @Test
    public void processorTest() {

        //        String url = "http://www.xxbiquge.com/0_142/1006272.html";
        String url = "http://www.sogou.com/web?query=大主宰";

        Request request = new Request(url, Method.get);

        request.addHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        request.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
        request.addHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.8");
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");


        Response response = processor.sendRequest(request, true);


        Document document = Jsoup.parse(response.getContent());


        Elements select = document.select("div.vrwrap:eq(1) p:contains(新：) a");
        Elements title = document.select("div.vrwrap:eq(1) h3.vrTitle a");
        Elements aList = document.select("div.vrwrap:eq(1) ul.vr-novel-list a");
        String rootPath = "";
        if (title != null && title.size() > 0) {
            rootPath = title.first().attr("href");
        }
        System.out.println(rootPath);
        String latterSection = "";
        if (select != null && select.size() > 0) {
            latterSection = select.first().text();
        }
        List<String> sections = new ArrayList<>();
        if (aList != null && aList.size() > 0) {

            for (Element temp : aList) {
                String text = temp.text();
                if (null != text) {
                    Pattern p = Pattern.compile("第(.+)章 (.+)");
                    Matcher m = p.matcher(text);
                    if (m.find()) {
                        String info = m.group(1) + "-" + m.group(2);
                        sections.add(info);
                    }
                }
            }
        }

        System.out.println(latterSection);
        System.out.println(StringUtils.join(sections, "&"));

    }


    @Test
    public void clientTest() throws Exception {
        CloseableHttpClient client = httpClientUtil.getHttpClient();
//        String url = "http://www.xxbiquge.com/0_142/1006272.html";
        String url = "https://www.baidu.com/s?wd=大唐风华路";

        Request request = new Request(url, Method.get);

        HttpGet httpGet = new HttpGet(url);
//        RequestConfig.Builder config = RequestConfig.custom();
//        httpGet.setConfig(config.setProxy(new HttpHost("127.0.0.1", 8088)).build());


        InputStream inputStream = null;
        CloseableHttpResponse response = null;

        try {
            //HttpUriRequest req = RequestUtil.generateUriRequest(request);
            response = client.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {

                inputStream = httpEntity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpGet.releaseConnection();
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    public static void main(String[] args) {

        String url = "http://www.xxbiquge.com/0_142/1006272.html";
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);

            HttpEntity httpEntity = response.getEntity();

            if (httpEntity != null) {
                String content = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(content);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }


    }


}
