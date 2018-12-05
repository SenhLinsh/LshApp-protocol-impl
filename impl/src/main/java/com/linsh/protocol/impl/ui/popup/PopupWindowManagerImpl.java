package com.linsh.protocol.impl.ui.popup;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.linsh.protocol.Client;
import com.linsh.protocol.ui.popup.PopupWindowManager;
import com.linsh.protocol.ui.view.ViewProtocol;
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

    private final Context context;
    private final PopupWindow window;
    private ViewProtocol viewHelper;

    public PopupWindowManagerImpl(Context context) {
        this.context = context;
        window = new PopupWindow(context);
    }

    @Override
    public PopupWindow getWindow() {
        return window;
    }

    @Override
    public PopupWindowManager setView(int layout) {
        viewHelper = Client.ui().view().view(LayoutInflater.from(context).inflate(layout, null));
        window.setContentView(viewHelper.getView());
        return this;
    }

    @Override
    public PopupWindowManager setView(View view) {
        viewHelper = Client.ui().view().view(view);
        window.setContentView(viewHelper.getView());
        return this;
    }

    @Override
    public PopupWindowManager setView(ViewProtocol viewHelper) {
        this.viewHelper = viewHelper;
        window.setContentView(viewHelper.getView());
        return this;
    }

    @Override
    public PopupWindowManager setView(WindowViewHelper helper) {
        viewHelper = Client.ui().view().view(helper.getView());
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
