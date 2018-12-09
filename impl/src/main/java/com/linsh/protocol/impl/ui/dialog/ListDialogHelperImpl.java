package com.linsh.protocol.impl.ui.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.linsh.protocol.ui.dialog.ListDialogProtocol;

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
class ListDialogHelperImpl extends BaseDialogHelperImpl<ListDialogProtocol> implements ListDialogProtocol {

    private List<? extends CharSequence> data;
    private OnItemClickListener listener;
    private int checkedItem = -1;
    private boolean[] checkedItems;
    private int type;

    public ListDialogHelperImpl(Activity activity) {
        this(activity, null);
    }

    public ListDialogHelperImpl(Activity activity, List<? extends CharSequence> items) {
        super(activity);
        data = items;
    }

    @Override
    public ListDialogProtocol singleChoice() {
        type = 1;
        return this;
    }

    @Override
    public ListDialogProtocol singleChoice(int checkedItem) {
        type = 1;
        this.checkedItem = checkedItem;
        return this;
    }

    @Override
    public ListDialogProtocol multiChoice() {
        type = 2;
        return this;
    }

    @Override
    public ListDialogProtocol multiChoice(boolean[] checkedItems) {
        type = 2;
        this.checkedItems = checkedItems;
        return this;
    }

    @Override
    public ListDialogProtocol setItems(CharSequence[] list) {
        return setItems(list, null);
    }

    @Override
    public ListDialogProtocol setItems(CharSequence[] list, OnItemClickListener listener) {
        return setItems(Arrays.asList(list), listener);
    }

    @Override
    public ListDialogProtocol setItems(List<? extends CharSequence> list) {
        return setItems(list, null);
    }

    @Override
    public ListDialogProtocol setItems(List<? extends CharSequence> list, final OnItemClickListener listener) {
        this.data = list;
        this.listener = listener;
        return this;
    }

    @Override
    public ListDialogProtocol setItems(RecyclerView.Adapter<?> adapter) {
        return this;
    }

    @Override
    public ListDialogProtocol setOnItemClickListener(final OnItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public ListDialogProtocol show() {
        if (data != null) {
            CharSequence[] array = data.toArray(new CharSequence[data.size()]);
            if (type == 1) {
                builder.setSingleChoiceItems(array, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which >= 0) {
                            if (listener != null)
                                listener.onItemClick(ListDialogHelperImpl.this, which);
                        }
                    }
                });
            } else if (type == 2) {
                builder.setMultiChoiceItems(array, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (which >= 0) {
                            if (listener != null)
                                listener.onItemClick(ListDialogHelperImpl.this, which);
                        }
                    }
                });
            } else {
                builder.setItems(array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which >= 0) {
                            if (listener != null)
                                listener.onItemClick(ListDialogHelperImpl.this, which);
                        }
                    }
                });
            }
        }
        return super.show();
    }

    @Override
    public View getContentView() {
        return null;
    }
}
