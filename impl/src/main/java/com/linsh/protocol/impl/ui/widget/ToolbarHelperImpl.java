package com.linsh.protocol.impl.ui.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.linsh.protocol.ui.widget.ToolbarHelper;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/25
 *    desc   :
 * </pre>
 */
class ToolbarHelperImpl implements ToolbarHelper {

    private AppCompatActivity activity;
    private ActionBar actionBar;

    public ToolbarHelperImpl(Activity activity) {
        if (activity instanceof AppCompatActivity) {
            this.activity = (AppCompatActivity) activity;
            actionBar = this.activity.getSupportActionBar();
        } else {
            throw new IllegalArgumentException("使用 AppCompatActivity 并不是什么难事吧? 为何不呢!");
        }
    }

    @Override
    public ToolbarHelper setToolbar(Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        return this;
    }

    @Override
    public ToolbarHelper setBackgroundColor(int color) {
        actionBar.setBackgroundDrawable(new ColorDrawable(color));
        return this;
    }

    @Override
    public ToolbarHelper setTitle(CharSequence title) {
        actionBar.setTitle(title);
        return this;
    }

    @Override
    public ToolbarHelper setSubtitle(CharSequence subtitle) {
        actionBar.setSubtitle(subtitle);
        return this;
    }
}
