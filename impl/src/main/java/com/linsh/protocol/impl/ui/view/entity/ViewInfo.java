package com.linsh.protocol.impl.ui.view.entity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.linsh.protocol.Client;
import com.linsh.protocol.impl.ui.view.JsonLayoutFinder;
import com.linsh.utilseverywhere.ClassUtils;

import org.json.JSONException;
import org.json.JSONObject;

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

    public DrawableInfo background;
    public int elevation;
    public float scaleX = 1;
    public float scaleY = 1;
    public int visibility = View.VISIBLE;

    public ProtocolInfo protocol;

    protected void onDeserialize(JSONObject object, ViewInfo parent) throws JSONException {
        id = object.optString("id");
        layoutParams = JsonLayoutParser.getLayoutParamsInfo(object, "layoutParams", parent);

        minWidth = JsonLayoutParser.getSize(object.opt("minWidth"), minWidth);
        minHeight = JsonLayoutParser.getSize(object.opt("minHeight"), minHeight);
        padding = JsonLayoutParser.getSize(object.opt("padding"), padding);
        paddings = JsonLayoutParser.getSizeArray(object.optJSONArray("paddings"), 0);

        background = JsonLayoutParser.getDrawableInfo(object.opt("background"));
        elevation = JsonLayoutParser.getSize(object.opt("elevation"), elevation);
        scaleX = (float) object.optDouble("scaleX", scaleX);
        scaleY = (float) object.optDouble("scaleY", scaleY);
        visibility = JsonLayoutParser.getVisibility(object.opt("visibility"), visibility);

        protocol = JsonLayoutParser.getProtocol(object);
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
        onInflateView(view);
        if (parent != null && attachToRoot)
            parent.addView(view);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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

        if (background != null)
            view.setBackground(background.getDrawable());
        if (elevation != 0 && android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            view.setElevation(elevation);

        if (scaleX >= 0)
            view.setScaleX(scaleX);
        if (scaleY >= 0)
            view.setScaleY(scaleY);
        if (visibility != 0)
            view.setVisibility(visibility);

        if (protocol != null && view.getContext() instanceof Activity) {
            Client.activity().target((Activity) view.getContext()).useSubscriber(JsonLayoutFinder.class).setViewProtocol(view, protocol);
        }
    }
}
