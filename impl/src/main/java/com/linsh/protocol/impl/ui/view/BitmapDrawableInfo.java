package com.linsh.protocol.impl.ui.view;

import android.graphics.drawable.Drawable;

import org.json.JSONObject;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/05
 *    desc   :
 * </pre>
 */
public class BitmapDrawableInfo extends DrawableInfo {

    public String path;

    public BitmapDrawableInfo(String path) {
        this.path = path;
    }

    @Override
    public Drawable getDrawable() {
        return null;
    }

    @Override
    public void deserialize(JSONObject object) {

    }
}
