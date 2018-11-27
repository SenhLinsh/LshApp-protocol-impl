package com.linsh.protocol.impl.ui.toast;

import android.content.Context;

import com.linsh.protocol.ui.toast.ToastManager;
import com.linsh.utilseverywhere.ToastUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/24
 *    desc   :
 * </pre>
 */
public class ToastManagerImpl implements ToastManager {

    @Override
    public void show(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void show(Context context, String msg) {
        ToastUtils.show(context, msg);
    }

    @Override
    public void showLong(String msg) {
        ToastUtils.showLong(msg);
    }

    @Override
    public void showLong(Context context, String msg) {
        ToastUtils.showLong(context, msg);
    }
}
