package com.linsh.protocol.impl.ui.view;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.linsh.protocol.Client;
import com.linsh.protocol.ui.view.Info;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/28
 *    desc   : 通过 Json 类表示一个 View 类的信息
 * </pre>
 */
public class ViewInfo<T extends View> implements Info<T> {
    // View
    public Class<T> name;
    public ViewGroupInfo.LayoutParamsInfo layoutParams;

    public String id;

    public int minWidth = -1;
    public int minHeight = -1;
    public int padding;
    public int[] paddings;

    // int(color) & String(url|path) & DrawableInfo(setBackground)
    public Object background;
    public int elevation;
    public float scaleX = 1;
    public float scaleY = 1;

    public int visibility = View.VISIBLE;
    public Integer alpha;
    public Boolean enabled;
    public Boolean clickable;

    public ProtocolInfo protocol;

    protected void onDeserialize(JsonObject jsonObject, JsonDeserializationContext context, ViewInfo parent) {
        id = JsonElementUtils.getString(jsonObject.get("id"));

        minWidth = JsonLayoutParser.getSize(jsonObject.get("minWidth"), minWidth);
        minHeight = JsonLayoutParser.getSize(jsonObject.get("minHeight"), minHeight);
        padding = JsonLayoutParser.getSize(jsonObject.get("padding"), padding);
        paddings = JsonLayoutParser.getSizeArray(jsonObject.get("paddings"), padding);
        if (paddings == null) {
            int paddingLeft = JsonLayoutParser.getSize(jsonObject.get("paddingLeft"), padding);
            int paddingTop = JsonLayoutParser.getSize(jsonObject.get("paddingTop"), padding);
            int paddingRight = JsonLayoutParser.getSize(jsonObject.get("paddingRight"), padding);
            int paddingBottom = JsonLayoutParser.getSize(jsonObject.get("paddingBottom"), padding);
            if (paddingLeft >= 0 || paddingTop >= 0 || paddingRight >= 0 || paddingBottom >= 0)
                paddings = new int[]{paddingLeft, paddingTop, paddingRight, paddingBottom};
        }
        background = JsonLayoutParser.getBackground(jsonObject.get("background"), context);
        elevation = JsonLayoutParser.getSize(jsonObject.get("elevation"), elevation);
        scaleX = JsonElementUtils.getFloat(jsonObject.get("scaleX"), scaleX);
        scaleY = JsonElementUtils.getFloat(jsonObject.get("scaleY"), scaleY);

        visibility = JsonLayoutParser.getVisibility(jsonObject.get("visibility"), visibility);
        alpha = JsonElementUtils.getIntObj(jsonObject.get("alpha"));
        enabled = JsonElementUtils.getBooleanObj(jsonObject.get("enabled"));
        clickable = JsonElementUtils.getBooleanObj(jsonObject.get("clickable"));


        protocol = JsonLayoutParser.getProtocol(jsonObject.get("protocol"), context);
    }

    @Override
    public void onInflateTarget(T view) {
        if (id != null && view.getContext() instanceof Activity) {
            Client.activity().target((Activity) view.getContext()).useSubscriber(JsonLayoutFinder.class).setKeyId(view, id);
        }
        if (layoutParams != null) {
            ViewGroup.MarginLayoutParams params = layoutParams.getLayoutParams();
            layoutParams.onInflateLayoutParams(params);
            view.setLayoutParams(params);
        } else {
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        if (minHeight >= 0)
            view.setMinimumHeight(minHeight);
        if (minWidth >= 0)
            view.setMinimumWidth(minWidth);

        if (padding != 0) view.setPadding(padding, padding, padding, padding);
        if (paddings != null && paddings.length >= 4)
            view.setPadding(paddings[0], paddings[1], paddings[2], paddings[3]);

        if (background != null) {
            if (background instanceof Integer) {
                view.setBackgroundColor((Integer) background);
            } else if (background instanceof String) {
                Client.image().with((String) background).loadBg(view);
            } else if (background instanceof DrawableInfo) {
                ((DrawableInfo) background).setBackground(view);
            }
        }
        if (elevation != 0 && android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            view.setElevation(elevation);

        if (scaleX >= 0) view.setScaleX(scaleX);
        if (scaleY >= 0) view.setScaleY(scaleY);
        if (visibility != 0) view.setVisibility(visibility);
        if (alpha != null) view.setAlpha(alpha);
        if (enabled != null) view.setEnabled(enabled);
        if (clickable != null) view.setClickable(clickable);
    }
}
