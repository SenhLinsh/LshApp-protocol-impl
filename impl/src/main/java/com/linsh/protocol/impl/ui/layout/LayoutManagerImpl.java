package com.linsh.protocol.impl.ui.layout;

import android.app.Activity;

import com.linsh.protocol.Type;
import com.linsh.protocol.ui.layout.LayoutManager;
import com.linsh.protocol.ui.layout.ListViewHelper;
import com.linsh.protocol.ui.layout.TabLayoutHelper;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/25
 *    desc   :
 * </pre>
 */
public class LayoutManagerImpl implements LayoutManager {

    private final Activity activity;

    public LayoutManagerImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public <T> ListViewHelper<T> list(Type<T> typeOfData) {
        return new ListViewHelperImpl<>(activity);
    }

    @Override
    public <T> TabLayoutHelper<T> tab(Type<T> typeOfData) {
        throw new IllegalArgumentException("暂未开发");
    }
}
