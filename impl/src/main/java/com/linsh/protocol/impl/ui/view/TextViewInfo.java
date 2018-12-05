package com.linsh.protocol.impl.ui.view;

import android.util.TypedValue;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/03
 *    desc   :
 * </pre>
 */
public class TextViewInfo<T extends TextView> extends ViewInfo<T> {

    public int maxWidth = -1;
    public int maxHeight = -1;

    public int gravity = -1;
    public int textSize = -1;
    public int textColor;
    public String text;
    public String hint;

    @Override
    protected void onDeserialize(JSONObject object, ViewInfo parent) throws JSONException {
        super.onDeserialize(object, parent);
        maxWidth = JsonLayoutParser.getSize(object.opt("maxWidth"), maxWidth);
        maxHeight = JsonLayoutParser.getSize(object.opt("maxHeight"), maxHeight);

        gravity = JsonLayoutParser.getGravity(object.opt("gravity"), gravity);
        textSize = JsonLayoutParser.getSize(object.opt("textSize"), textSize);
        textColor = JsonLayoutParser.getColor(object.opt("textColor"), textColor);
        text = object.optString("text");
        hint = object.optString("hint");
    }

    @Override
    protected void onInflateView(T view) {
        super.onInflateView(view);
        if (maxWidth >= 0)
            view.setMinimumWidth(maxWidth);
        if (maxHeight >= 0)
            view.setMinimumHeight(maxHeight);

        if (gravity >= 0) {
            view.setGravity(gravity);
        }
        if (textSize >= 0)
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (textColor != 0)
            view.setTextColor(textColor);
        if (text != null)
            view.setText(text);
        if (hint != null)
            view.setHint(hint);
    }
}
