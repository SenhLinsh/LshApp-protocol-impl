package com.linsh.protocol.impl.ui.view;

import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/04
 *    desc   :
 * </pre>
 */
public class ViewGroupInfo<T extends ViewGroup> extends ViewInfo<T> {

    public List<ViewInfo> children;

    @Override
    protected void onDeserialize(JSONObject object, ViewInfo parent) throws JSONException {
        super.onDeserialize(object, parent);
        JSONArray array = object.optJSONArray("children");
        if (array != null) {
            children = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                children.add(JsonLayoutParser.parse(array.optJSONObject(i), parent));
            }
        }
    }

    @Override
    protected void onInflateView(T view) {
        super.onInflateView(view);
        if (children != null) {
            for (ViewInfo child : children) {
                child.inflateView(view.getContext(), view, true);
            }
        }
    }

    public static class LayoutParamsInfo<T extends ViewGroup.MarginLayoutParams> {
        public int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int margin;
        public int[] margins;

        protected void onDeserialize(JSONObject object) throws JSONException {
            Object temp;
            width = JsonLayoutParser.getSize((temp = object.opt("width")) != null ? temp : object.opt("layout_width"), width);
            height = JsonLayoutParser.getSize((temp = object.opt("height")) != null ? temp : object.opt("layout_height"), height);
            margin = JsonLayoutParser.getSize((temp = object.opt("margin")) != null ? temp : object.opt("layout_margin"), margin);
            JSONArray jsonArray = object.optJSONArray("margins");
            if (jsonArray == null) jsonArray = object.optJSONArray("layout_margins");
            margins = JsonLayoutParser.getSizeArray(jsonArray, 0);
        }

        protected T getLayoutParams() {
            return (T) new ViewGroup.MarginLayoutParams(width, height);
        }

        protected void onInflateLayoutParams(T layoutParams) {
            if (margin > 0) {
                layoutParams.leftMargin = margin;
                layoutParams.topMargin = margin;
                layoutParams.rightMargin = margin;
                layoutParams.bottomMargin = margin;
            }
            if (margins != null && margins.length >= 4) {
                layoutParams.leftMargin = margins[0];
                layoutParams.topMargin = margins[1];
                layoutParams.rightMargin = margins[2];
                layoutParams.bottomMargin = margins[3];
            }
        }
    }
}
