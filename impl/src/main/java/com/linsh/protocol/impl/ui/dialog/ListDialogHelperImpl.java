package com.linsh.protocol.impl.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.RecyclerView;

import com.linsh.dialog.LshDialog;
import com.linsh.protocol.ui.dialog.ListDialogHelper;
import com.linsh.protocol.ui.view.ViewHelper;
import com.linsh.utilseverywhere.ListUtils;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/24
 *    desc   :
 * </pre>
 */
class ListDialogHelperImpl implements ListDialogHelper {

    private final LshDialog.ListDialogBuilder builder;
    private LshDialog dialog;

    public ListDialogHelperImpl(Activity activity) {
        builder = new LshDialog(activity).buildList();
    }

    public ListDialogHelperImpl(Activity activity, List<? extends CharSequence> items) {
        builder = new LshDialog(activity).buildList();
        setItems(items);
    }

    @Override
    public ListDialogHelper singleChoice() {
        return this;
    }

    @Override
    public ListDialogHelper singleChoice(int checkedItem) {
        return this;
    }

    @Override
    public ListDialogHelper multiChoice() {
        return this;
    }

    @Override
    public ListDialogHelper multiChoice(boolean[] checkedItems) {
        return this;
    }

    @Override
    public ListDialogHelper setItems(CharSequence[] list) {
        return setItems(list, null);
    }

    @Override
    public ListDialogHelper setItems(CharSequence[] list, OnItemClickListener listener) {
        return setItems(Arrays.asList(list), listener);
    }

    @Override
    public ListDialogHelper setItems(List<? extends CharSequence> list) {
        return setItems(list, null);
    }

    @Override
    public ListDialogHelper setItems(List<? extends CharSequence> list, final OnItemClickListener listener) {
        List<String> stringList = ListUtils.toStringList(list, null);
        builder.setList(stringList);
        if (listener != null)
            builder.setOnItemClickListener(new LshDialog.OnItemClickListener() {
                @Override
                public void onClick(LshDialog lshDialog, int i) {
                    listener.onItemClick(ListDialogHelperImpl.this, i);
                }
            });
        return this;
    }

    @Override
    public ListDialogHelper setItems(RecyclerView.Adapter<?> adapter) {
        return this;
    }

    @Override
    public ListDialogHelper setOnItemClickListener(final OnItemClickListener listener) {
        if (listener != null)
            builder.setOnItemClickListener(new LshDialog.OnItemClickListener() {
                @Override
                public void onClick(LshDialog lshDialog, int i) {
                    listener.onItemClick(ListDialogHelperImpl.this, i);
                }
            });
        else
            builder.setOnItemClickListener(null);
        return this;
    }

    @Override
    public ListDialogHelper title(CharSequence title) {
        builder.setTitle(title.toString());
        return this;
    }

    @Override
    public ListDialogHelper positiveBtn(OnClickListener<ListDialogHelper> listener) {
        return positiveBtn("确定", listener);
    }

    @Override
    public ListDialogHelper positiveBtn(CharSequence text, OnClickListener<ListDialogHelper> listener) {
        return this;
    }

    @Override
    public ListDialogHelper negativeBtn(OnClickListener<ListDialogHelper> listener) {
        return this;
    }

    @Override
    public ListDialogHelper negativeBtn(CharSequence text, OnClickListener<ListDialogHelper> listener) {
        return this;
    }

    @Override
    public ListDialogHelper neutralBtn(OnClickListener<ListDialogHelper> listener) {
        return this;
    }

    @Override
    public ListDialogHelper neutralBtn(CharSequence text, OnClickListener<ListDialogHelper> listener) {
        return this;
    }

    @Override
    public ListDialogHelper show() {
        if (dialog != null)
            dialog.show();
        else
            dialog = builder.show();
        return this;
    }

    @Override
    public ListDialogHelper dismiss() {
        if (dialog != null)
            dialog.dismiss();
        return this;
    }

    @Override
    public Dialog build() {
        return null;
    }

    @Override
    public ViewHelper getContentView() {
        return null;
    }
}
