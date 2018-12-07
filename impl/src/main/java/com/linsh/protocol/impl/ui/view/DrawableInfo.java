package com.linsh.protocol.impl.ui.view;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.linsh.protocol.Client;
import com.linsh.utilseverywhere.BackgroundUtils;
import com.linsh.utilseverywhere.SDCardUtils;

import java.io.File;
import java.lang.reflect.Type;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/03
 *    desc   :
 * </pre>
 */
public class DrawableInfo {

    public ColorInfo color;
    public String url;
    public String path;
    public String drawable;
    public ShapeInfo shape;
    public Object[][] states;

    public void setBackground(View view) {
        if (color != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(color.getStateListDrawable());
            }
        } else if (url != null) {
            Client.image().with(url).loadBg(view);
        } else if (path != null) {
            Client.image().with(path).loadBg(view);
        } else if (drawable != null) {
            File file = new File(Client.config().ui().resDir(), "drawable/" + drawable);
            Client.image().with(file).loadBg(view);
        } else if (shape != null) {
            BackgroundUtils.setBackground(view, shape.getDrawable());
        }
    }

    public void setImage(ImageView view) {
        if (color != null) {
            view.setImageDrawable(color.getStateListDrawable());
        } else if (url != null) {
            Client.image().load(url, view);
        } else if (path != null) {
            Client.image().load(path, view);
        } else if (drawable != null) {
            File file = new File(Client.config().ui().resDir(), "drawable/" + drawable);
            Client.image().load(file, view);
        } else if (shape != null) {
            view.setImageDrawable(shape.getDrawable());
        }
    }

    public static class ShapeInfo {

        public int shape;
        public int color;
        public StrokeInfo stroke;

        private Drawable getDrawable() {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(shape);
            drawable.setColor(color);
            if (stroke != null) {
                drawable.setStroke(stroke.width, stroke.color, stroke.dashWidth, stroke.dashGap);
            }
            return drawable;
        }
    }

    public static class StrokeInfo {
        public int width;
        public int color;
        public int dashWidth;
        public int dashGap;
    }

    static class TypeAdapter implements JsonDeserializer<DrawableInfo> {

        @Override
        public DrawableInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json == null || json.isJsonNull())
                return null;
            if (json.isJsonPrimitive()) {
                DrawableInfo info = new DrawableInfo();
                JsonPrimitive primitive = json.getAsJsonPrimitive();
                if (primitive.isNumber()) {
                    info.color = new ColorInfo(primitive.getAsInt());
                    return info;
                } else if (primitive.isString()) {
                    String value = primitive.getAsString();
                    if (value.matches("(#|0x).+")) {
                        info.color = new ColorInfo(ColorUtils.parseColor(value));
                        return info;
                    } else if (value.startsWith("color.")) {
                        info.color = new ColorInfo(JsonResource.getColor(value.substring("color.".length())));
                        return info;
                    } else if (value.startsWith("drawable.")) {
                        info.drawable = value.substring("drawable.".length());
                        return info;
                    } else if (value.startsWith("http")) {
                        info.url = value;
                        return info;
                    } else if (value.startsWith("sdcard/") || value.startsWith(SDCardUtils.getRootDirectory().getAbsolutePath())) {
                        info.path = value;
                        return info;
                    }
                }
            }
            return GsonFactory.getDefault().fromJson(json, DrawableInfo.class);
        }
    }
}
