package com.linsh.protocol.impl.ui.view;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import com.linsh.protocol.ui.view.ViewProxy;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/26
 *    desc   :
 * </pre>
 */
public class ViewProxyImpl<T extends ViewProxy, V extends View> implements ViewProxy<T, V> {

    protected final V view;

    public ViewProxyImpl(V view) {
        this.view = view;
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public T setBackground(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        return (T) this;
    }

    @Override
    public T setBackgroundColor(int color) {
        view.setBackgroundColor(color);
        return (T) this;
    }

    @Override
    public T setBackgroundRes(int resId) {
        view.setBackgroundResource(resId);
        return (T) this;
    }

    @Override
    public T setVisibility(int visibility) {
        view.setVisibility(visibility);
        return (T) this;
    }

    @Override
    public T setEnabled(boolean enabled) {
        view.setEnabled(enabled);
        return (T) this;
    }

    @Override
    public T setClickable(boolean clickable) {
        view.setClickable(clickable);
        return (T) this;
    }

    @Override
    public T setOnClickListener(View.OnClickListener listener) {
        view.setOnClickListener(listener);
        return (T) this;
    }

    @Override
    public T setOnTouchListener(View.OnTouchListener listener) {
        view.setOnTouchListener(listener);
        return (T) this;
    }
}
