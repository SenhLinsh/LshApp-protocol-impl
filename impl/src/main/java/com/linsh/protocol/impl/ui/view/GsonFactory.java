package com.linsh.protocol.impl.ui.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/06
 *    desc   :
 * </pre>
 */
class GsonFactory {

    private static Gson defaultGson;
    private static Gson infoGson;

    public static Gson getDefault() {
        if (defaultGson == null) {
            defaultGson = new Gson();
        }
        return defaultGson;
    }

    public static Gson parseInfo() {
        if (infoGson == null) {
            infoGson = new GsonBuilder()
                    .registerTypeAdapter(ViewInfo.class, new ViewInfoTypeAdapter())
                    .registerTypeAdapter(DrawableInfo.class, new DrawableInfo.TypeAdapter())
                    .registerTypeAdapter(ColorInfo.class, new ColorInfo.TypeAdapter())
                    .create();
        }
        return infoGson;
    }
}
