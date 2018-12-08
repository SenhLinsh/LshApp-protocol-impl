package com.linsh.protocol.impl.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.linsh.protocol.Client;
import com.linsh.protocol.impl.ui.view.JsonLayoutInflater;
import com.linsh.utilseverywhere.ResourceUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/07
 *    desc   :
 * </pre>
 */
public class JsonIdButtonViewProtocol implements ButtonViewProtocol {

    private final View view;
    private final Button btButton;

    public JsonIdButtonViewProtocol(Context context) {
        String text = ResourceUtils.getTextFromAssets("ButtonView.info");
        view = JsonLayoutInflater.from(context).inflate(text, null);
        btButton = (Button) Client.ui().view().findViewById(view, "bt_button");
    }

    @Override
    public void setText(CharSequence text) {
        btButton.setText(text);
    }

    @Override
    public void setOnclick(View.OnClickListener listener) {
        btButton.setOnClickListener(listener);
    }

    @Override
    public View getView() {
        return view;
    }
}
