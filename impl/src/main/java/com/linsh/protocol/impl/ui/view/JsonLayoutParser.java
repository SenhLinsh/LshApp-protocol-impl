package com.linsh.protocol.impl.ui.view;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.linsh.utilseverywhere.SDCardUtils;
import com.linsh.utilseverywhere.UnitConverseUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/06
 *    desc   :
 * </pre>
 */
public class JsonLayoutParser {

    static int getSize(JsonElement element, int defaultValue) {
        if (element == null || element.isJsonNull())
            return defaultValue;
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                return primitive.getAsInt();
            } else if (primitive.isString()) {
                String size = primitive.getAsString();
                if ("MATCH_PARENT".equals(size) || "match_parent".equals(size))
                    return ViewGroup.LayoutParams.MATCH_PARENT;
                if (size.matches("\\d+(px|Px|PX)*"))
                    return Integer.parseInt(size.replaceAll("px|Px|PX", ""));
                if (size.matches("\\d+(dp|dip|Dp|Dip|DIP)")) {
                    int dp = Integer.parseInt(size.replaceAll("dp|dip|Dp|Dip|DIP", ""));
                    return UnitConverseUtils.dp2px(dp);
                }
                if (size.matches("\\d+(sp|Sp|SP)")) {
                    int sp = Integer.parseInt(size.replaceAll("sp|Sp|SP", ""));
                    return UnitConverseUtils.sp2px(sp);
                }
                switch (size.toUpperCase()) {
                    case "WRAP_CONTENT":
                    case "WRAPCONTENT":
                    case "WC":
                    case "W":
                        return ViewGroup.LayoutParams.WRAP_CONTENT;
                    case "MATCH_PARENT":
                    case "MATCHPARENT":
                    case "MP":
                    case "M":
                        return ViewGroup.LayoutParams.MATCH_PARENT;
                }
                throw new IllegalArgumentException("无法解析尺寸: " + size);
            }
        }
        throw new IllegalArgumentException("无法将 " + element.toString() + " 解析成 Size 值");
    }

    public static int[] getSizeArray(JsonElement element, int defaultValue) {
        if (element == null || element.isJsonNull())
            return null;
        if (element.isJsonArray()) {
            JsonArray jsonArray = element.getAsJsonArray();
            int[] sizes = new int[4];
            for (int i = 0; i < Math.min(jsonArray.size(), 4); i++) {
                sizes[i] = getSize(jsonArray.get(i), defaultValue);
            }
            return sizes;
        }
        return null;
    }

    public static Object getColor(JsonElement element, JsonDeserializationContext context) {
        if (element == null || element.isJsonNull())
            return null;
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                return primitive.getAsInt();
            } else if (primitive.isString()) {
                String value = primitive.getAsString();
                if (value.matches("(#|0x).+")) {
                    return ColorUtils.parseColor(value);
                } else if (value.startsWith("color.")) {
                    return JsonResource.getColor(value.substring("color.".length()));
                }
            }
        } else if (element.isJsonObject()) {
            return context.deserialize(element, ColorInfo.class);
        }
        return null;
    }

    public static int getVisibility(JsonElement element, int visibility) {
        if (element == null || element.isJsonNull())
            return visibility;
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                return primitive.getAsInt();
            } else if (primitive.isString()) {
                String value = primitive.getAsString();
                switch (value.toUpperCase()) {
                    case "GONE":
                    case "G":
                        return View.GONE;
                    case "INVISIBLE":
                    case "IV":
                    case "I":
                        return View.INVISIBLE;
                    default:
                        return View.VISIBLE;
                }
            }
        }
        throw new IllegalArgumentException("无法将 " + element.toString() + " 解析成 Visibility 值");
    }

    public static int getGravity(JsonElement element, int defaultValue) {
        if (element == null || element.isJsonNull())
            return defaultValue;
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                switch (value.toUpperCase()) {
                    case "CENTER":
                    case "C":
                        return Gravity.CENTER;
                    case "CENTER_HORIZONTAL":
                    case "CENTERHORIZONTAL":
                    case "CH":
                        return Gravity.CENTER_HORIZONTAL;
                    case "CENTER_VERTICAL":
                    case "CENTERVERTICAL":
                    case "CV":
                        return Gravity.CENTER_VERTICAL;
                    case "LEFT":
                    case "L":
                        return Gravity.LEFT;
                    case "TOP":
                    case "T":
                        return Gravity.TOP;
                    case "RIGHT":
                    case "R":
                        return Gravity.RIGHT;
                    case "BOTTOM":
                    case "B":
                        return Gravity.BOTTOM;
                    case "START":
                    case "S":
                        return Gravity.START;
                    case "END":
                    case "E":
                        return Gravity.END;
                }
            }
        }
        throw new IllegalArgumentException("无法将 " + element.toString() + " 解析成 Gravity 值");
    }

    public static int getOrientation(JsonElement element, int defaultValue) {
        if (element == null || element.isJsonNull())
            return defaultValue;
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                return primitive.getAsInt();
            } else if (primitive.isString()) {
                String value = primitive.getAsString();
                switch (value.toUpperCase()) {
                    case "HORIZONTAL":
                    case "H":
                        return LinearLayout.HORIZONTAL;
                    case "VERTICAL":
                    case "V":
                        return LinearLayout.VERTICAL;
                }
            }
        }
        throw new IllegalArgumentException("无法将 " + element.toString() + " 解析成 Orientation 值");
    }

    public static ImageView.ScaleType getScaleType(JsonElement element) {
        if (element == null || element.isJsonNull())
            return null;
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                switch (value.toUpperCase()) {
                    case "CENTER":
                    case "C":
                        return ImageView.ScaleType.CENTER;
                    case "CENTER_CROP":
                    case "CENTERCROP":
                    case "CC":
                        return ImageView.ScaleType.CENTER_CROP;
                    case "CENTER_INSIDE":
                    case "CENTERINSIDE":
                    case "CI":
                        return ImageView.ScaleType.CENTER_INSIDE;
                    case "FIT_CENTER":
                    case "FITCENTER":
                    case "FC":
                        return ImageView.ScaleType.FIT_CENTER;
                    case "FIT_XY":
                    case "FITXY":
                    case "FXY":
                    case "FX":
                        return ImageView.ScaleType.FIT_XY;
                    case "FIT_START":
                    case "FITSTART":
                    case "FS":
                        return ImageView.ScaleType.FIT_START;
                    case "FIT_END":
                    case "FITEND":
                    case "FE":
                        return ImageView.ScaleType.FIT_END;
                    case "MATRIX":
                    case "M":
                        return ImageView.ScaleType.MATRIX;
                }
            }
        }
        throw new IllegalArgumentException("无法将 " + element.toString() + " 解析成 ImageView.ScaleType");
    }

    public static Object getBackground(JsonElement element, JsonDeserializationContext context) {
        if (element == null || element.isJsonNull())
            return null;
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                return primitive.getAsInt();
            } else if (primitive.isString()) {
                String value = primitive.getAsString();
                if (value.matches("(#|0x).+")) {
                    return ColorUtils.parseColor(value);
                } else if (value.startsWith("color.")) {
                    return JsonResource.getColor(value.substring("color.".length()));
                } else if (value.startsWith("drawable.")) {
                    DrawableInfo info = new DrawableInfo();
                    info.drawable = value.substring("drawable.".length());
                    return info;
                } else if (value.startsWith("http")) {
                    DrawableInfo info = new DrawableInfo();
                    info.url = value;
                    return info;
                } else if (value.startsWith("sdcard/") || value.startsWith(SDCardUtils.getRootDirectory().getAbsolutePath())) {
                    DrawableInfo info = new DrawableInfo();
                    info.path = value;
                    return info;
                }
            }
        } else if (element.isJsonObject()) {
            return context.deserialize(element, DrawableInfo.class);
        }
        return null;
    }

    public static ProtocolInfo getProtocol(JsonElement element, JsonDeserializationContext context) {
        if (element == null || element.isJsonNull())
            return null;
        if (element.isJsonObject()) {
            return context.deserialize(element, ProtocolInfo.class);
        }
        return null;
    }

    public static ViewGroupInfo.LayoutParamsInfo getLayoutParamsInfo(JsonObject jsonObject, String fieldName, ViewInfo parent) {
        ViewGroupInfo.LayoutParamsInfo layoutParamsInfo = InfoIdentifier.getLayoutParamsInfo(parent);
        JsonElement element = jsonObject.get(fieldName);
        if (element != null && element.isJsonObject()) {
            layoutParamsInfo.onDeserialize(element.getAsJsonObject());
        } else {
            layoutParamsInfo.onDeserialize(jsonObject);
        }
        return layoutParamsInfo;
    }
}
