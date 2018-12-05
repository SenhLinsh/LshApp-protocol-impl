package com.linsh.protocol.impl.ui.view;

import android.annotation.SuppressLint;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

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
    protected void onDeserialize(JSONObject object, ViewInfo parent) throws JSONException {
        super.onDeserialize(object, parent);
        gravity = JsonLayoutParser.getGravity(object.opt("gravity"), gravity);
        orientation = JsonLayoutParser.getOrientation(object.opt("orientation"), orientation);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onInflateView(T view) {
        super.onInflateView(view);
        if (gravity >= 0)
            view.setGravity(gravity);
        if (orientation >= 0)
            view.setOrientation(orientation);
    }

    public static class LayoutParamsInfo<T extends LinearLayout.LayoutParams> extends ViewGroupInfo.LayoutParamsInfo<T> {

        public int weight = -1;
        public int gravity = -1;

        @Override
        protected void onDeserialize(JSONObject object) throws JSONException {
            super.onDeserialize(object);
            weight = object.optInt("weight", object.optInt("layout_weight", weight));
            gravity = JsonLayoutParser.getGravity(object.opt("layout_gravity"), gravity);
        }

        @Override
        protected T getLayoutParams() {
            return (T) new LinearLayout.LayoutParams(width, height);
        }

        @Override
        protected void onInflateLayoutParams(T layoutParams) {
            super.onInflateLayoutParams(layoutParams);
            layoutParams.weight = weight;
        }
    }
}
