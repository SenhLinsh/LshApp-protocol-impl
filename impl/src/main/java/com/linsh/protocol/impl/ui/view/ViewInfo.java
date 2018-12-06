package com.linsh.protocol.impl.ui.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.linsh.protocol.Client;
import com.linsh.utilseverywhere.ClassUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/28
 *    desc   : 通过 Json 类表示一个 View 类的信息
 * </pre>
 */
public class ViewInfo<T extends View> {
    // View
    public Class<T> name;
    public String id;

    public ViewGroupInfo.LayoutParamsInfo layoutParams;

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

    public ProtocolInfo protocol;

    protected void onDeserialize(JsonObject jsonObject, JsonDeserializationContext context, ViewInfo parent) {
        id = JsonObjectUtils.getString(jsonObject.get("id"));

        layoutParams = JsonLayoutParser.getLayoutParamsInfo(jsonObject, "layoutParams", parent);

        minWidth = JsonLayoutParser.getSize(jsonObject.get("minWidth"), minWidth);
        minHeight = JsonLayoutParser.getSize(jsonObject.get("minHeight"), minHeight);
        padding = JsonLayoutParser.getSize(jsonObject.get("padding"), padding);
        paddings = JsonLayoutParser.getSizeArray(jsonObject.get("paddings"), padding);

        background = JsonLayoutParser.getBackground(jsonObject.get("background"), context);
        elevation = JsonLayoutParser.getSize(jsonObject.get("elevation"), elevation);
        scaleX = JsonObjectUtils.getFloat(jsonObject.get("scaleX"), scaleX);
        scaleY = JsonObjectUtils.getFloat(jsonObject.get("scaleY"), scaleY);
        visibility = JsonLayoutParser.getVisibility(jsonObject.get("visibility"), visibility);

        protocol = JsonLayoutParser.getProtocol(jsonObject.get("protocol"), context);
    }

    public View inflateView(Context context, ViewGroup parent, boolean attachToRoot) {
        if (name == null)
            throw new IllegalArgumentException("控件包名(ViewInfo.name) 不能为 null");
        T view;
        try {
            view = (T) ClassUtils.newInstance(name, new Class[]{Context.class}, new Object[]{context});
        } catch (Exception e) {
            throw new IllegalArgumentException("控件生成失败: " + name, e);
        }
        // 填充 View 属性
        onInflateView(view);
        // 添加到父 view 中
        if (parent != null && attachToRoot)
            parent.addView(view);
        // 设置 protocol
        if (protocol != null && view.getContext() instanceof Activity) {
            Client.activity().target((Activity) view.getContext()).useSubscriber(JsonLayoutFinder.class).setViewProtocol(view, protocol);
        }
        return view;
    }

    protected void onInflateView(T view) {
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

        if (padding != 0)
            view.setPadding(padding, padding, padding, padding);
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

        if (scaleX >= 0)
            view.setScaleX(scaleX);
        if (scaleY >= 0)
            view.setScaleY(scaleY);
        if (visibility != 0)
            view.setVisibility(visibility);
    }
}
