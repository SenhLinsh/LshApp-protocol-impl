package com.linsh.protocol.impl.ui.widget;

import android.app.Activity;

import com.linsh.protocol.Client;
import com.linsh.protocol.ui.widget.MenuHelper;
import com.linsh.protocol.ui.widget.StatusBarHelper;
import com.linsh.protocol.ui.widget.ToolbarHelper;
import com.linsh.protocol.ui.widget.WidgetManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/25
 *    desc   :
 * </pre>
 */
public class WidgetManagerImpl implements WidgetManager {

    private final Activity activity;

    public WidgetManagerImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public MenuHelper menu() {
        return Client.activity().target(activity).useSubscriber(MenuHelperImpl.class);
    }

    @Override
    public StatusBarHelper statusBar() {
        throw new IllegalArgumentException("暂未开发");
    }

    @Override
    public ToolbarHelper toolbar() {
        return new ToolbarHelperImpl(activity);
    }
}
