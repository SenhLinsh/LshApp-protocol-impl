package com.linsh.protocol.impl.ui.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.linsh.protocol.Client;
import com.linsh.protocol.ui.dialog.TextDialogHelper;
import com.linsh.protocol.ui.view.ViewProtocol;
import com.linsh.utilseverywhere.UnitConverseUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/24
 *    desc   :
 * </pre>
 */
class TextDialogHelperImpl extends BaseDialogHelperImpl<TextDialogHelper> implements TextDialogHelper {

    private TextView contentView;

    public TextDialogHelperImpl(Activity activity) {
        this(activity, null);
    }

    public TextDialogHelperImpl(Activity activity, CharSequence content) {
        super(activity);
        contentView = new TextView(activity);
        contentView.setTextSize(Client.value().textSize().text());
        int padding = UnitConverseUtils.dp2px(10);
        contentView.setPadding(padding * 2, padding, padding * 2, padding);
        builder.setView(contentView);
        content(content);
    }

    @Override
    public TextDialogHelper content(CharSequence content) {
        if (content == null) content = "";
        contentView.setText(content);
        return this;
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
