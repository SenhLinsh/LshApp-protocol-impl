package com.linsh.protocol.impl.ui.view;

import android.util.TypedValue;
import android.widget.TextView;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

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
    public Object textColor;
    public String text;
    public String hint;

    @Override
    public void onDeserialize(JsonObject jsonObject, JsonDeserializationContext context, ViewInfo parent) {
        super.onDeserialize(jsonObject, context, parent);
        maxWidth = JsonLayoutParser.getSize(jsonObject.get("maxWidth"), maxWidth);
        maxHeight = JsonLayoutParser.getSize(jsonObject.get("maxHeight"), maxHeight);

        gravity = JsonLayoutParser.getGravity(jsonObject.get("gravity"), gravity);
        textSize = JsonLayoutParser.getSize(jsonObject.get("textSize"), textSize);
        textColor = JsonLayoutParser.getColor(jsonObject.get("textColor"), context);
        text = JsonElementUtils.getString(jsonObject.get("text"));
        hint = JsonElementUtils.getString(jsonObject.get("hint"));
    }

    @Override
    public void onInflateTarget(T view) {
        super.onInflateTarget(view);
        if (maxWidth >= 0)
            view.setMinimumWidth(maxWidth);
        if (maxHeight >= 0)
            view.setMinimumHeight(maxHeight);

        if (gravity >= 0) {
            view.setGravity(gravity);
        }
        if (textSize >= 0)
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (textColor != null) {
            if (textColor instanceof Integer)
                view.setTextColor((int) textColor);
            else if (textColor instanceof ColorInfo)
                view.setTextColor(((ColorInfo) textColor).getColorStateList());
        }
        if (text != null)
            view.setText(text);
        if (hint != null)
            view.setHint(hint);
    }
}
