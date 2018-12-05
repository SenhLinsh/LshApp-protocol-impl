package com.linsh.protocol.impl.ui.layout;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/05
 *    desc   :
 * </pre>
 */
public class JsonListViewProtocolImpl<T> extends ListViewProtocolImpl<T> {

    public JsonListViewProtocolImpl(View view) {
        super((RecyclerView) view);
    }
}
