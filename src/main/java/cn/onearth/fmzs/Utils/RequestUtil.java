package cn.onearth.fmzs.Utils;

import cn.onearth.fmzs.spider.req.Request;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import java.nio.charset.Charset;

/**
 * Created by wliu on 2017/11/22 0022.
 */
public class RequestUtil {


    public static HttpUriRequest generateUriRequest(Request request) {
        RequestBuilder builder;

        switch (request.getMethod()) {
            case get:
                builder = RequestBuilder.get();
                break;
            case post:
                builder = RequestBuilder.post();
                break;
            default:
                throw new IllegalArgumentException("非法的请求Method=" + request.getMethod());
        }
        // 设置charset
        builder.setCharset(Charset.forName(request.getCharset()));
        //设置url
        builder.setUri(request.getUrl());
        //设置超时时间
        RequestConfig.Builder config = RequestConfig.custom().setConnectionRequestTimeout(request.getConnReqTimeoutMs()).setSocketTimeout(request.getSocketTimeoutMs())
                .setConnectTimeout(request.getConnTimeoutMs()).setMaxRedirects(3).setRedirectsEnabled(false);
        //进行本地代理
//        config.setAuthenticationEnabled(true);
//        config.setProxy(new HttpHost("127.0.0.1", 8088));
        builder.setConfig(config.build());

        if (null != request.getEntity()){
            builder.setEntity(request.getEntity());
        }

        //设置headers
        if (!CollectionUtils.isEmpty(request.getHeaders())) {
            for (Header header : request.getHeaders()) {
                builder.addHeader(header);
            }
        }
        return builder.build();
    }

    public static Request get31xsSectionReq(String url){




        return null;
    }


}
