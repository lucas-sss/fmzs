package cn.onearth.fmzs.spider.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wliu on 2017/11/22 0022.
 */
@Data
@AllArgsConstructor
public class Request {

    //请求ur
    private String url;
    //请求方法
    private Method method;
    //编码默认utf-8
    private String charset = "UTF-8";
    //超时时间
    private int connReqTimeoutMs = 5000;
    private int socketTimeoutMs = 8000;
    private int connTimeoutMs = 5000;

    //post请求参数
    private Map<String, String> params = new HashMap<>();

    //字符串参数
    private HttpEntity entity;

    //请求头
    private List<Header> headers = new ArrayList<Header>();

    public Request() {
        super();
    }

    public Request(String url, Method method) {
        this.url = url;
        this.method = method;
    }

    public Request addHeader(String key, String value){
        this.headers.add(new BasicHeader(key, value));
        return this;
    }


}
