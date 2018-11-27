package com.linsh.protocol.impl.ui.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linsh.protocol.ui.view.EditTextHelper;
import com.linsh.protocol.ui.view.ImageViewHelper;
import com.linsh.protocol.ui.view.TextViewHelper;
import com.linsh.protocol.ui.view.ViewGroupHelper;
import com.linsh.protocol.ui.view.ViewHelper;
import com.linsh.protocol.ui.view.ViewManager;
import com.linsh.protocol.value.Types;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/26
 *    desc   :
 * </pre>
 */
public class ViewManagerImpl implements ViewManager {

    private final Activity activity;

    public ViewManagerImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public ImageViewHelper image() {
        return new ImageViewHelperImpl(activity);
    }

    @Override
    public ImageViewHelper image(Types types) {
        ImageViewHelperImpl helper = new ImageViewHelperImpl(activity);
        helper.setTypes(types);
        return helper;
    }

    @Override
    public EditTextHelper input() {
        return new EditTextHelperImpl(activity);
    }

    @Override
    public EditTextHelper input(Types types) {
        EditTextHelperImpl helper = new EditTextHelperImpl(activity);
        helper.setTypes(types);
        return helper;
    }

    @Override
    public TextViewHelper text() {
        return new TextViewHelperImpl(activity);
    }

    @Override
    public TextViewHelper text(Types types) {
        TextViewHelperImpl helper = new TextViewHelperImpl(activity);
        helper.setTypes(types);
        return helper;
    }

    @Override
    public ViewHelper view() {
        return new ViewHelperImpl(activity);
    }

    @Override
    public <V extends View> ViewHelper<ViewHelper, V> view(V view) {
        return new ViewHelperImpl<>(view);
    }

    @Override
    public ViewHelper view(Types types) {
        ViewHelperImpl helper = new ViewHelperImpl(activity);
        helper.setTypes(types);
        return helper;
    }

    @Override
    public <V extends View> ViewHelper<ViewHelper, V> view(V view, Types types) {
        ViewHelperImpl<ViewHelper, V> helper = new ViewHelperImpl<>(view);
        helper.setTypes(types);
        return helper;
    }

    @Override
    public <V extends ViewGroup> ViewGroupHelper<ViewGroupHelper, V> viewGroup(V viewGroup) {
        return new ViewGroupHelperImpl<>(activity);
    }

    @Override
    public <V extends ViewGroup> ViewGroupHelper<ViewGroupHelper, V> viewGroup(V viewGroup, Types types) {
        ViewGroupHelperImpl<ViewGroupHelper, V> helper = new ViewGroupHelperImpl<>(activity);
        helper.setTypes(types);
        return helper;
    }

    @Override
    public ViewHelper find(int id) {
        return new ViewHelperImpl(activity.findViewById(id));
    }

    @Override
    public ViewHelper inflate(int layout) {
        return new ViewHelperImpl(LayoutInflater.from(activity).inflate(layout, null));
    }

    @Override
    public ViewHelper inflate(int layout, ViewGroup parent) {
        return new ViewHelperImpl(LayoutInflater.from(activity).inflate(layout, parent));
    }

    @Override
    public ViewHelper inflate(int layout, ViewGroup parent, boolean attach) {
        return new ViewHelperImpl(LayoutInflater.from(activity).inflate(layout, parent, attach));
    }
}
