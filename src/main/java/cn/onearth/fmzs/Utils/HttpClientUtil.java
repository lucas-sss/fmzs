package cn.onearth.fmzs.Utils;

import cn.onearth.fmzs.spider.proxy.Proxy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.execchain.ClientExecChain;
import org.apache.http.protocol.*;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wliu on 2017/11/21 0021.
 */
@Component
public class HttpClientUtil {


    private static final int MAX_CONNECTION_NUM = 400;

    private static final int MAX_PER_ROUTE = 40;


    private PoolingHttpClientConnectionManager clientPool;


    @PostConstruct
    public void init() throws Exception {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("https", createSSLConnSocketFactory())
                .register("http", new PlainConnectionSocketFactory())
                .build();
        clientPool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        clientPool.setMaxTotal(MAX_CONNECTION_NUM);
        clientPool.setDefaultMaxPerRoute(MAX_PER_ROUTE);
    }


    public CloseableHttpClient getHttpClient() {
        /*if (null == config) {
            config = new RequestConfig();
        }*/
        CloseableHttpClient httpClient = HttpClients.custom()
                //.setDefaultRequestConfig(config)
                .setConnectionManager(this.clientPool).setConnectionManagerShared(true).build();
        return httpClient;
        //return custom(clientPool, 5L, Collections.<HttpRequestInterceptor>emptyList()).build();
    }


    public static HttpClientBuilder custom(final HttpClientConnectionManager connnManager,
                                           final long maxIdleSeconds,
                                           final List<HttpRequestInterceptor> itcps)
            throws Exception {
        HttpClientBuilder httpClientBuilder = new HttpClientBuilder() {
            @Override
            protected ClientExecChain createMainExec(HttpRequestExecutor requestExec,
                                                     HttpClientConnectionManager connManager,
                                                     ConnectionReuseStrategy reuseStrategy,
                                                     ConnectionKeepAliveStrategy keepAliveStrategy,
                                                     HttpProcessor proxyHttpProcessor,
                                                     AuthenticationStrategy targetAuthStrategy,
                                                     AuthenticationStrategy proxyAuthStrategy,
                                                     UserTokenHandler userTokenHandler) {
                return super.createMainExec(requestExec, connManager, reuseStrategy,
                        keepAliveStrategy, new ImmutableHttpProcessor(
                                new HttpRequestInterceptor[]{new RequestTargetHost()}),
                        targetAuthStrategy, proxyAuthStrategy, userTokenHandler);
            }
        };
        // 设置自定义的HTTP 拦截器
        if (CollectionUtils.isNotEmpty(itcps)) {
            for (HttpRequestInterceptor requestInterceptor : itcps) {
                httpClientBuilder.addInterceptorLast(requestInterceptor);

            }
        }

        httpClientBuilder.setDefaultSocketConfig(SocketConfig.custom().setSoKeepAlive(true)
                .setTcpNoDelay(true).build());
        // 设置自定义的Cookie处理
        httpClientBuilder.setDefaultCookieSpecRegistry(CookieSpecRegistries.createDefault());
        httpClientBuilder.setConnectionManager(connnManager);
        httpClientBuilder.evictExpiredConnections();
        httpClientBuilder.evictIdleConnections(maxIdleSeconds, TimeUnit.SECONDS);
        return httpClientBuilder;
    }


    public HttpContext newContext(HttpUriRequest req, Proxy proxy) {
        final CookieStore cookieStore = new BasicCookieStore();

        final HttpClientContext context = HttpClientContext.create();

        if (null != proxy) {

            // 设置代理认证信息
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(new AuthScope(proxy.getHost(), proxy.getPort()),
                    new UsernamePasswordCredentials(proxy.getUsername(),
                            proxy.getPassword()));
            context.setCredentialsProvider(credentialsProvider);
        }
        // 设置CookieStore:
        context.setCookieStore(cookieStore);
        return context;
    }


    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = SSLContext.getDefault();
            sslsf = new SSLConnectionSocketFactory(sslContext);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

}
