package com.dnc.server.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

abstract public class CookieUtils {
    public static final String SESSION_KEY = "DNC_SESSION_ID";

    public static Map<String, String> getCookieMap(HttpServletRequest req){
        Map<String, String> cookieMap = new HashMap<>();

        Cookie[] cookies = req.getCookies();

        if(cookies != null){
            for(int i = 0; i < cookies.length; i++){
                cookieMap.put(cookies[i].getName(), cookies[i].getValue());
            }
        }

        return cookieMap;
    }

    public static Cookie makeCookie(String name, String value){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(60*60);

        return cookie;
    }
}
