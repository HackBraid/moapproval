package com.longfor.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpUtils {

    /**
     *获取数据的post请求
     * @param url
     * @param map
     * @return
     */
    public static String getData(String url, Map<String, Object> map,Map<String, Object> mapHeader) throws  Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //遍历map数据
            for (Entry<String, Object> entry : map.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            //设置头部
            for (Entry<String, Object> entry : mapHeader.entrySet()) {
                post.setHeader(entry.getKey(), entry.getValue().toString());
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            post.setEntity(entity);
            HttpResponse response = httpclient.execute(post);
            HttpEntity entity2 = response.getEntity();
            return EntityUtils.toString(entity2, "utf-8");
        }finally {
            post.releaseConnection();
        }
    }


    /**
     *获取数据的post请求 参数是json数据
     * @param url
     * @return
     */
    public static String getDataByJson(String url, String json,Map<String, Object> mapHeader)throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        try {
            StringEntity entity = new StringEntity(json,"utf-8");//解决中文乱码问题    解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            //设置头部
            if(mapHeader!=null){
                for (Entry<String, Object> entry : mapHeader.entrySet()) {
                    post.setHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            post.setEntity(entity);
            HttpResponse response = httpclient.execute(post);
            HttpEntity entity2 = response.getEntity();
            return EntityUtils.toString(entity2, "utf-8");
        }finally {
            post.releaseConnection();
        }
    }


}
