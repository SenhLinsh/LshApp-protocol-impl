package com.linsh.protocol.impl.ui.view;

import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.linsh.protocol.Client;

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
    public Object src;

    @Override
    public void onDeserialize(JsonObject jsonObject, JsonDeserializationContext context, ViewInfo parent) {
        super.onDeserialize(jsonObject, context, parent);
        maxWidth = JsonLayoutParser.getSize(jsonObject.get("maxWidth"), maxWidth);
        maxHeight = JsonLayoutParser.getSize(jsonObject.get("maxHeight"), maxHeight);

        scaleType = JsonLayoutParser.getScaleType(jsonObject.get("scaleType"));
        src = JsonLayoutParser.getBackground(jsonObject.get("src"), context);
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
            if (src instanceof Integer) {
                view.setImageDrawable(new ColorDrawable((int) src));
            } else if (src instanceof String) {
                Client.image().load((String) background, view);
            } else if (background instanceof DrawableInfo) {
                ((DrawableInfo) background).setImage(view);
            }
        }
    }
}
