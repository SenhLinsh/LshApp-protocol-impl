package com.linsh.protocol.impl.ui.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/03
 *    desc   :
 * </pre>
 */
public class ColorInfo {

    public String color;
    public String[][] states;

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
        if (color != null) {
            return new ColorDrawable(ColorUtils.parseColor(color));
        }
        return new ColorDrawable(Color.TRANSPARENT);
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
        if (color != null) {
            return ColorStateList.valueOf(ColorUtils.parseColor(color));
        }
        return ColorStateList.valueOf(Color.TRANSPARENT);
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
}
