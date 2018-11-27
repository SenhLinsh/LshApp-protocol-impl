package com.linsh.protocol.impl.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linsh.protocol.Client;
import com.linsh.protocol.impl.R;
import com.linsh.protocol.ui.dialog.CustomDialogHelper;
import com.linsh.protocol.ui.dialog.LoadingDialogHelper;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/24
 *    desc   :
 * </pre>
 */
class LoadingDialogHelperImpl implements LoadingDialogHelper {

    private final CustomDialogHelper custom;
    private final ProgressBar progressBar;
    private final TextView textView;


    public LoadingDialogHelperImpl(Activity activity) {
        custom = Client.ui().dialog(activity).custom(R.layout.impl_dialog_loading);
        ViewGroup viewGroup = (ViewGroup) custom.getView();
        progressBar = (ProgressBar) viewGroup.getChildAt(0);
        textView = (TextView) viewGroup.getChildAt(1);
    }

    public LoadingDialogHelperImpl(Activity activity, CharSequence title) {
        custom = Client.ui().dialog(activity).custom(R.layout.impl_dialog_loading);
        ViewGroup viewGroup = (ViewGroup) custom.getView();
        progressBar = (ProgressBar) viewGroup.getChildAt(0);
        textView = (TextView) viewGroup.getChildAt(1);
        textView.setText(title);
    }

    @Override
    public LoadingDialogHelper title(CharSequence title) {
        custom.title(title);
        return this;
    }

    @Override
    public LoadingDialogHelper content(CharSequence content) {
        textView.setText(content);
        return this;
    }

    @Override
    public LoadingDialogHelper progress(float progress) {
        progressBar.setProgress((int) progress * 100);
        return this;
    }

    @Override
    public LoadingDialogHelper horizontal() {
        progressBar.setHorizontalScrollBarEnabled(true);
        return this;
    }

    @Override
    public LoadingDialogHelper horizontal(float progress) {
        progressBar.setHorizontalScrollBarEnabled(true);
        progressBar.setProgress((int) progress * 100);
        return this;
    }

    @Override
    public LoadingDialogHelper round() {
        progressBar.setHorizontalScrollBarEnabled(false);
        return this;
    }

    @Override
    public LoadingDialogHelper round(float progress) {
        progressBar.setHorizontalScrollBarEnabled(false);
        progressBar.setProgress((int) progress * 100);
        return this;
    }

    @Override
    public LoadingDialogHelper show() {
        custom.show();
        return this;
    }

    @Override
    public LoadingDialogHelper dismiss() {
        custom.dismiss();
        return this;
    }

    @Override
    public Dialog build() {
        return custom.build();
    }
}
