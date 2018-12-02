package com.linsh.protocol.impl.ui.view;

import android.view.View;

import com.linsh.protocol.ui.view.ViewProtocol;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/26
 *    desc   :
 * </pre>
 */
class ViewProtocolImpl<T extends View> implements ViewProtocol<T> {

    private final T view;

    public ViewProtocolImpl(T view) {
        this.view = view;
    }

    @Override
    public T getView() {
        return view;
    }
}
