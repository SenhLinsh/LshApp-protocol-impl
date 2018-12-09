package com.linsh.protocol.impl.ui.dialog;

import android.app.Activity;
import android.view.View;

import com.linsh.protocol.activity.ActivitySubscribe;
import com.linsh.protocol.ui.dialog.BaseDialogProtocol;
import com.linsh.protocol.ui.dialog.CustomDialogProtocol;
import com.linsh.protocol.ui.dialog.DialogManager;
import com.linsh.protocol.ui.dialog.DialogProtocol;
import com.linsh.protocol.ui.dialog.InputDialogProtocol;
import com.linsh.protocol.ui.dialog.ListDialogProtocol;
import com.linsh.protocol.ui.dialog.LoadingDialogProtocol;
import com.linsh.protocol.ui.dialog.TextDialogProtocol;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/24
 *    desc   :
 * </pre>
 */
public class DialogManagerImpl implements DialogManager, ActivitySubscribe.OnDestroy {

    private Activity activity;
    private Object dialogHelper;

    @Override
    public LoadingDialogProtocol loading() {
        LoadingDialogHelperImpl helper = new LoadingDialogHelperImpl(activity);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public LoadingDialogProtocol loading(CharSequence content) {
        LoadingDialogHelperImpl helper = new LoadingDialogHelperImpl(activity, content);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public TextDialogProtocol text() {
        TextDialogHelperImpl helper = new TextDialogHelperImpl(activity);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public TextDialogProtocol text(CharSequence content) {
        TextDialogHelperImpl helper = new TextDialogHelperImpl(activity, content);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public InputDialogProtocol input() {
        InputDialogHelperImpl helper = new InputDialogHelperImpl(activity);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public InputDialogProtocol input(CharSequence content) {
        InputDialogHelperImpl helper = new InputDialogHelperImpl(activity, content);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public ListDialogProtocol list() {
        ListDialogHelperImpl helper = new ListDialogHelperImpl(activity);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public ListDialogProtocol list(CharSequence[] items) {
        ListDialogHelperImpl helper = new ListDialogHelperImpl(activity, Arrays.asList(items));
        dialogHelper = helper;
        return helper;
    }

    @Override
    public ListDialogProtocol list(List<? extends CharSequence> items) {
        ListDialogHelperImpl helper = new ListDialogHelperImpl(activity, items);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public CustomDialogProtocol custom() {
        return null;
    }

    @Override
    public CustomDialogProtocol custom(View view) {
        return null;
    }

    @Override
    public CustomDialogProtocol custom(int layout) {
        return null;
    }

    @Override
    public void dismiss() {
        if (dialogHelper instanceof LoadingDialogProtocol) {
            ((LoadingDialogProtocol) dialogHelper).dismiss();
        } else if (dialogHelper instanceof BaseDialogProtocol) {
            ((DialogProtocol) dialogHelper).getDialog().dismiss();
        }
    }

    @Override
    public void attach(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onDestroy() {
        dismiss();
    }
}
