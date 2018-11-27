package com.linsh.protocol.impl.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.linsh.protocol.ui.view.TextViewHelper;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/27
 *    desc   :
 * </pre>
 */
public class TextViewHelperImpl<T extends TextViewHelper, V extends TextView> extends ViewHelperImpl<T, V> implements TextViewHelper<T, V> {

    public TextViewHelperImpl(Context context) {
        super(context);
    }

    @Override
    public T setText(CharSequence text) {
        view.setText(text);
        return (T) this;
    }

    @Override
    public T setText(int resId) {
        view.setText(resId);
        return (T) this;
    }

    @Override
    public T setTextColor(int color) {
        view.setTextColor(color);
        return (T) this;
    }

    @Override
    public T setTextSize(float size) {
        view.setTextSize(size);
        return (T) this;
    }

    @Override
    public T setTextSize(int unit, float size) {
        view.setTextSize(unit, size);
        return (T) this;
    }

    @Override
    public T setHint(CharSequence text) {
        view.setHint(text);
        return (T) this;
    }

    @Override
    public T setHint(int resId) {
        view.setHint(resId);
        return (T) this;
    }

    @Override
    public T setHintTextColor(int color) {
        view.setHintTextColor(color);
        return (T) this;
    }

    @Override
    public T setLines(int lines) {
        view.setLines(lines);
        return (T) this;
    }

    @Override
    public T setLineSpacing(float add, float mult) {
        view.setLineSpacing(add, mult);
        return (T) this;
    }

    @Override
    public T setMaxLines(int maxLines) {
        view.setMaxLines(maxLines);
        return (T) this;
    }

    @Override
    public T setMinLines(int minLines) {
        view.setMinLines(minLines);
        return (T) this;
    }

    @Override
    public T setEllipsize(TextUtils.TruncateAt where) {
        view.setEllipsize(where);
        return (T) this;
    }
}
