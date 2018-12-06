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
public class JsonIdTitleTextItemViewProtocol implements TitleTextItemViewProtocol {

    private final View view;
    private final TextView tvTitle;
    private final TextView tvText;

    public JsonIdTitleTextItemViewProtocol(View view) {
        this.view = view;
        tvTitle = (TextView) Client.ui().view().findViewById(view, "tv_title");
        tvText = (TextView) Client.ui().view().findViewById(view, "tv_text");
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    @Override
    public void setText(CharSequence text) {
        tvText.setText(text);
    }
}
