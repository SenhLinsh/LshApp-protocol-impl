package com.linsh.protocol.impl.ui.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

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
class InputDialogHelperImpl extends BaseDialogHelperImpl<InputDialogHelper> implements InputDialogHelper {

    private EditText contentView;

    public InputDialogHelperImpl(Activity activity) {
        this(activity, null);
    }

    public InputDialogHelperImpl(Activity activity, CharSequence content) {
        super(activity);
        contentView = new EditText(activity);
        builder.setView(contentView);
        content(content);
    }

    @Override
    public InputDialogHelper hint(CharSequence hint) {
        contentView.setHint(hint);
        return this;
    }

    @Override
    public InputDialogHelper content(CharSequence content) {
        if (content == null) content = "";
        contentView.setText(content);
        return this;
    }

    @Override
    public CharSequence getHint() {
        return contentView.getHint();
    }

    @Override
    public CharSequence getContent() {
        return contentView.getText();
    }

    @Override
    public ViewProtocol getContentView() {
        return new ViewProtocol() {
            @Override
            public View getView() {
                return contentView;
            }
        };
    }
}
