package com.linsh.protocol.impl.ui.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/06
 *    desc   :
 * </pre>
 */
public class RecyclerViewInfo<T extends RecyclerView> extends ViewGroupInfo<T> {

    public int orientation = LinearLayout.VERTICAL;
    public int spanCount;

    @Override
    public void onDeserialize(JsonObject jsonObject, JsonDeserializationContext context, ViewInfo parent) {
        super.onDeserialize(jsonObject, context, parent);
        orientation = JsonLayoutParser.getOrientation(jsonObject.get("orientation"), orientation);
        spanCount = JsonObjectUtils.getInt(jsonObject.get("spanCount"), spanCount);
    }

    @Override
    public void onInflateTarget(T view) {
        super.onInflateTarget(view);
        if (spanCount <= 0) {
            view.setLayoutManager(new LinearLayoutManager(view.getContext(), orientation, false));
        } else {
            view.setLayoutManager(new GridLayoutManager(view.getContext(), spanCount, orientation, false));
        }
    }
}
