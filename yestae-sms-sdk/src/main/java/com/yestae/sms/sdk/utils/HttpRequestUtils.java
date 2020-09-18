package com.yestae.sms.sdk.utils;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * Http请求工具类
 *
 * @author:chengqiang
 * @date:2019/6/17-19:47
 */
public final class HttpRequestUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestUtils.class);

    /**
     * 请求超时时间
     */
    private static final int REQUEST_TIMEOUT = 8000;

    /**
     * 传输超时时间
     */
    private static final int TRANSFER_TIMEOUT = 8000;

    /**
     * 默认字符集
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * GET请求
     * 对应StringEntity
     * HttpRequestUtils.doGet()
     *
     * @param linkUrl
     * @return
     */
    public static String doGet(String linkUrl) {
        LOGGER.info("[HttpRequestUtils]...[doGet]...url : " + linkUrl);
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(linkUrl);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(REQUEST_TIMEOUT)
                .setConnectTimeout(TRANSFER_TIMEOUT).build();
        request.setConfig(requestConfig);
        try {
            HttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET).trim();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } finally {
            request.abort();
            request.releaseConnection();
        }
    }

    /**
     * POST请求
     * 对应StringEntity
     * HttpRequestUtils.doPost()
     *
     * @param linkUrl
     * @param param
     * @return
     */
    public static String doPost(String linkUrl, String param) {
        LOGGER.info("[HttpRequestUtils]...[doPost]...url : " + linkUrl + " , param: " + param);
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(linkUrl);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(REQUEST_TIMEOUT)
                .setConnectTimeout(TRANSFER_TIMEOUT).build();
        request.setConfig(requestConfig);
        try {
            StringEntity entity = new StringEntity(param, DEFAULT_CHARSET);
            request.setEntity(entity);
            HttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET).trim();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } finally {
            request.abort();
            request.releaseConnection();
        }
    }

    /**
     * POST请求
     *
     * @param linkUrl
     * @param params
     * @return
     */
    public static String doPost(String linkUrl, Map<String, String> params) {
        LOGGER.info("[HttpRequestUtils]...[doPost]...url : " + linkUrl + " , param: " + JSON.toJSONString(params));
        // 执行请求
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(linkUrl);
        // 设置请求的header
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(REQUEST_TIMEOUT)
                .setConnectTimeout(TRANSFER_TIMEOUT).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Accept", "application/json");
        try {
            httpPost.setEntity(new StringEntity(JSON.toJSONString(params), DEFAULT_CHARSET));
            HttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET).trim();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } finally {
            httpPost.abort();
            httpPost.releaseConnection();
        }
    }

    public static String doPostSSL(String url, Map<String, String> params) {
        String result = HttpRequest.post(url)
                .header("Content-Type", "application/json")
//                .header("X-Bmob-Application-Id", "2f0419a31f9casdfdsf431f6cd297fdd3e28fds4af")
//                .header("X-Bmob-REST-API-Key", "1e03efdas82178723afdsafsda4be0f305def6708cc6")
                .body(JSON.toJSONString(params))
                .execute().body();
        return result;
    }

}
