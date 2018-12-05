package com.linsh.protocol.impl.ui.view.entity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public View inflate(ViewInfo info, ViewGroup parent, boolean attachToRoot) {
        if (info == null)
            throw new IllegalArgumentException("ViewInfo 不能为 null");
        return info.inflateView(context, parent, attachToRoot);
    }
}
