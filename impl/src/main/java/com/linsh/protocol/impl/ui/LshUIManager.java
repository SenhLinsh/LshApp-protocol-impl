package com.linsh.protocol.impl.ui;

import android.app.Activity;

import com.linsh.protocol.Client;
import com.linsh.protocol.impl.ui.dialog.DialogManagerImpl;
import com.linsh.protocol.impl.ui.layout.LayoutManagerImpl;
import com.linsh.protocol.impl.ui.popup.PopupWindowManagerImpl;
import com.linsh.protocol.impl.ui.toast.ToastManagerImpl;
import com.linsh.protocol.impl.ui.view.ViewManagerImpl;
import com.linsh.protocol.impl.ui.view.ViewProxyImpl;
import com.linsh.protocol.impl.ui.widget.WidgetManagerImpl;
import com.linsh.protocol.ui.UIManager;
import com.linsh.protocol.ui.dialog.DialogManager;
import com.linsh.protocol.ui.layout.LayoutManager;
import com.linsh.protocol.ui.popup.PopupWindowManager;
import com.linsh.protocol.ui.toast.ToastManager;
import com.linsh.protocol.ui.view.ViewManager;
import com.linsh.protocol.ui.widget.WidgetManager;
import com.linsh.protocol.ui.window.WindowManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/24
 *    desc   :
 * </pre>
 */
public class LshUIManager implements UIManager {
    @Override
    public DialogManager dialog(Activity activity) {
        return Client.activity().target(activity)
                .useSubscriber(DialogManagerImpl.class);
    }

    @Override
    public LayoutManager layout(Activity activity) {
        return new LayoutManagerImpl(activity);
    }

    @Override
    public PopupWindowManager popup(Activity activity) {
        return new PopupWindowManagerImpl(activity);
    }

    @Override
    public ToastManager toast() {
        return new ToastManagerImpl();
    }

    @Override
    public ViewManager view(Activity activity) {
        return new ViewManagerImpl(activity);
    }

    @Override
    public WidgetManager widget(Activity activity) {
        return new WidgetManagerImpl(activity);
    }

    @Override
    public WindowManager window() {
        throw new IllegalArgumentException("暂未开发");
    }
}
