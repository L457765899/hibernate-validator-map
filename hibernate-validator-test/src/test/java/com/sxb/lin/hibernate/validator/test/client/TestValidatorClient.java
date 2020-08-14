package com.sxb.lin.hibernate.validator.test.client;

import com.google.gson.Gson;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestValidatorClient {

    private String post(String urlPath, Map<String, Object> headers, String param){
        HttpPost httpPost = new HttpPost(urlPath);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(10000)
                .build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        CloseableHttpClient client = HttpClients.createDefault();

        if(headers != null && headers.size() > 0){
            Set<Map.Entry<String, Object>> headerEntrySet = headers.entrySet();
            for(Map.Entry<String, Object> entry : headerEntrySet){
                httpPost.setHeader(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
            }
        }

        StringEntity entity = new StringEntity(param,"utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        try {
            CloseableHttpResponse response = client.execute(httpPost);
            if(response.getStatusLine().getStatusCode() != 200) {
                System.err.println(urlPath + " statusCode:" + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            }
            return EntityUtils.toString(response.getEntity(),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void validMap() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", 1);
        param.put("name", "张三");
        param.put("level", 99);
        param.put("number", 11.11);
        String json = new Gson().toJson(param);
        String post = this.post("http://localhost:8080/test/validator/validMap.json", null, json);
        System.out.println(post);
    }
}
