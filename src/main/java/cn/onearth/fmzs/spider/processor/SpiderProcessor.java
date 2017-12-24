package cn.onearth.fmzs.spider.processor;

import cn.onearth.fmzs.Utils.HttpClientUtil;
import cn.onearth.fmzs.Utils.RequestUtil;
import cn.onearth.fmzs.spider.proxy.Proxy;
import cn.onearth.fmzs.spider.req.Request;
import cn.onearth.fmzs.spider.resp.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.utils.UrlUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wliu on 2017/11/22 0022.
 */
@Component(value = "spiderProcessor")
public class SpiderProcessor {

    @Autowired
    HttpClientUtil httpClientUtil;

    private CloseableHttpClient client;

    /*@PostConstruct
    public void init(){
        this.client = httpClientUtil.getHttpClient();
    }*/

    public Response sendRequest(Request request, boolean contentLengthError) {

        HttpUriRequest httpUriRequest = RequestUtil.generateUriRequest(request);
        //Proxy proxy = new Proxy();
        HttpContext httpContext = httpClientUtil.newContext(httpUriRequest, null);

        CloseableHttpResponse httpResponse = null;
        Response response = null;
        try {
            CloseableHttpClient client = httpClientUtil.getHttpClient();
            httpResponse = client.execute(httpUriRequest, httpContext);
            response = new Response(httpResponse.getStatusLine().getStatusCode(), request, "");
            response.setHeaders(CollectionUtils.arrayToList(httpResponse.getAllHeaders()));

            byte[] contentBytes = null;
            if (!contentLengthError) {
                contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
            } else {
                InputStream in = null;
                ByteArrayOutputStream out = null;
                try {
                    out = new ByteArrayOutputStream();
                    HttpEntity httpEntity = httpResponse.getEntity();
                    in = httpEntity.getContent();
                    if (in.available() > 0){
                        byte[] temp = new byte[1024];
                        int size = 0;
                        try {
                            while ((size = in.read(temp)) != -1) {
                                out.write(temp, 0, size);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        contentBytes = out.toByteArray();
                    }
                } finally {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                }


                String charset = null;
                // 如果代理出问题了是会出现contentType拿不到的情况，例如代理的账号密码有问题等
                if (null != httpResponse.getEntity().getContentType()) {
                    String value = httpResponse.getEntity().getContentType().getValue();
                    if (value.contains("message")) {    //下载的是eml文件
                        response.setChartset(getEmlCharset(httpResponse, contentBytes));
                    } else {
                        response.setChartset(getHtmlCharset(httpResponse, contentBytes));
                    }
                }
                response.setData(contentBytes);
                if (StringUtils.isNotBlank(charset)) {
                    response.setChartset(charset);
                }
                return response;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            //需要先关闭流
            if (null != httpResponse) {
                EntityUtils.consumeQuietly(httpResponse.getEntity());
                try {
                    httpResponse.close();
                } catch (IOException e) {
                }
            }
            // 关闭http客户端和请求response
            if (client != null){
                try{
                    client.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    protected String getEmlCharset(HttpResponse httpResponse, byte[] contentBytes) throws IOException {
        String charset;
        String value = httpResponse.getEntity().getContentType().getValue();
        charset = UrlUtils.getCharset(value);
        if (StringUtils.isNotBlank(charset)) {
            return charset;
        }

        Charset defaultCharset = Charset.defaultCharset();
        String content = new String(contentBytes, defaultCharset.name());
        //
        if (StringUtils.isNotEmpty(content)) {
            charset = getCharset(content);
            if (StringUtils.isNotBlank(charset)) {
                return charset;
            }
        }
        return charset;
    }

    protected String getHtmlCharset(HttpResponse httpResponse, byte[] contentBytes) throws IOException {
        String charset;
        // charset
        // 1、encoding in http header Content-Type
        String value = httpResponse.getEntity().getContentType().getValue();
        charset = UrlUtils.getCharset(value);
        if (org.apache.commons.lang.StringUtils.isNotBlank(charset)) {
            //logger.debug("Auto get charset: {}", charset);
            return charset;
        }
        // use default charset to decode first time
        Charset defaultCharset = Charset.defaultCharset();
        String content = new String(contentBytes, defaultCharset.name());
        // 2、charset in meta
        if (org.apache.commons.lang.StringUtils.isNotEmpty(content)) {
            Document document = Jsoup.parse(content);
            Elements links = document.select("meta");
            for (Element link : links) {
                // 2.1、html4.01 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                String metaContent = link.attr("content");
                String metaCharset = link.attr("charset");
                if (metaContent.indexOf("charset") != -1) {
                    metaContent = metaContent.substring(metaContent.indexOf("charset"), metaContent.length());
                    charset = metaContent.split("=")[1];
                    break;
                }
                // 2.2、html5 <meta charset="UTF-8" />
                else if (org.apache.commons.lang.StringUtils.isNotEmpty(metaCharset)) {
                    charset = metaCharset;
                    break;
                }
            }
        }
        //logger.debug("Auto get charset: {}", charset);
        // 3、todo use tools as cpdetector for content decode
        return charset;
    }

    private static final Pattern patternForCharset = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)");

    private static String getCharset(String contentType) {
        Matcher matcher = patternForCharset.matcher(contentType);
        if (matcher.find()) {
            String charset = matcher.group(1);
            if (StringUtils.isNotEmpty(charset) && Charset.isSupported(charset)) {
                return charset;
            }
        }
        return null;
    }

    protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
        if (charset == null) {
            byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
            String htmlCharset = getHtmlCharset(httpResponse, contentBytes);
            if (htmlCharset != null) {
                return new String(contentBytes, htmlCharset);
            } else {
                //logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()", Charset.defaultCharset());
                return new String(contentBytes);
            }
        } else {
            return IOUtils.toString(httpResponse.getEntity().getContent(), charset);
        }
    }

}
