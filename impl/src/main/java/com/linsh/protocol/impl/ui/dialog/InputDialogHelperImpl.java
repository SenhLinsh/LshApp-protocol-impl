package com.linsh.protocol.impl.ui.dialog;

import android.app.Activity;
import android.app.Dialog;

import com.linsh.dialog.LshDialog;
import com.linsh.protocol.ui.OnClickListener;
import com.linsh.protocol.ui.dialog.InputDialogHelper;
import com.linsh.protocol.ui.view.ViewProtocol;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/24
 *    desc   :
 * </pre>
 */
class InputDialogHelperImpl implements InputDialogHelper {

    private final LshDialog.InputDialogBuilder builder;
    private LshDialog dialog;

    public InputDialogHelperImpl(Activity activity) {
        builder = new LshDialog(activity).buildInput();
    }

    public InputDialogHelperImpl(Activity activity, CharSequence title) {
        builder = new LshDialog(activity).buildInput();
        builder.setTitle(title.toString());
    }

    @Override
    public InputDialogHelper hint(CharSequence hint) {
        builder.setHint(hint.toString());
        return this;
    }

    @Override
    public InputDialogHelper content(CharSequence content) {
        return this;
    }

    @Override
    public CharSequence getHint() {
        return null;
    }

    @Override
    public CharSequence getContent() {
        return null;
    }

    @Override
    public InputDialogHelper title(CharSequence title) {
        builder.setTitle(title.toString());
        return this;
    }

    @Override
    public InputDialogHelper positiveBtn(OnClickListener<InputDialogHelper> listener) {
        return positiveBtn("确定", listener);
    }

    @Override
    public InputDialogHelper positiveBtn(CharSequence text, final OnClickListener<InputDialogHelper> listener) {
        builder.setPositiveButton(text.toString(), new LshDialog.OnInputPositiveListener() {
            @Override
            public void onClick(LshDialog lshDialog, String s) {
                if (listener != null)
                    listener.onClick(InputDialogHelperImpl.this);
            }
        });
        return this;
    }

    @Override
    public InputDialogHelper negativeBtn(OnClickListener<InputDialogHelper> listener) {
        return negativeBtn("确定", listener);
    }

    @Override
    public InputDialogHelper negativeBtn(CharSequence text, final OnClickListener<InputDialogHelper> listener) {
        builder.setNegativeButton(text.toString(), new LshDialog.OnInputNegativeListener() {
            @Override
            public void onClick(LshDialog lshDialog, String s) {
                if (listener != null)
                    listener.onClick(InputDialogHelperImpl.this);
            }
        });
        return this;
    }

    @Override
    public InputDialogHelper neutralBtn(OnClickListener<InputDialogHelper> listener) {
        return this;
    }

    @Override
    public InputDialogHelper neutralBtn(CharSequence text, OnClickListener<InputDialogHelper> listener) {
        return this;
    }

    @Override
    public InputDialogHelper show() {
        if (dialog == null)
            dialog = builder.show();
        else
            dialog.show();
        return this;
    }

    @Override
    public InputDialogHelper dismiss() {
        if (dialog != null)
            dialog.dismiss();
        return this;
    }

    @Override
    public Dialog build() {
        // TODO: 2018/12/2
        return null;
    }

    @Override
    public ViewProtocol getContentView() {
        // TODO: 2018/12/2
        return null;
    }
}
