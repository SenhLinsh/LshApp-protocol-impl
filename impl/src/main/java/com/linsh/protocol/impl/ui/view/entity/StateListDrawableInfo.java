package com.linsh.protocol.impl.ui.view.entity;

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
public class StateListDrawableInfo extends DrawableInfo {

    public Object[][] states;

    @Override
    public Drawable getDrawable() {
        return null;
    }

    @Override
    public void deserialize(JSONObject object) {

    }
}
