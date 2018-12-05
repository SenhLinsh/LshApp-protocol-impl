package com.linsh.protocol.impl.ui.view.entity;

import android.graphics.drawable.Drawable;

import org.json.JSONObject;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/03
 *    desc   :
 * </pre>
 */
public abstract class DrawableInfo {

    public abstract Drawable getDrawable();

    public abstract void deserialize(JSONObject object);
}
