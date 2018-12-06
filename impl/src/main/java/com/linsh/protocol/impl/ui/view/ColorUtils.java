package com.linsh.protocol.impl.ui.view;

import android.graphics.Color;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/06
 *    desc   :
 * </pre>
 */
class ColorUtils {

    static boolean isColor(String color) {
        return color.matches("(#|0x)*[0-9a-fA-F]{3,8}");
    }

    static int parseColor(String color) {
        color = color.replaceAll("#|0x", "");
        if (color.length() < 3) {
            throw new IllegalArgumentException("无法将 " + color + " 解析成 Color 值");
        } else if (color.length() == 3) {
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
}
