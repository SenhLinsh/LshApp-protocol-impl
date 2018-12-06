package com.linsh.protocol.impl.ui.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/06
 *    desc   :
 * </pre>
 */
public class JsonObjectUtils {

    public static String getString(JsonElement element) {
        if (element != null && element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                return primitive.getAsString();
            }
        }
        return null;
    }

    public static int getInt(JsonElement element, int defaultValue) {
        if (element != null && element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                return primitive.getAsInt();
            }
        }
        return defaultValue;
    }

    public static float getFloat(JsonElement element, float defaultValue) {
        if (element != null && element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                return primitive.getAsFloat();
            }
        }
        return defaultValue;
    }

    public static boolean getBoolean(JsonElement element) {
        if (element != null && element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            }
        }
        return false;
    }
}
