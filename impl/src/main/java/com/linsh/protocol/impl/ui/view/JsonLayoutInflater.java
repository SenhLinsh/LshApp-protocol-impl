package com.linsh.protocol.impl.ui.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

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
        return inflate(json, parent, parent != null);
    }

    public View inflate(String json, ViewGroup parent, boolean attachToRoot) {
        ViewInfo viewInfo = GsonFactory.parseInfo().fromJson(json, ViewInfo.class);
        return inflate(viewInfo, parent, attachToRoot);
    }

    public View inflate(ViewInfo info) {
        return inflate(info, null);
    }

    public View inflate(ViewInfo info, ViewGroup parent) {
        return inflate(info, parent, parent != null);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public View inflate(ViewInfo info, ViewGroup parent, boolean attachToRoot) {
        if (info == null)
            throw new IllegalArgumentException("ViewInfo 不能为 null");
        if (info.name == null)
            throw new IllegalArgumentException("控件包名(ViewInfo.name) 不能为 null");
        View view;
        try {
            view = (View) ClassUtils.newInstance(info.name, new Class[]{Context.class}, new Object[]{context});
        } catch (Exception e) {
            throw new IllegalArgumentException("控件生成失败: " + info.name, e);
        }
        // 填充 View 属性
        info.onInflateTarget(view);
        // 添加到父 view 中
        if (parent != null && attachToRoot)
            parent.addView(view);
        // 设置 protocol
        if (info.protocol != null && view.getContext() instanceof Activity) {
            Client.activity().target((Activity) view.getContext()).useSubscriber(JsonLayoutFinder.class).setViewProtocol(view, info.protocol);
        }
        return view;
    }
}
