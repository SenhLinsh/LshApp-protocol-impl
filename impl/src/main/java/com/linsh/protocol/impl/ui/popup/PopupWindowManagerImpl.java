package com.linsh.protocol.impl.ui.popup;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

import com.linsh.protocol.Client;
import com.linsh.protocol.ui.popup.PopupWindowManager;
import com.linsh.protocol.ui.view.ViewHelper;
import com.linsh.protocol.ui.window.WindowViewHelper;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/25
 *    desc   :
 * </pre>
 */
public class PopupWindowManagerImpl implements PopupWindowManager {

    private final Activity activity;
    private final PopupWindow window;
    private ViewHelper viewHelper;

    public PopupWindowManagerImpl(Activity activity) {
        this.activity = activity;
        window = new PopupWindow(activity);
    }

    @Override
    public PopupWindow getWindow() {
        return window;
    }

    @Override
    public PopupWindowManager setView(int layout) {
        viewHelper = Client.ui().view(activity).inflate(layout);
        window.setContentView(viewHelper.getView());
        return this;
    }

    @Override
    public PopupWindowManager setView(View view) {
        viewHelper = Client.ui().view(activity).view(view);
        window.setContentView(viewHelper.getView());
        return this;
    }

    @Override
    public PopupWindowManager setView(ViewHelper viewHelper) {
        this.viewHelper = viewHelper;
        window.setContentView(viewHelper.getView());
        return this;
    }

    @Override
    public PopupWindowManager setView(WindowViewHelper helper) {
        viewHelper = Client.ui().view(activity).view(helper.getView());
        window.setContentView(viewHelper.getView());
        return this;
    }

    @Override
    public PopupWindowManager showAsDropDown(View anchor) {
        window.showAsDropDown(anchor);
        return this;
    }

    @Override
    public PopupWindowManager showAsDropDown(View anchor, int xoff, int yoff) {
        window.showAsDropDown(anchor, xoff, yoff);
        return this;
    }

    @Override
    public PopupWindowManager showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.showAsDropDown(anchor, xoff, yoff, gravity);
        } else {
            window.showAsDropDown(anchor, xoff, yoff);
        }
        return this;
    }

    @Override
    public PopupWindowManager showAtLocation(View anchor, int gravity) {
        window.showAtLocation(anchor, gravity, 0, 0);
        return this;
    }

    @Override
    public PopupWindowManager showAtLocation(View anchor, int gravity, int xoff, int yoff) {
        window.showAtLocation(anchor, gravity, xoff, yoff);
        return this;
    }

    @Override
    public PopupWindowManager setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        window.setOnDismissListener(onDismissListener);
        return this;
    }
}
