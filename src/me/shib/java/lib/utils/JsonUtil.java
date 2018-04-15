package me.shib.java.lib.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public final class JsonUtil {

    private Gson gson;
    private Gson prettyGson;
    private Gson upperCamelCaseGson;

    public JsonUtil() {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    private Gson getPrettyGson() {
        if (null == prettyGson) {
            prettyGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        }
        return prettyGson;
    }

    private Gson getUpperCamelCaseGson() {
        if (null == upperCamelCaseGson) {
            upperCamelCaseGson = new GsonBuilder().disableHtmlEscaping()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        }
        return upperCamelCaseGson;
    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public <T> T fromUpperCamelCaseJson(String json, Class<T> classOfT) {
        return getUpperCamelCaseGson().fromJson(json, classOfT);
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public String toPrettyJson(Object object) {
        return getPrettyGson().toJson(object);
    }

}
