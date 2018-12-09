package com.linsh.protocol.impl.ui.view;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/06
 *    desc   :
 * </pre>
 */
public class ViewInfoTypeAdapter implements JsonDeserializer<ViewInfo> {

    @Override
    public ViewInfo deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (element == null) return null;
        JsonObject jsonObject = element.getAsJsonObject();
        ViewInfo viewInfo = InfoIdentifier.getViewInfo(jsonObject);
        viewInfo.layoutParams = JsonLayoutParser.getLayoutParamsInfo(jsonObject, null);
        viewInfo.onDeserialize(jsonObject, context, null);
        return viewInfo;
    }
}
