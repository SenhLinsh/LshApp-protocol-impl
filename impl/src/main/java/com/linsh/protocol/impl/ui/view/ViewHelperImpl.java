package com.linsh.protocol.impl.ui.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.linsh.protocol.ui.view.ViewHelper;
import com.linsh.protocol.value.Types;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/26
 *    desc   :
 * </pre>
 */
class ViewHelperImpl<T extends ViewHelper, V extends View> extends ViewProxyImpl<T, V> implements ViewHelper<T, V> {

    public ViewHelperImpl(Context context) {
        super((V) new View(context));
    }

    public ViewHelperImpl(V view) {
        super(view);
    }

    @Override
    public void setTypes(Types types) {

    }

    @Override
    public void setWidth(int width) {
        ViewGroup.LayoutParams params = getView().getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            params.width = width;
        }
        getView().setLayoutParams(params);
    }

    @Override
    public void setHeight(int height) {
        ViewGroup.LayoutParams params = getView().getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        } else {
            params.height = height;
        }
        getView().setLayoutParams(params);
    }

    @Override
    public void setSize(int width, int height) {
        ViewGroup.LayoutParams params = getView().getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(width, height);
        } else {
            params.width = width;
            params.height = height;
        }
        getView().setLayoutParams(params);
    }
}
