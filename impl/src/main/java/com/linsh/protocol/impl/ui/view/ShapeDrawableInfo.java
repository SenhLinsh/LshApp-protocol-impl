package com.linsh.protocol.impl.ui.view;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import org.json.JSONObject;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/03
 *    desc   :
 * </pre>
 */
public class ShapeDrawableInfo extends DrawableInfo {

    public int shape;
    public int color;
    public StrokeInfo stroke;
//    public Object gradient;

    @Override
    public Drawable getDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(shape);
        drawable.setColor(color);
        if (stroke != null) {
            drawable.setStroke(stroke.width, stroke.color, stroke.dashWidth, stroke.dashGap);
        }
        return drawable;
    }

    @Override
    public void deserialize(JSONObject object) {
        color = JsonLayoutParser.getColor(object.opt("color"), color);
        shape = JsonLayoutParser.getShape(object.opt("shape"), shape);
    }
}
