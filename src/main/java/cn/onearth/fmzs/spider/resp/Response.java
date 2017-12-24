package cn.onearth.fmzs.spider.resp;

import cn.onearth.fmzs.spider.req.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.UrlUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wliu on 2017/11/22 0022.
 */
@Data
@AllArgsConstructor
public class Response {

    //状态码
    private int statusCode;
    //字符集
    private String chartset;

    private byte[] data;
    //字符串内容
    private String content;

    private List<Header> headers;

    private Request request;

    public Response() {
        super();
    }

    public Response(int statusCode, Request request, String content){
        this.statusCode = statusCode;
        this.request = request;
        this.content = content;
    }

    public String getHeader(String key) {
        for (Header header : headers) {
            if (StringUtils.equals(header.getName(), key)) {
                return header.getValue();
            }
        }
        return null;
    }

    /**
     * 获取header里的key一样的键值，例如Set-Cookie
     *
     * @param key
     * @return
     */
    public List<String> getHeaderList(String key) {
        List<String> hdrs = new ArrayList<String>();
        for (Header header : headers) {
            if (StringUtils.equalsIgnoreCase(header.getName(), key)) {
                String value = header.getValue();
                hdrs.add(value);
            }
        }
        return hdrs;
    }

    /**
     * 取得 String 类型数据
     *
     * @return
     */
    public String getContent() {
        if (StringUtils.isNotBlank(chartset)) {
            try {
                return new String(data, chartset);
            } catch (UnsupportedEncodingException e) {
                return new String(data);
            }
        } else {
            return new String(data);
        }
    }

    /**
     * 返回从页面源代码生成的 HTML 对象
     *
     * @return
     */
    public Html getHtml() {
        // TODO json 字符串判断等
        return new Html(UrlUtils.fixAllRelativeHrefs(getContent(), request.getUrl()));// 替换链接相对路径为绝对路径
    }

}
