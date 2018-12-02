package com.linsh.protocol.impl.ui.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linsh.protocol.Client;
import com.linsh.utilseverywhere.ClassUtils;
import com.linsh.utilseverywhere.FileUtils;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/28
 *    desc   :
 * </pre>
 */
public class JsonLayoutInflater {

    private final Context context;
    private JsonLayoutFinder jsonLayoutFinder;

    private JsonLayoutInflater(Context context) {
        this.context = context;
    }

    public static JsonLayoutInflater from(Context context) {
        return new JsonLayoutInflater(context);
    }

    public View inflate(File file, ViewGroup parent) {
        return inflate(FileUtils.readAsString(file), parent);
    }

    public View inflate(File file, ViewGroup parent, boolean attachToRoot) {
        return inflate(FileUtils.readAsString(file), parent, attachToRoot);
    }

    public View inflate(String json, ViewGroup parent) {
        return inflate(JsonLayoutParser.parse(json), parent);
    }

    public View inflate(String json, ViewGroup parent, boolean attachToRoot) {
        return inflate(JsonLayoutParser.parse(json), parent, attachToRoot);
    }

    public View inflate(ViewInfo info) {
        return inflate(info, null);
    }

    public View inflate(ViewInfo info, ViewGroup parent) {
        return inflate(info, parent, parent != null);
    }

    @SuppressLint("WrongConstant")
    public View inflate(ViewInfo info, ViewGroup parent, boolean attachToRoot) {
        if (info == null)
            throw new IllegalArgumentException("ViewInfo 不能为 null");
        if (info.name == null)
            throw new IllegalArgumentException("控件包名(ViewInfo.name) 不能为 null");
        View view;
        try {
            view = (View) ClassUtils.newInstance(Class.forName(info.name), new Class[]{Context.class}, new Object[]{context});
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("无法识别控件包名: " + info.name, e);
        } catch (Exception e) {
            throw new IllegalArgumentException("控件生成失败: " + info.name, e);
        }
        if (info.id != null && context instanceof Activity) {
            getJsonLayoutFinder().setKey(view, info.id);
        }
        if (info.maxWidth >= 0) {
            if (view instanceof TextView)
                ((TextView) view).setMaxWidth(info.maxWidth);
            if (view instanceof ImageView)
                ((ImageView) view).setMaxWidth(info.maxWidth);
        }
        if (info.maxHeight >= 0) {
            if (view instanceof TextView)
                ((TextView) view).setMaxHeight(info.maxHeight);
            if (view instanceof ImageView)
                ((ImageView) view).setMaxHeight(info.maxHeight);
        }

        if (info.minHeight >= 0)
            view.setMinimumHeight(info.minHeight);
        if (info.minWidth >= 0)
            view.setMinimumWidth(info.minWidth);
        if (info.padding != 0)
            view.setPadding(info.padding, info.padding, info.padding, info.padding);
        if (info.paddings != null && info.paddings.length >= 4)
            view.setPadding(info.paddings[0], info.paddings[1], info.paddings[2], info.paddings[3]);

        if (info.background != 0)
            view.setBackgroundColor(info.background);
        if (info.elevation != 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            view.setElevation(info.elevation);

        if (info.scaleX >= 0)
            view.setScaleX(info.scaleX);
        if (info.scaleY >= 0)
            view.setScaleY(info.scaleY);
        if (info.visibility != 0)
            view.setVisibility(info.visibility);
        if (info.orientation >= 0) {
            if (view instanceof LinearLayout)
                ((LinearLayout) view).setOrientation(info.orientation);
        }
        if (info.scaleType != null && view instanceof ImageView)
            ((ImageView) view).setScaleType(info.scaleType);
        if (info.gravity >= 0) {
            if (view instanceof TextView)
                ((TextView) view).setGravity(info.gravity);
            else if (view instanceof LinearLayout)
                ((LinearLayout) view).setGravity(info.gravity);
            else if (view instanceof RelativeLayout)
                ((RelativeLayout) view).setGravity(info.gravity);
        }
        if (info.src != null) {
            // TODO: 2018/11/29
        }
        if (view instanceof TextView) {
            if (info.textSize >= 0)
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, info.textSize);
            if (info.textColor != 0)
                ((TextView) view).setTextColor(info.textColor);
            if (info.text != null)
                ((TextView) view).setText(info.text);
            if (info.hint != null)
                ((TextView) view).setHint(info.hint);
        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(info.width, info.height);
        if (parent instanceof FrameLayout) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(params);
            setMargin(info, layoutParams);
            if (info.layout_gravity >= 0)
                layoutParams.gravity = info.layout_gravity;
            params = layoutParams;
        } else if (parent instanceof LinearLayout) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(params);
            setMargin(info, layoutParams);
            if (info.weight >= 0)
                layoutParams.weight = info.weight;
            if (info.layout_gravity >= 0)
                layoutParams.gravity = info.layout_gravity;
            params = layoutParams;
        } else if (parent instanceof RelativeLayout) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(params);
            setMargin(info, layoutParams);
            params = layoutParams;
        } else if (parent instanceof RecyclerView) {
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(params);
            setMargin(info, layoutParams);
            params = layoutParams;
        }
        if (info.weight >= 0) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(params);
            layoutParams.weight = info.weight;
            params = layoutParams;
        }
        view.setLayoutParams(params);
        if (parent != null && attachToRoot)
            parent.addView(view);
        if (info.children != null && view instanceof ViewGroup) {
            for (ViewInfo childInfo : info.children) {
                inflate(childInfo, (ViewGroup) view, true);
            }
        }
        if (info.protocol != null) {
            if (context instanceof Activity) {
                getJsonLayoutFinder().setViewProtocol(view, info.protocol);
            }
        }
        return view;
    }

    private JsonLayoutFinder getJsonLayoutFinder() {
        if (jsonLayoutFinder == null)
            jsonLayoutFinder = Client.activity().target((Activity) context).useSubscriber(JsonLayoutFinder.class);
        return jsonLayoutFinder;
    }

    private void setMargin(ViewInfo info, ViewGroup.MarginLayoutParams layoutParams) {
        if (info.margin > 0) {
            layoutParams.leftMargin = info.margin;
            layoutParams.topMargin = info.margin;
            layoutParams.rightMargin = info.margin;
            layoutParams.bottomMargin = info.margin;
        }
        if (info.margins != null) {
            layoutParams.leftMargin = info.margins[0];
            layoutParams.topMargin = info.margins[1];
            layoutParams.rightMargin = info.margins[2];
            layoutParams.bottomMargin = info.margins[3];
        }
    }
}
