package com.linsh.protocol.impl.ui.view;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/28
 *    desc   : 通过 Json 类表示一个 View 类的信息
 * </pre>
 */
public class ViewInfo {

    public String name;
    public String id;

    public int maxWidth = -1;
    public int minWidth = -1;
    public int maxHeight = -1;
    public int minHeight = -1;
    public int padding;
    public int[] paddings;

    public int background;
    public int elevation;
    public float scaleX = 1;
    public float scaleY = 1;
    public int visibility;

    // TextView & LinearLayout
    public int gravity = -1;
    // LinearLayout
    public int orientation = -1;
    // ImageView
    public ImageView.ScaleType scaleType;
    public String src;
    // TextView
    public int textSize = -1;
    public int textColor;
    public String text;
    public String hint;

    // LayoutParams
    public int width = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int height = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int margin;
    public int[] margins;
    public int weight = -1;
    public int layout_gravity = -1;

    public List<ViewInfo> children;
    public ViewProtocolInfo protocol;
}
