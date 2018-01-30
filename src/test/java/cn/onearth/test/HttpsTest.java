package cn.onearth.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author liuwei liuwei@flksec.com
 * @version 1.0
 * @date 2018/1/30 0030
 */
public class HttpsTest {


    public static void main(String[] args) throws Exception {

        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, (chain, authType) -> true).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());


//        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new File("D:\\alipay.store"), "ssl123".toCharArray(), new TrustSelfSignedStrategy()).build();
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
//
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        InputStream inputStream = null;

        try {

            HttpGet httpGet = new HttpGet("https://www.baidu.com/s?wd=大唐风华路");
            CloseableHttpResponse response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();
            System.out.println("---------------------------------");
            if (entity != null) {

                inputStream = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                EntityUtils.consume(entity);
            }


//            System.out.println(response.getStatusLine());

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            httpClient.close();
            if (inputStream != null){
                try {
                    inputStream.close();
                }catch (Exception e){

                }
            }
        }

    }
}
