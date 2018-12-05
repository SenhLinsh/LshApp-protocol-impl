package com.linsh.protocol.impl.ui.view.entity;

import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/04
 *    desc   :
 * </pre>
 */
public class ImageViewInfo<T extends ImageView> extends ViewInfo<T> {

    public int maxWidth = -1;
    public int maxHeight = -1;

    public ImageView.ScaleType scaleType;
    public DrawableInfo src;

    @Override
    protected void onDeserialize(JSONObject object, ViewInfo parent) throws JSONException {
        super.onDeserialize(object, parent);
        maxWidth = JsonLayoutParser.getSize(object.opt("maxWidth"), maxWidth);
        maxHeight = JsonLayoutParser.getSize(object.opt("maxHeight"), maxHeight);

        scaleType = JsonLayoutParser.getScaleType(object.opt("scaleType"));
        src = JsonLayoutParser.getDrawableInfo(object.opt("src"));
    }

    @Override
    protected void onInflateView(T view) {
        super.onInflateView(view);
        if (maxWidth >= 0)
            view.setMinimumWidth(maxWidth);
        if (maxHeight >= 0)
            view.setMinimumHeight(maxHeight);

        if (scaleType != null)
            view.setScaleType(scaleType);
        if (src != null) {
            // TODO: 2018/12/5
        }
    }
}
