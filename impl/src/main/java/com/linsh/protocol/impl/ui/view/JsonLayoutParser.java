package com.linsh.protocol.impl.ui.view;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
            return parse(new JSONObject(json));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static ViewInfo parse(JSONObject object) throws JSONException {
        ViewInfo info = new ViewInfo();
        info.name = getName(object);
        info.id = object.optString("id");
        info.maxWidth = getSize(object, "maxWidth", info.maxWidth);
        info.maxHeight = getSize(object, "maxHeight", info.maxHeight);
        info.minWidth = getSize(object, "minWidth", info.minWidth);
        info.minHeight = getSize(object, "minHeight", info.minHeight);
        info.padding = getSize(object, "padding", info.padding);
        info.paddings = getSizeArray(object, "paddings", 0);
        info.background = getColor(object, "background");
        info.elevation = getSize(object, "elevation", info.elevation);
        info.scaleX = (float) object.optDouble("scaleX", info.scaleX);
        info.scaleY = (float) object.optDouble("scaleY", info.scaleY);
        info.visibility = getVisibility(object);
        info.gravity = getGravity(object, "gravity", info.gravity);
        info.orientation = getOrientation(object, "orientation", info.orientation);
        info.scaleType = getScaleType(object, "scaleType");
        info.src = object.optString("src");
        info.textSize = getSize(object, "textSize", info.textSize);
        info.textColor = getColor(object, "textColor");
        info.text = object.optString("text");
        info.hint = object.optString("hint");
        info.width = getSize(object, "width", info.width);
        info.height = getSize(object, "height", info.height);
        info.margin = getSize(object, "margin", info.margin);
        info.margins = getSizeArray(object, "margins", 0);
        info.weight = object.optInt("weight", info.weight);
        info.layout_gravity = getGravity(object, "layout_gravity", info.layout_gravity);
        info.children = getChildren(object);
        info.protocol = getProtocol(object);
        return info;
    }

    private static ViewProtocolInfo getProtocol(JSONObject object) throws JSONException {
        JSONObject protocolObj = object.optJSONObject("protocol");
        if (protocolObj == null)
            return null;
        ViewProtocolInfo protocol = new ViewProtocolInfo();
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

    private static List<ViewInfo> getChildren(JSONObject object) throws JSONException {
        JSONArray array = object.optJSONArray("children");
        if (array == null)
            return null;
        List<ViewInfo> res = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            res.add(parse(array.optJSONObject(i)));
        }
        return res;
    }

    private static ImageView.ScaleType getScaleType(JSONObject object, String fieldName) {
        Object value = object.opt(fieldName);
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
        throw new IllegalArgumentException("无法识别属性值 " + fieldName + ": " + value);
    }

    private static int getOrientation(JSONObject object, String fieldName, int defaultValue) {
        Object value = object.opt(fieldName);
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
        throw new IllegalArgumentException("无法识别属性值 " + fieldName + ": " + value);
    }

    private static int getGravity(JSONObject object, String fieldName, int defaultValue) {
        Object value = object.opt(fieldName);
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
        throw new IllegalArgumentException("无法识别属性值 " + fieldName + ": " + value);
    }

    private static int getVisibility(JSONObject object) {
        Object value = object.opt("visibility");
        if (value == null)
            return View.VISIBLE;
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
        throw new IllegalArgumentException("无法识别属性值 visibility: " + value);
    }

    private static int getColor(JSONObject object, String fieldName) {
        Object value = object.opt(fieldName);
        if (value == null)
            return 0;
        if (value instanceof Integer)
            return (int) value;
        if (value instanceof String) {
            String color = (String) value;
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
        }
        throw new IllegalArgumentException("无法识别属性值 " + fieldName + ": " + value);
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

    private static int[] getSizeArray(JSONObject object, String fieldName, int defaultValue) throws JSONException {
        JSONArray jsonArray = object.optJSONArray(fieldName);
        if (jsonArray == null)
            return null;
        int[] sizes = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            sizes[i] = getSize(value, fieldName, defaultValue);
        }
        return sizes;
    }

    private static int getSize(JSONObject object, String fieldName, int defaultValue) {
        return getSize(object.opt(fieldName), fieldName, defaultValue);
    }

    private static int getSize(Object value, String fieldName, int defaultValue) {
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
        throw new IllegalArgumentException("无法识别属性值 " + fieldName + ": " + value);
    }

    private static String getName(JSONObject object) {
        String name = object.optString("name");
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
}
