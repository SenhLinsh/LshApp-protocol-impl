package com.linsh.protocol.impl.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.linsh.protocol.ui.OnClickListener;
import com.linsh.protocol.ui.dialog.DialogHelper;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/08
 *    desc   :
 * </pre>
 */
public abstract class BaseDialogHelperImpl<T extends DialogHelper> implements DialogHelper<T> {

    protected AlertDialog.Builder builder;
    protected Dialog dialog;

    public BaseDialogHelperImpl(Activity activity) {
        builder = new AlertDialog.Builder(activity);
    }

    @Override
    public T title(CharSequence title) {
        builder.setTitle(title);
        return (T) this;
    }

    @Override
    public T positiveBtn(OnClickListener<T> listener) {
        return positiveBtn(null, listener);
    }

    @Override
    public T positiveBtn(CharSequence text, OnClickListener<T> listener) {
        builder.setPositiveButton(text == null ? "确定" : text.toString(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null)
                    listener.onClick((T) BaseDialogHelperImpl.this);
            }
        });
        return (T) this;
    }

    @Override
    public T negativeBtn(OnClickListener<T> listener) {
        return negativeBtn(null, listener);
    }

    @Override
    public T negativeBtn(CharSequence text, OnClickListener<T> listener) {
        builder.setNegativeButton(text == null ? "取消" : text.toString(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null)
                    listener.onClick((T) BaseDialogHelperImpl.this);
            }
        });
        return (T) this;
    }

    @Override
    public T neutralBtn(OnClickListener<T> listener) {
        return neutralBtn(null, listener);
    }

    @Override
    public T neutralBtn(CharSequence text, OnClickListener<T> listener) {
        builder.setNeutralButton(text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null)
                    listener.onClick((T) BaseDialogHelperImpl.this);
            }
        });
        return (T) this;
    }

    @Override
    public T show() {
        if (dialog == null)
            dialog = builder.show();
        else
            dialog.show();
        return (T) this;
    }

    @Override
    public T dismiss() {
        if (dialog != null)
            dialog.dismiss();
        return (T) this;
    }

    @Override
    public Dialog build() {
        if (dialog == null)
            dialog = builder.create();
        return dialog;
    }
}
