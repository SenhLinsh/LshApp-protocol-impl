package com.linsh.protocol.impl.ui;

import android.app.Activity;
import android.content.Context;

import com.linsh.protocol.Client;
import com.linsh.protocol.config.UIConfig;
import com.linsh.protocol.impl.ui.dialog.DialogManagerImpl;
import com.linsh.protocol.impl.ui.layout.LayoutManagerImpl;
import com.linsh.protocol.impl.ui.popup.PopupWindowManagerImpl;
import com.linsh.protocol.impl.ui.toast.ToastManagerImpl;
import com.linsh.protocol.impl.ui.view.ViewManagerImpl;
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

    public LshUIManager(UIConfig config) {
    }

    @Override
    public DialogManager dialog(Activity activity) {
        return Client.activity().target(activity)
                .useSubscriber(DialogManagerImpl.class);
    }

    @Override
    public LayoutManager layout(Context context) {
        return new LayoutManagerImpl(context);
    }

    @Override
    public PopupWindowManager popup(Context context) {
        return new PopupWindowManagerImpl(context);
    }

    @Override
    public ToastManager toast() {
        return new ToastManagerImpl();
    }

    @Override
    public ViewManager view() {
        return new ViewManagerImpl();
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
