package com.github.chm.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.chm.exception.DownloadImgException;
import com.github.chm.exception.UpLoadToHfException;
import com.github.chm.model.HfData;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;


/**
 * Created by chenhuaming on 16/7/8.
 */
public class HttpRequest {

    //连接超时时间，默认10秒
    private int socketTimeout = 5000;

    //传输超时时间，默认30秒
    private int connectTimeout = 10000;

    //请求器的配置
    private RequestConfig requestConfig;

    //HTTP请求器
    private CloseableHttpClient httpClient;

    public HttpRequest() throws NoSuchAlgorithmException {
        init();
    }

    private void init() throws NoSuchAlgorithmException {
        SSLContext sslcontext = SSLContext.getDefault();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    public void resetConfig(int socketTimeout, int connectTimeout) {
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /*public String get(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } finally {
            response.close();
        }
    }*/


    public String getBase64Img(String url) throws DownloadImgException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream in = entity.getContent();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int len;
                while ((len = in.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                }
                byte[] returnBytes = out.toByteArray();
                return Base64.encodeBase64String(returnBytes);
            } else {
                throw new DownloadImgException(String.format("(状态不是200)下载图片失败:url(%s)", url));
            }
        } catch (IOException e) {
            throw new DownloadImgException(String.format("(IO异常)下载图片失败:url(%s)", url), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject postDataToHF(String url, HfData data) throws UpLoadToHfException {
        String dt = JSON.toJSONString(data);
        HttpPost post = new HttpPost();
        post.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        StringEntity myEntity = new StringEntity(dt, ContentType.APPLICATION_JSON);// 构造请求数据
        try {
            response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseContent = EntityUtils.toString(entity, "UTF-8");
                try {
                    JSONObject res = JSON.parseObject(responseContent);
                    return res;
                }catch (Throwable e){
                    throw new UpLoadToHfException(String.format("(jsonParse错误)上传HF接口失败:%s;", dt),e);
                }
            } else {
                throw new UpLoadToHfException(String.format("(状态不是200)上传HF接口失败:%s;", dt));
            }
        } catch (IOException e) {
            throw new UpLoadToHfException(String.format("(IO异常)上传HF接口失败%s:;", dt), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
