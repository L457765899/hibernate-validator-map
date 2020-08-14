package com.sxb.lin.hibernate.validator.test.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RetUtil {

    /**
     * 访问正确
     */
    public static final int ReturnCodeValue_Normal = 200;

    /**
     * 服务器错误
     */
    public static final int ReturnCodeValue_ServerEorror = 500;

    /**
     * 生成自定义的tokenId 作为用户的唯一标识tokenId (32位)
     ***/
    public static String formTokenId() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    public static Map<String, Object> getRetValue(boolean b, Object datas, String msg, int code) {
        Map<String, Object> retMp = new HashMap<String, Object>();
        retMp.put("success", b);
        retMp.put("errMsg", msg);
        retMp.put("code", code);
        retMp.put("datas", datas);
        return retMp;
    }

    public static Map<String, Object> getRetValue(boolean b, Object datas) {
        Map<String, Object> retMp = new HashMap<String, Object>();
        retMp.put("success", b);
        retMp.put("errMsg", "");
        retMp.put("code", ReturnCodeValue_Normal);
        retMp.put("datas", datas);
        return retMp;
    }

    public static Map<String, Object> getRetValue(boolean b, Object datas, String msg) {
        Map<String, Object> retMp = new HashMap<String, Object>();
        retMp.put("success", b);
        retMp.put("errMsg", msg);
        retMp.put("code", ReturnCodeValue_Normal);
        retMp.put("datas", datas);
        return retMp;
    }


    public static Map<String, Object> getRetValue(Object datas) {
        Map<String, Object> retMp = new HashMap<String, Object>();
        retMp.put("success", true);
        retMp.put("errMsg", "");
        retMp.put("code", ReturnCodeValue_Normal);
        retMp.put("datas", datas);
        return retMp;
    }


    public static Map<String, Object> getRetValue(boolean b) {
        Map<String, Object> retMp = new HashMap<String, Object>();
        retMp.put("success", b);
        retMp.put("errMsg", "");
        retMp.put("code", ReturnCodeValue_Normal);
        retMp.put("datas", new HashMap<String, Object>());
        return retMp;
    }


    public static Map<String, Object> getRetValue(boolean b, String msg) {
        Map<String, Object> retMp = new HashMap<String, Object>();
        retMp.put("success", b);
        retMp.put("errMsg", msg);
        retMp.put("code", ReturnCodeValue_Normal);
        retMp.put("datas", new HashMap<String, Object>());
        return retMp;
    }


    public static Map<String, Object> getRetValue(boolean b, String msg, int code) {
        Map<String, Object> retMp = new HashMap<String, Object>();
        retMp.put("success", b);
        retMp.put("errMsg", msg);
        retMp.put("code", code);
        retMp.put("datas", new HashMap<String, Object>());
        return retMp;
    }
}
