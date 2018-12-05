package com.linsh.protocol.impl.ui.view.entity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.linsh.utilseverywhere.ClassUtils;
import com.linsh.utilseverywhere.UnitConverseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/29
 *    desc   :
 * </pre>
 */
class JsonLayoutParser {

    static ViewInfo parse(String json) {
        try {
            return parse(new JSONObject(json), null);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static ViewInfo parse(JSONObject object, ViewInfo parent) throws JSONException {
        ViewInfo info = getViewInfo(object.optString("name"));
        info.onDeserialize(object, parent);
        return info;
    }

    @NonNull
    private static ViewInfo getViewInfo(String name) {
        String className = adaptName(name);
        try {
            ViewInfo info = null;
            Class<? extends View> viewClass = (Class<? extends View>) Class.forName(className);
            Class clazz = viewClass;
            while (clazz != null) {
                Class<? extends ViewInfo> viewInfoClass = Build.VIEW_INFO_BUILDING.get(clazz);
                if (viewInfoClass != null) {
                    try {
                        info = (ViewInfo) ClassUtils.newInstance(viewInfoClass);
                        break;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                clazz = clazz.getSuperclass();
            }
            if (info == null) {
                throw new IllegalArgumentException("无法匹配到指定的 ViewInfo: " + viewClass);
            }
            info.name = viewClass;
            return info;
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("无法解析 View 的类: " + className);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("ViewInfo 指定的类名必须继承自 " + View.class.getName());
        }
    }

    private static String adaptName(String name) {
        if (name == null)
            return null;
        if (name.contains("."))
            return name;
        switch (name) {
            case "View":
                return View.class.getName();
            case "RecyclerView":
                return RecyclerView.class.getName();
        }
        return "android.widget." + name;
    }

    static ViewGroupInfo.LayoutParamsInfo getLayoutParamsInfo(JSONObject object, String fieldName, ViewInfo parent) throws JSONException {
        ViewGroupInfo.LayoutParamsInfo layoutParamsInfo = null;
        if (parent instanceof ViewGroupInfo) {
            if (parent instanceof LinearLayoutInfo) {
                layoutParamsInfo = new LinearLayoutInfo.LayoutParamsInfo();
            }
        }
        if (layoutParamsInfo == null) {
            layoutParamsInfo = new ViewGroupInfo.LayoutParamsInfo();
        }
        JSONObject obj = object.optJSONObject(fieldName);
        if (obj != null) {
            layoutParamsInfo.onDeserialize(obj);
        } else {
            layoutParamsInfo.onDeserialize(object);
        }
        return layoutParamsInfo;
    }

    static ProtocolInfo getProtocol(JSONObject object) throws JSONException {
        JSONObject protocolObj = object.optJSONObject("protocol");
        if (protocolObj == null)
            return null;
        ProtocolInfo protocol = new ProtocolInfo();
        protocol.name = protocolObj.optString("name");
        protocol.key = protocolObj.optString("key");
        JSONObject funcsObj = protocolObj.optJSONObject("funcs");
        if (funcsObj != null) {
            protocol.funcs = new HashMap<>();
            Iterator<String> keys = funcsObj.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                JSONObject funcObj = funcsObj.getJSONObject(next);
                if (funcObj != null) {
                    FuncInfo funcInfo = new FuncInfo();
                    funcInfo.id = funcObj.optString("id");
                    protocol.funcs.put(next, funcInfo);
                }
            }
        }
        return protocol;
    }

    static List<ViewInfo> getChildren(JSONObject object, ViewInfo parent) throws JSONException {
        JSONArray array = object.optJSONArray("children");
        if (array == null)
            return null;
        List<ViewInfo> res = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            res.add(parse(array.optJSONObject(i), parent));
        }
        return res;
    }

    static ImageView.ScaleType getScaleType(Object value) {
        if (value == null)
            return null;
        if (value instanceof String) {
            switch (((String) value).toUpperCase()) {
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
        throw new IllegalArgumentException("无法将 " + value + " 解析成 ImageView.ScaleType");
    }

    static int getOrientation(Object value, int defaultValue) {
        if (value == null)
            return defaultValue;
        if (value instanceof Integer)
            return (int) value;
        if (value instanceof String) {
            switch (((String) value).toUpperCase()) {
                case "HORIZONTAL":
                case "H":
                    return LinearLayout.HORIZONTAL;
                case "VERTICAL":
                case "V":
                    return LinearLayout.VERTICAL;
            }
        }
        throw new IllegalArgumentException("无法将 " + value + " 解析成 Orientation 值");
    }

    static int getGravity(Object value, int defaultValue) {
        if (value == null)
            return defaultValue;
        if (value instanceof Integer)
            return (int) value;
        if (value instanceof String) {
            switch (((String) value).toUpperCase()) {
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
        throw new IllegalArgumentException("无法将 " + value + " 解析成 Gravity 值");
    }

    static int getVisibility(Object value, int defaultValue) {
        if (value == null)
            return defaultValue;
        if (value instanceof Integer)
            return (int) value;
        if (value instanceof String) {
            switch (((String) value).toUpperCase()) {
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
        throw new IllegalArgumentException("无法将 " + value + " 解析成 Visibility 值");
    }

    static DrawableInfo getDrawableInfo(Object value) {
        if (value == null) return null;
        DrawableInfo info = null;
        if (value instanceof Integer) {
            info = new ColorDrawableInfo((Integer) value);
        } else if (value instanceof String) {
            if (((String) value).matches("(#|0x).+")) {
                info = new ColorDrawableInfo(getColor((String) value));
            } else if (((String) value).matches("color\\.[a-zA-Z_]+")) {
                // TODO: 2018/12/5
            } else if (((String) value).matches("drawable\\..+")) {
                // TODO: 2018/12/5
            }
        } else if (value instanceof JSONObject) {
            JSONObject object = (JSONObject) value;
            value = object.opt("color");
            if (value != null) {
                return getDrawableInfo(value);
            }
            value = object.opt("path");
            if (value != null) {
                return new BitmapDrawableInfo(value.toString());
            }
            value = object.opt("state");
            if (value != null) {
                // TODO: 2018/12/5
            }
            value = object;
        }
        if (info != null)
            return info;
        throw new IllegalArgumentException("无法将 " + value + " 解析成 Drawable");
    }

    static int getColor(Object value, int defaultValue) {
        if (value == null)
            return defaultValue;
        if (value instanceof Integer)
            return (int) value;
        if (value instanceof String) {
            return getColor((String) value);
        }
        throw new IllegalArgumentException("无法将 " + value + " 解析成 Color 值");
    }

    private static int getColor(String color) {
        if (color.matches("(#|0x)*[0-9a-fA-F]{3,8}")) {
            color = color.replaceAll("#|0x", "");
            if (color.length() == 3) {
                return parseColor(color, 0, 1, 2, 3);
            } else if (color.length() == 4) {
                return parseColor(color, 1, 2, 3, 4);
            } else if (color.length() == 5) {
                return parseColor(color, 2, 3, 4, 5);
            } else if (color.length() == 6) {
                return parseColor(color, 0, 2, 4, 6);
            } else if (color.length() == 7) {
                return parseColor(color, 1, 3, 5, 7);
            } else {
                return parseColor(color, 2, 4, 6, 8);
            }
        }
        throw new IllegalArgumentException("无法将 " + color + " 解析成 Color 值");
    }

    private static int parseColor(String color, int ai, int ri, int gi, int bi) {
        return Color.argb(ai == 0 ? 0xFF : parseHexStr(color.substring(0, ai)),
                parseHexStr(color.substring(ai, ri)),
                parseHexStr(color.substring(ri, gi)),
                parseHexStr(color.substring(gi, bi)));
    }

    private static int parseHexStr(String hex) {
        hex = hex.toUpperCase();
        int res = 0;
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            if (c >= '0' && c <= '9')
                res = res * 16 + c - '0';
            else if (c >= 'A' && c <= 'F')
                res = res * 16 + 10 + c - 'A';
            else
                throw new NumberFormatException("无法解析字符串: " + hex);
        }
        return res;
    }

    static int[] getSizeArray(JSONArray jsonArray, int defaultValue) throws JSONException {
        if (jsonArray == null)
            return null;
        int[] sizes = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            sizes[i] = getSize(value, defaultValue);
        }
        return sizes;
    }

    static int getSize(Object value, int defaultValue) {
        if (value == null)
            return defaultValue;
        if (value instanceof Integer)
            return (int) value;
        if (value instanceof String) {
            String size = (String) value;
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
        throw new IllegalArgumentException("无法将 " + value + " 解析成 Size 值");
    }

    static int getShape(Object value, int defaultValue) {
        if (value == null)
            return defaultValue;
        if (value instanceof Integer)
            return (int) value;
        if (value instanceof String) {
            // TODO: 2018/12/5
        }
        return 0;
    }
}
