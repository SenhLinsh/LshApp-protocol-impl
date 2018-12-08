package com.linsh.protocol.impl.view;

import android.view.View;

import com.linsh.protocol.ui.view.ViewProtocol;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/07
 *    desc   :
 * </pre>
 */
public interface ButtonViewProtocol extends ViewProtocol {

    void setText(CharSequence text);

    void setOnclick(View.OnClickListener listener);
}
