package com.linsh.protocol.impl.ui.view;

import android.content.Context;
import android.widget.EditText;

import com.linsh.protocol.ui.view.EditTextHelper;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/27
 *    desc   :
 * </pre>
 */
class EditTextHelperImpl<T extends EditTextHelper, V extends EditText> extends TextViewHelperImpl<T, V> implements EditTextHelper<T, V> {

    public EditTextHelperImpl(Context context) {
        super(context);
    }

    @Override
    public T setSelection(int index) {
        view.setSelection(index);
        return (T) this;
    }

    @Override
    public T setSelection(int start, int stop) {
        view.setSelection(start, stop);
        return (T) this;
    }
}
