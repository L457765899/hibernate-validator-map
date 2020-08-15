package com.sxb.lin.hibernate.validator.test.client;

import com.google.gson.Gson;
import com.sxb.lin.hibernate.validator.test.dto.AdminDto;
import com.sxb.lin.hibernate.validator.test.dto.ParamDto;
import com.sxb.lin.hibernate.validator.test.dto.UserDto;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.Date;
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
        //param.put("id", 1);
        param.put("name", "张三");
        param.put("level", 99);
        param.put("number", 11.11);
        String json = new Gson().toJson(param);
        String post = this.post("http://localhost:8080/test/validator/validMap.json", null, json);
        System.out.println(post);
    }

    @Test
    public void validParamMap() {
        Map<String, Object> param = new HashMap<String, Object>();
        //param.put("id", 1);
        param.put("name", "张三");
        param.put("level", 99);
        param.put("number", 11.11);

        ParamDto<Map<String, Object>> paramDto = new ParamDto<Map<String, Object>>();
        paramDto.setUserId("102");
        //paramDto.setToken("qwertyuiop");
        paramDto.setParam(param);

        String json = new Gson().toJson(paramDto);
        String post = this.post("http://localhost:8080/test/validator/validParamMap.json", null, json);
        System.out.println(post);
    }

    @Test
    public void validObject() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("李四");

        String json = new Gson().toJson(userDto);
        String post = this.post("http://localhost:8080/test/validator/validObject.json", null, json);
        System.out.println(post);
    }

    @Test
    public void validParamObject() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("李四");

        ParamDto<UserDto> paramDto = new ParamDto<UserDto>();
        paramDto.setUserId("102");
        //paramDto.setToken("qwertyuiop");
        paramDto.setParam(userDto);

        String json = new Gson().toJson(paramDto);
        String post = this.post("http://localhost:8080/test/validator/validParamObject.json", null, json);
        System.out.println(post);
    }

    @Test
    public void customValidMap() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", 1);
        param.put("name", "王五");

        param.put("url", "www.baidu.com");
        //param.put("url", "http://www.baidu.com");

        param.put("json", "json");
        //param.put("json", "[]");
        param.put("type", 1);

        param.put("content", "12345");
        //param.put("content", "abcde");

        String json = new Gson().toJson(param);
        String post = this.post("http://localhost:8080/test/validator/customValidMap.json", null, json);
        System.out.println(post);
    }

    @Test
    public void customValidParamMap() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", 1);
        param.put("name", "王五");

        param.put("url", "www.baidu.com");
        //param.put("url", "http://www.baidu.com");

        param.put("json", "json");
        //param.put("json", "[]");
        param.put("type", 1);

        //param.put("content", "12345");
        param.put("content", "abcde");

        ParamDto<Map<String, Object>> paramDto = new ParamDto<Map<String, Object>>();
        paramDto.setParam(param);

        String json = new Gson().toJson(paramDto);
        String post = this.post("http://localhost:8080/test/validator/customValidParamMap.json", null, json);
        System.out.println(post);
    }

    @Test
    public void customValidObject() {
        AdminDto adminDto = new AdminDto();
        adminDto.setId(1);
        adminDto.setUrl("www.baidu.com");
        adminDto.setJson("{}");
        adminDto.setType(1);
        //adminDto.setContent("123456");
        adminDto.setContent("abcde");
        String json = new Gson().toJson(adminDto);
        String post = this.post("http://localhost:8080/test/validator/customValidObject.json", null, json);
        System.out.println(post);
    }

    @Test
    public void customValidParamObject() {

    }
}
