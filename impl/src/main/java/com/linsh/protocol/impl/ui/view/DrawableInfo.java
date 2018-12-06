package com.linsh.protocol.impl.ui.view;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;

import com.linsh.protocol.Client;
import com.linsh.utilseverywhere.BackgroundUtils;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/03
 *    desc   :
 * </pre>
 */
public class DrawableInfo {

    public ColorInfo color;
    public String url;
    public String path;
    public String drawable;
    public ShapeInfo shape;
    public Object[][] states;

    public void setBackground(View view) {
        if (color != null) {
            BackgroundUtils.setBackground(view, color.getStateListDrawable());
        } else if (url != null) {
            Client.image().with(url).loadBg(view);
        } else if (path != null) {
            Client.image().with(path).loadBg(view);
        } else if (drawable != null) {
            File file = new File(Client.config().ui().resDir(), "drawable/" + drawable);
            Client.image().with(file).loadBg(view);
        } else if (shape != null) {
            BackgroundUtils.setBackground(view, shape.getDrawable());
        }
    }

    public void setImage(ImageView view) {
        if (url != null) {
            Client.image().load(url, view);
        } else if (path != null) {
            Client.image().load(path, view);
        } else if (drawable != null) {
            File file = new File(Client.config().ui().resDir(), "drawable/" + drawable);
            Client.image().load(file, view);
        } else if (shape != null) {
            view.setImageDrawable(shape.getDrawable());
        }
    }

    public static class ShapeInfo {

        public int shape;
        public int color;
        public StrokeInfo stroke;

        private Drawable getDrawable() {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(shape);
            drawable.setColor(color);
            if (stroke != null) {
                drawable.setStroke(stroke.width, stroke.color, stroke.dashWidth, stroke.dashGap);
            }
            return drawable;
        }
    }

    public static class StrokeInfo {
        public int width;
        public int color;
        public int dashWidth;
        public int dashGap;
    }
}
