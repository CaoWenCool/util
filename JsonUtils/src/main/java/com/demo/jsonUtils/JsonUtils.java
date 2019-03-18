package com.demo.jsonUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 15:05
 * @version: V1.0
 * @detail:
 **/
public class JsonUtils {
    private static final Logger log = Logger.getLogger(JsonUtils.class);

    final static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * JSON串转换为Java泛型对象
     *
     * @param <T>
     * @param jsonString JSON字符串
     * @param tr         TypeReference,例如: new TypeReference< List<FamousUser> >(){}
     * @return List对象列表
     */
    public static <T> T toGenericObject(String jsonString, TypeReference<T> tr) {

        if (jsonString == null || "".equals(jsonString)) {
            return null;
        } else {
            try {
                return (T) objectMapper.readValue(jsonString, tr);
            } catch (Exception e) {
                log.warn(jsonString);
                log.warn("json error:" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Json字符串转Java对象
     *
     * @param jsonString
     * @param c
     * @return
     */
    public static Object toObject(String jsonString, Class<?> c) {

        if (jsonString == null || "".equals(jsonString)) {
            return "";
        } else {
            try {
                return objectMapper.readValue(jsonString, c);
            } catch (Exception e) {
                log.warn("json error:" + e.getMessage());
            }
        }
        return "";
    }

    /**
     * Java对象转Json字符串
     *
     * @param object Java对象，可以是对象，数组，List,Map等
     * @return json 字符串
     */
    public static String toJson(Object object) {
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.warn("json error:" + e.getMessage());
        }
        return jsonString;
    }
}
