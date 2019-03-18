package com.demo.webutils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 18:01
 * @version: V1.0
 * @detail: ession,Cookie工具类
 **/
public class WebUtils {
    /**
     * 获得对应key的cookie 无则null
     *
     * @param key cookiekey
     * @return
     */
    public static String getCookieVal(String key) {
        return getCookie(key) == null ? null : getCookie(key).getValue();
    }

    public static Cookie getCookie(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request == null || request.getCookies() == null) {
            return null;
        }
        for (Cookie c : request.getCookies()) {
            if (key.equals(c.getName()))
                return c;
        }
        return null;
    }

    /**
     * 保存用户缓存
     *
     * @param key key
     * @param val value
     */
    public static void setSession(String key, Object val) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute(key, val);
    }

    /**
     * 删除用户缓存
     *
     * @param key key
     */
    public static void removeSession(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().removeAttribute(key);
    }

    /**
     * 获得用户缓存
     *
     * @param key key
     */
    public static <T> T getSession(String key) {
        // 过期时间30分钟
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (T) request.getSession().getAttribute(key);
    }


    /**
     * 获取参数
     *
     * @param key key
     */
    public static String getParameter(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getParameter(key);
    }
}
