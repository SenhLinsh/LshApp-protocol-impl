package com.linsh.protocol.impl.ui.view;

import android.annotation.SuppressLint;
import android.widget.LinearLayout;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/04
 *    desc   :
 * </pre>
 */
public class LinearLayoutInfo<T extends LinearLayout> extends ViewGroupInfo<T> {

    public int gravity = -1;
    public int orientation = -1;

    @Override
    public void onDeserialize(JsonObject jsonObject, JsonDeserializationContext context, ViewInfo parent) {
        super.onDeserialize(jsonObject, context, parent);
        gravity = JsonLayoutParser.getGravity(jsonObject.get("gravity"), gravity);
        orientation = JsonLayoutParser.getOrientation(jsonObject.get("orientation"), orientation);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onInflateTarget(T view) {
        super.onInflateTarget(view);
        if (gravity >= 0)
            view.setGravity(gravity);
        if (orientation >= 0)
            view.setOrientation(orientation);
    }

    public static class LayoutParamsInfo<T extends LinearLayout.LayoutParams> extends ViewGroupInfo.LayoutParamsInfo<T> {

        public int weight = -1;
        public int gravity = -1;

        @Override
        protected void onDeserialize(JsonObject jsonObject) {
            super.onDeserialize(jsonObject);
            JsonElement element = (element = jsonObject.get("weight")) == null ? jsonObject.get("layout_weight") : element;
            weight = JsonElementUtils.getInt(element, weight);
            gravity = JsonLayoutParser.getGravity(jsonObject.get("layout_gravity"), gravity);
        }

        @Override
        protected T getLayoutParams() {
            return (T) new LinearLayout.LayoutParams(width, height);
        }

        @Override
        protected void onInflateLayoutParams(T layoutParams) {
            super.onInflateLayoutParams(layoutParams);
            if (gravity >= 0) layoutParams.gravity = gravity;
            if (weight >= 0) layoutParams.weight = weight;
        }
    }
}
