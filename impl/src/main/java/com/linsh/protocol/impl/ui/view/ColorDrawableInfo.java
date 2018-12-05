package com.linsh.protocol.impl.ui.view;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import org.json.JSONObject;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/04
 *    desc   :
 * </pre>
 */
public class ColorDrawableInfo extends DrawableInfo {

    public int color;

    public ColorDrawableInfo(int color) {
        this.color = color;
    }

    @Override
    public Drawable getDrawable() {
        return new ColorDrawable(color);
    }

    @Override
    public void deserialize(JSONObject object) {

    }
}
