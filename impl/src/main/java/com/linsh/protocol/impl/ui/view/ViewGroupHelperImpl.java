package com.linsh.protocol.impl.ui.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;

import com.linsh.protocol.ui.view.ViewGroupHelper;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/27
 *    desc   :
 * </pre>
 */
class ViewGroupHelperImpl<T extends ViewGroupHelper, V extends ViewGroup> extends ViewHelperImpl<T,V> implements ViewGroupHelper<T, V> {

    public ViewGroupHelperImpl(Context context) {
        super(context);
    }

    @Override
    public T addView(View view) {
        getView().addView(view);
        return (T) this;
    }

    @Override
    public T getChildAt(int index) {
        view.getChildAt(index);
        return (T) this;
    }

    @Override
    public T removeView(View view) {
        getView().removeView(view);
        return (T) this;
    }

    @Override
    public T removeViewAt(int index) {
        view.removeViewAt(index);
        return (T) this;
    }

    @Override
    public T removeAllViews() {
        view.removeAllViews();
        return (T) this;
    }

    @Override
    public T setClipToPadding(boolean clipToPadding) {
        view.setClipToPadding(clipToPadding);
        return (T) this;
    }

    @Override
    public T setClipChildren(boolean clipChildren) {
        view.setClipChildren(clipChildren);
        return (T) this;
    }

    @Override
    public T setLayoutAnimation(LayoutAnimationController controller) {
        view.setLayoutAnimation(controller);
        return (T) this;
    }

    @Override
    public T setLayoutAnimationListener(Animation.AnimationListener animationListener) {
        view.setLayoutAnimationListener(animationListener);
        return (T) this;
    }
}
