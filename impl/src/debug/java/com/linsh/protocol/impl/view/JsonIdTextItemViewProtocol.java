package com.linsh.protocol.impl.view;

import android.view.View;
import android.widget.TextView;

import com.linsh.protocol.Client;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/05
 *    desc   :
 * </pre>
 */
public class JsonIdTextItemViewProtocol implements TextItemViewProtocol {

    private final View view;
    private final TextView tvText;

    public JsonIdTextItemViewProtocol(View view) {
        this.view = view;
        tvText = (TextView) Client.ui().view().findViewById(view, "textItem_setText");
    }

    @Override
    public void setText(CharSequence text) {
        tvText.setText(text);
    }

    @Override
    public View getView() {
        return view;
    }
}
