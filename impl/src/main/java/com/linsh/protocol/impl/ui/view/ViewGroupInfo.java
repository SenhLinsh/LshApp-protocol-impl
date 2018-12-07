package com.linsh.protocol.impl.ui.view;

import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
    public void onDeserialize(JsonObject jsonObject, JsonDeserializationContext context, ViewInfo parent) {
        super.onDeserialize(jsonObject, context, parent);
        JsonElement element = jsonObject.get("children");
        if (element != null && element.isJsonArray()) {
            children = new ArrayList<>();
            JsonArray jsonArray = element.getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                children.add(context.deserialize(jsonElement, ViewInfo.class));
            }
        }
    }

    @Override
    public void onInflateTarget(T view) {
        super.onInflateTarget(view);
        if (children != null) {
            for (ViewInfo child : children) {
                JsonLayoutInflater.from(view.getContext()).inflate(child, view, true);
            }
        }
    }

    public static class LayoutParamsInfo<T extends ViewGroup.MarginLayoutParams> {
        public int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int margin;
        public int[] margins;

        protected void onDeserialize(JsonObject jsonObject) {
            JsonElement element = (element = jsonObject.get("width")) == null ? jsonObject.get("layout_width") : element;
            width = JsonLayoutParser.getSize(element, width);
            element = (element = jsonObject.get("height")) == null ? jsonObject.get("layout_height") : element;
            height = JsonLayoutParser.getSize(element, height);
            element = (element = jsonObject.get("margin")) == null ? jsonObject.get("layout_margin") : element;
            margin = JsonLayoutParser.getSize(element, margin);
            element = (element = jsonObject.get("margins")) == null ? jsonObject.get("layout_margins") : element;
            margins = JsonLayoutParser.getSizeArray(element, 0);
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
