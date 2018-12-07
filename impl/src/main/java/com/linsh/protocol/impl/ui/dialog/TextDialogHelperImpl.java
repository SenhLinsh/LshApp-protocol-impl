package com.linsh.protocol.impl.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import com.linsh.dialog.LshDialog;
import com.linsh.protocol.ui.OnClickListener;
import com.linsh.protocol.ui.dialog.TextDialogHelper;
import com.linsh.protocol.ui.view.ViewProtocol;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/24
 *    desc   :
 * </pre>
 */
class TextDialogHelperImpl implements TextDialogHelper {

    private final LshDialog.TextDialogBuilder builder;
    private LshDialog dialog;

    public TextDialogHelperImpl(Activity activity) {
        builder = new LshDialog(activity).buildText();
    }

    public TextDialogHelperImpl(Activity activity, CharSequence title) {
        builder = new LshDialog(activity).buildText();
        builder.setTitle(title.toString());
    }

    @Override
    public TextDialogHelper content(CharSequence content) {
        builder.setContent(content.toString());
        return this;
    }

    @Override
    public CharSequence getContent() {
        return null;
    }

    @Override
    public TextDialogHelper title(CharSequence title) {
        builder.setTitle(title.toString());
        return this;
    }

    @Override
    public TextDialogHelper positiveBtn(OnClickListener<TextDialogHelper> listener) {
        return positiveBtn("确定", listener);
    }

    @Override
    public TextDialogHelper positiveBtn(CharSequence text, final OnClickListener<TextDialogHelper> listener) {
        builder.setPositiveButton(text.toString(), new LshDialog.OnPositiveListener() {
            @Override
            public void onClick(LshDialog lshDialog) {
                if (listener != null)
                    listener.onClick(TextDialogHelperImpl.this);
            }
        });
        return this;
    }

    @Override
    public TextDialogHelper negativeBtn(OnClickListener<TextDialogHelper> listener) {
        return negativeBtn("取消", listener);
    }

    @Override
    public TextDialogHelper negativeBtn(CharSequence text, final OnClickListener<TextDialogHelper> listener) {
        builder.setNegativeButton(text.toString(), new LshDialog.OnNegativeListener() {
            @Override
            public void onClick(LshDialog lshDialog) {
                if (listener != null)
                    listener.onClick(TextDialogHelperImpl.this);
            }
        });
        return this;
    }

    @Override
    public TextDialogHelper neutralBtn(OnClickListener<TextDialogHelper> listener) {
        return this;
    }

    @Override
    public TextDialogHelper neutralBtn(CharSequence text, OnClickListener<TextDialogHelper> listener) {
        return this;
    }

    @Override
    public TextDialogHelper show() {
        if (dialog != null)
            dialog.show();
        else
            dialog = builder.show();
        return this;
    }

    @Override
    public TextDialogHelper dismiss() {
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
        return new ViewProtocol() {
            @Override
            public View getView() {
                return dialog.getWindow().getDecorView();
            }
        };
    }
}
