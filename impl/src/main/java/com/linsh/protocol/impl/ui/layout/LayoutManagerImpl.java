package com.linsh.protocol.impl.ui.layout;

import android.content.Context;

import com.linsh.protocol.ui.layout.LayoutManager;
import com.linsh.protocol.ui.layout.ListViewProtocol;

import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/25
 *    desc   :
 * </pre>
 */
public class LayoutManagerImpl implements LayoutManager {

    private final Context context;

    public LayoutManagerImpl(Context context) {
        this.context = context;
    }

    @Override
    public <T> ListViewProtocol<T> list() {
        return new ListViewProtocolImpl<>(context);
    }

    @Override
    public <T> ListViewProtocol<T> list(List<T> data) {
        ListViewProtocolImpl<T> listViewProtocol = new ListViewProtocolImpl<>(context);
        listViewProtocol.setData(data);
        return listViewProtocol;
    }

    @Override
    public <T> ListViewProtocol<T> list(T[] data) {
        ListViewProtocolImpl<T> listViewProtocol = new ListViewProtocolImpl<>(context);
        listViewProtocol.setData(data);
        return listViewProtocol;
    }

    @Override
    public <T> ListViewProtocol<T> list(T data) {
        ListViewProtocolImpl<T> listViewProtocol = new ListViewProtocolImpl<>(context);
        listViewProtocol.setSingleData(data);
        return listViewProtocol;
    }
}
