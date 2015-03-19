package com.spark.util;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonUtil {
    private static final Gson gson = new Gson();

    private static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}
