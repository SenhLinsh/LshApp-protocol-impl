package com.linsh.protocol.impl.ui.dialog;

import android.app.Activity;
import android.view.View;

import com.linsh.protocol.activity.ActivitySubscribe;
import com.linsh.protocol.ui.dialog.CustomDialogHelper;
import com.linsh.protocol.ui.dialog.DialogHelper;
import com.linsh.protocol.ui.dialog.DialogManager;
import com.linsh.protocol.ui.dialog.InputDialogHelper;
import com.linsh.protocol.ui.dialog.ListDialogHelper;
import com.linsh.protocol.ui.dialog.LoadingDialogHelper;
import com.linsh.protocol.ui.dialog.TextDialogHelper;

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
    public LoadingDialogHelper loading() {
        LoadingDialogHelperImpl helper = new LoadingDialogHelperImpl(activity);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public LoadingDialogHelper loading(CharSequence content) {
        LoadingDialogHelperImpl helper = new LoadingDialogHelperImpl(activity, content);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public TextDialogHelper text() {
        TextDialogHelperImpl helper = new TextDialogHelperImpl(activity);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public TextDialogHelper text(CharSequence content) {
        TextDialogHelperImpl helper = new TextDialogHelperImpl(activity, content);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public InputDialogHelper input() {
        InputDialogHelperImpl helper = new InputDialogHelperImpl(activity);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public InputDialogHelper input(CharSequence content) {
        InputDialogHelperImpl helper = new InputDialogHelperImpl(activity, content);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public ListDialogHelper list() {
        ListDialogHelperImpl helper = new ListDialogHelperImpl(activity);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public ListDialogHelper list(CharSequence[] items) {
        ListDialogHelperImpl helper = new ListDialogHelperImpl(activity, Arrays.asList(items));
        dialogHelper = helper;
        return helper;
    }

    @Override
    public ListDialogHelper list(List<? extends CharSequence> items) {
        ListDialogHelperImpl helper = new ListDialogHelperImpl(activity, items);
        dialogHelper = helper;
        return helper;
    }

    @Override
    public CustomDialogHelper custom() {
        return null;
    }

    @Override
    public CustomDialogHelper custom(View view) {
        return null;
    }

    @Override
    public CustomDialogHelper custom(int layout) {
        return null;
    }

    @Override
    public void dismiss() {
        if (dialogHelper instanceof LoadingDialogHelper) {
            ((LoadingDialogHelper) dialogHelper).dismiss();
        } else if (dialogHelper instanceof DialogHelper) {
            ((DialogHelper) dialogHelper).dismiss();
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
