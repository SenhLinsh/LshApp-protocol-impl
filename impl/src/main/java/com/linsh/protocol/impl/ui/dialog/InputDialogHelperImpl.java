package com.linsh.protocol.impl.ui.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.linsh.protocol.ui.dialog.InputDialogProtocol;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/24
 *    desc   :
 * </pre>
 */
class InputDialogHelperImpl extends BaseDialogHelperImpl<InputDialogProtocol> implements InputDialogProtocol {

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
    public InputDialogProtocol hint(CharSequence hint) {
        contentView.setHint(hint);
        return this;
    }

    @Override
    public InputDialogProtocol content(CharSequence content) {
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
    public View getContentView() {
        return contentView;
    }
}
