package com.linsh.protocol.impl.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.linsh.protocol.ui.view.ImageViewHelper;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/26
 *    desc   :
 * </pre>
 */
public class ImageViewHelperImpl<T extends ImageViewHelper, V extends ImageView> extends ViewHelperImpl<T, V> implements ImageViewHelper<T, V> {

    public ImageViewHelperImpl(Context context) {
        super(context);
    }

    @Override
    public T setImage(Bitmap bitmap) {
        view.setImageBitmap(bitmap);
        return (T) this;
    }

    @Override
    public T setImage(Drawable drawable) {
        view.setImageDrawable(drawable);
        return (T) this;
    }

    @Override
    public T setImage(int resId) {
        view.setImageResource(resId);
        return (T) this;
    }
}
