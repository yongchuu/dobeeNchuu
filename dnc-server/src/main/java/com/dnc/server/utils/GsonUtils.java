package com.dnc.server.utils;

import com.dnc.server.oauth.dto.MemberDto;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Getter;

import java.lang.reflect.Type;

abstract public class GsonUtils {
    private static Gson gson;
    private static JsonParser parser;

    static{
        gson = new Gson();
        parser = new JsonParser();
    }

    public static <T> T fromJson(String jsonString, Class<T> classOfT){
        return gson.fromJson(jsonString, classOfT);
    }

    public static String getAsString(String jsonString, String key){
        JsonElement element = parser.parse(jsonString);

        return element.getAsJsonObject().get(key).getAsString();
    }

}
