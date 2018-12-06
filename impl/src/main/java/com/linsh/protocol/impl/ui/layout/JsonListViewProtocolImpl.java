package com.linsh.protocol.impl.ui.layout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.linsh.protocol.Client;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/05
 *    desc   :
 * </pre>
 */
public class JsonListViewProtocolImpl<T> extends ListViewProtocolImpl<T> {

    public JsonListViewProtocolImpl(Context context) {
        super(getDefaultListView(context));
    }

    public JsonListViewProtocolImpl(View view) {
        super((RecyclerView) view);
    }

    private static RecyclerView getDefaultListView(Context context) {
        RecyclerView recyclerView = Client.ui().view().view(new RecyclerView(context)).getView();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return recyclerView;
    }
}
