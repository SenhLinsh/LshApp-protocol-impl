package com.linsh.protocol.impl.ui.view;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/03
 *    desc   :
 * </pre>
 */
public class ColorInfo {

    public int color;
    public String[][] states;

    public ColorInfo() {
    }

    public ColorInfo(int color) {
        this.color = color;
    }

    public Drawable getStateListDrawable() {
        if (states != null && states.length > 0) {
            StateListDrawable drawable = new StateListDrawable();
            for (int i = 0; i < states.length; i++) {
                String[] state = states[i];
                if (state.length >= 1) {
                    int[] colorStates = new int[state.length - 1];
                    for (int j = 0; j < state.length - 1; j++) {
                        colorStates[j] = getState(state[j]);
                    }
                    drawable.addState(colorStates, new ColorDrawable(ColorUtils.parseColor(state[state.length - 1])));
                }
            }
            return drawable;
        }
        return new ColorDrawable(color);
    }

    public ColorStateList getColorStateList() {
        if (states != null && states.length > 0) {
            int[][] colorStates = new int[states.length][];
            int[] colors = new int[states.length];
            for (int i = 0; i < states.length; i++) {
                String[] state = states[i];
                if (state.length >= 1) {
                    colorStates[i] = new int[state.length - 1];
                    for (int j = 0; j < state.length - 1; j++) {
                        colorStates[i][j] = getState(state[j]);
                    }
                    colors[i] = ColorUtils.parseColor(state[state.length - 1]);
                }
            }
            return new ColorStateList(colorStates, colors);
        }
        return ColorStateList.valueOf(color);
    }

    private int getState(String value) {
        switch (value) {
            case "selected":
                return android.R.attr.state_selected;
            case "enabled":
                return android.R.attr.state_enabled;
            case "pressed":
                return android.R.attr.state_pressed;
            case "checked":
                return android.R.attr.state_checked;
            case "focused":
                return android.R.attr.state_focused;
        }
        throw new IllegalArgumentException("无法识别的 color state: " + value);
    }

    static class TypeAdapter implements JsonDeserializer<ColorInfo> {

        @Override
        public ColorInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json == null || json.isJsonNull())
                return null;
            if (json.isJsonPrimitive()) {
                JsonPrimitive primitive = json.getAsJsonPrimitive();
                if (primitive.isNumber()) {
                    return new ColorInfo(primitive.getAsInt());
                } else if (primitive.isString()) {
                    String value = primitive.getAsString();
                    if (value.matches("(#|0x).+")) {
                        return new ColorInfo(ColorUtils.parseColor(value));
                    } else if (value.startsWith("color.")) {
                        return new ColorInfo(JsonResource.getColor(value.substring("color.".length())));
                    }
                }
            } else if (json.isJsonObject()) {
                return GsonFactory.getDefault().fromJson(json, ColorInfo.class);
            }
            return null;
        }
    }
}
