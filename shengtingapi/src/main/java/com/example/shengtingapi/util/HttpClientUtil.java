package com.example.shengtingapi.util;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    protected static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    public static HttpClientConfig config = new HttpClientConfig();

    public static CloseableHttpClient getClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(config.connReqTimeout)   //从连接池中获取连接的超时时间
                //与服务器连接超时时间：httpclient会创建一个异步线程用以创建socket连接，此处设置该socket的连接超时时间
                .setConnectTimeout(config.connTimeout)
                .setSocketTimeout(config.socketTimeout)               //socket读数据超时时间：从服务器获取响应数据的超时时间
                .build();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(config.maxConnTotal)                   //连接池中最大连接数
                /**
                 * 分配给同一个route(路由)最大的并发连接数。
                 * route：运行环境机器 到 目标机器的一条线路。
                 * 举例来说，我们使用HttpClient的实现来分别请求 www.baidu.com 的资源和 www.bing.com 的资源那么他就会产生两个route。
                 */
                .setMaxConnPerRoute(config.maxConnPerRoute)
                .setDefaultRequestConfig(requestConfig).build();

        return httpClient;
    }

    private static void addHeads(HttpRequestBase httpRequest, Map<String, String> paramHeads ){
        if(paramHeads!=null) {
            for (String key : paramHeads.keySet()) {
                httpRequest.addHeader(key, paramHeads.get(key));
            }
        }
    }

    private static UrlEncodedFormEntity addParams(Map<String, Object> params ) throws UnsupportedEncodingException {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if(params!=null) {
            for (String key : params.keySet()) {
                list.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
            }
        }
        return new UrlEncodedFormEntity(list, "utf-8");
    }

    public static byte[] getByte(String requestUrl, Map<String, String> paramHeads) throws IOException {
        HttpGet httpGet = new HttpGet( requestUrl);
        addHeads(httpGet, paramHeads);
        return getByByte(httpGet);
    }

    public static String getByUrl(String requestUrl, Map<String, String> paramHeads) throws IOException {
        HttpGet httpGet = new HttpGet( requestUrl);
        addHeads(httpGet, paramHeads);
        return get(httpGet);
    }

    public static String chuckHttpEntity(HttpEntity httpEntity) throws IOException {
        InputStream inputStream= httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Consts.UTF_8));
        StringBuffer contentBuff = new StringBuffer();
        String body = null;
        while ((body = br.readLine()) != null) {
            contentBuff.append(body);
        }
        return contentBuff.toString();
    }

    public static String getByForm(Map<String, Object> params, String requestUrl, Map<String, String> paramHeads) throws IOException {

        UrlEncodedFormEntity urlEncodedFormEntity = addParams(params);
        String paramStr = EntityUtils.toString(urlEncodedFormEntity);
        HttpGet httpGet = new HttpGet( requestUrl+"?"+paramStr);
        addHeads(httpGet, paramHeads);
        return get(httpGet);

    }

    public static String postByStringJson(String content, String requestUrl, Map<String, String> paramHeads) {
        HttpPost httpPost = new HttpPost( requestUrl);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        addHeads(httpPost, paramHeads);

        // 解决中文乱码问题
        StringEntity stringEntity = new StringEntity(content, "UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        httpPost.setEntity(stringEntity);
        return post(httpPost);

    }

    public static String postByForm(Map<String, Object> paramsMap, String requestUrl, Map<String, String> paramHeads) {
        HttpPost httpPost = new HttpPost( requestUrl);
        try {
            addHeads(httpPost, paramHeads);
            //将参数进行编码为合适的格式,如将键值对编码为param1=value1&param2=value2
            UrlEncodedFormEntity urlEncodedFormEntity = addParams(paramsMap);
            httpPost.setEntity(urlEncodedFormEntity);
            return post(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String post(HttpPost httpPost) {
        CloseableHttpClient closeableHttpClient = getClient();
        try {
            //执行 post请求
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
            String strRequest = "";
            if (null != closeableHttpResponse && !"".equals(closeableHttpResponse)) {
                System.out.println(closeableHttpResponse.getStatusLine().getStatusCode());
                if (closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity httpEntity = closeableHttpResponse.getEntity();
                    strRequest = EntityUtils.toString(httpEntity,"utf-8");
                } else {
                    logger.error("error url:"+httpPost.toString());
                    strRequest = "Error Response" + closeableHttpResponse.getStatusLine().getStatusCode();
                }
            }
            return strRequest;

        } catch (ClientProtocolException e) {
            logger.error("协议异常",e);
            return null;
        } catch (ParseException e) {
            logger.error("解析异常",e);
            return null;
        } catch (IOException e) {
            logger.error("传输异常",e);
            return null;
        } finally {
            try {
                if (closeableHttpClient != null) {
                    closeableHttpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static byte[] getByByte(HttpGet httpGet) {
        CloseableHttpClient closeableHttpClient = getClient();
        try {
            //执行 post请求
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
            byte[] result=null;
            if (null != closeableHttpResponse && !"".equals(closeableHttpResponse)) {
                System.out.println(closeableHttpResponse.getStatusLine().getStatusCode());
                if (closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity httpEntity = closeableHttpResponse.getEntity();
                    InputStream inputStream= httpEntity.getContent();
                    int bytes = 0;
                    byte[] bufferOut = new byte[4096];
                    ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
                    while ((bytes = inputStream.read(bufferOut)) != -1) {
                        bOutput.write(bufferOut, 0, bytes);
                    }
                    inputStream.close();
                    result= bOutput.toByteArray();
                } else {
                    logger.error("error url:"+httpGet.toString());
                }
            }
            return result;

        } catch (ClientProtocolException e) {
            logger.error("协议异常",e);
            return null;
        } catch (ParseException e) {
            logger.error("解析异常",e);
            return null;
        } catch (IOException e) {
            logger.error("传输异常",e);
            return null;
        } finally {
            try {
                if (closeableHttpClient != null) {
                    closeableHttpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static String get(HttpGet httpGet) {
        CloseableHttpClient closeableHttpClient = getClient();
        try {
            //执行 post请求
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
            String result=null;
            if (null != closeableHttpResponse && !"".equals(closeableHttpResponse)) {
                System.out.println(closeableHttpResponse.getStatusLine().getStatusCode());
                if (closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity httpEntity = closeableHttpResponse.getEntity();
                    result=  EntityUtils.toString(httpEntity, "UTF-8");
                } else {
                    logger.error("error url:"+httpGet.toString());
                }
            }
            return result;
        } catch (ClientProtocolException e) {
            logger.error("协议异常",e);
            return null;
        } catch (ParseException e) {
            logger.error("解析异常",e);
            return null;
        } catch (IOException e) {
            logger.error("传输异常",e);
            return null;
        } finally {
            try {
                if (closeableHttpClient != null) {
                    closeableHttpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static String md5(byte[] bs) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(bs);
        String hex = new BigInteger(1, digest.digest()).toString(16);
        // 补齐BigInteger省略的前置0
        return new String(new char[32 - hex.length()]).replace("\0", "0") + hex;
    }
}


