package com.example.demo.toolkits;


//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
//import com.eve.wms.common.exception.ServiceException;
//import com.eve.wms.common.utils.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gai
 * @date 2022-11-25 14:42
 * @desc
 **/
public class EveJsonUtil {

    /**
     * json转Map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(JSONObject json) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }


    /**
     * 校验json串字段是否为空
     *
     * @param json
     * @param key
     * @return
     */
    public static Object getObject(JSONObject json, String key) {
        if (StringUtils.isEmpty(json) || StringUtils.isEmpty(key)) {
            try {
                throw new Exception("json与key不能为空！");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (json.get(key) == null) {
            try {
                throw new Exception(key + "不能为空！");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return json.get(key);
    }

    /**
     * 校验json串字段是否为空
     *
     * @param json
     * @param key
     * @return
     */
    public static JSONArray getJSONArray(JSONObject json, String key) {
        getObject(json, key);
        return json.getJSONArray(key);
    }

    /**
     * 校验json串字段是否为空
     *
     * @param json
     * @param key
     * @return
     */
    public static JSONObject getJSONObject(JSONObject json, String key) {
        getObject(json, key);
        return json.getJSONObject(key);
    }

    /**
     * 校验json串字段是否为空
     *
     * @param json
     * @param key
     * @return
     */
    public static String getString(JSONObject json, String key) {
        return getObject(json, key).toString();
    }

    public static BigDecimal getBigDecimal(JSONObject json, String key) {
        return new BigDecimal(getObject(json, key).toString());
    }

    /**
     * 返回错误报文
     *
     * @param message
     * @return
     */
    public static JSONObject error(String message) {
        JSONObject returnJson = new JSONObject();
        returnJson.put("result", false);
        returnJson.put("code", "500");
        returnJson.put("message", message);
        return returnJson;
    }

    /**
     * 返回正常报文
     *
     * @param message
     * @return
     */
    public static JSONObject success(String message) {
        return success(message, "200", null);
    }

    /**
     * @param message
     * @param code
     * @param data
     * @return
     */
    public static JSONObject success(String message, String code, Object data) {
        JSONObject returnJson = new JSONObject();
        returnJson.put("result", true);
        returnJson.put("code", code);
        returnJson.put("data", data);
        returnJson.put("message", message);
        return returnJson;
    }

//    private static ValueFilter filter = new ValueFilter() {
//        @Override
//        public Object process(Object obj, String s, Object v) {
//            if (v == null)
//                return "";
//            return v;
//        }
//    };


    /**
     * 返回json字符串
     *
     * @param jsonObject
     * @return
     */
//    public static String toJSONString(JSONObject jsonObject) {
//        return com.alibaba.fastjson.JSON.toJSONString(jsonObject, filter,
//                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
//    }

    /**
     * 返回富勒正确报文
     *
     * @param message
     * @return
     */
    public static JSONObject fluxSuccess(String message) {
        JSONObject headerJson = new JSONObject();
        JSONObject resJson = new JSONObject();
        resJson.put("returnFlag", "1");
        resJson.put("returnCode", "0000");
        resJson.put("returnDesc", StringUtils.nvl(message, "消息处理成功"));
        resJson.put("resultInfo", new JSONArray());
        headerJson.put("Response", resJson);
        return headerJson;
    }

    /**
     * 返回富勒错误报文
     *
     * @param message
     * @return
     */
    public static JSONObject fluxError(String message) {
        JSONObject headerJson = new JSONObject();
        JSONObject resJson = new JSONObject();
        resJson.put("returnFlag", "0");
        resJson.put("returnCode", "999");
        resJson.put("returnDesc", message);
        resJson.put("resultInfo", new JSONArray());
        headerJson.put("Response", resJson);
        return headerJson;
    }
}
