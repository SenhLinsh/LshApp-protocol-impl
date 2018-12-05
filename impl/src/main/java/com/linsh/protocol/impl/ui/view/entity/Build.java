package com.linsh.protocol.impl.ui.view.entity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/04
 *    desc   :
 * </pre>
 */
class Build {

    static final Map<Class<? extends View>, Class<? extends ViewInfo>> VIEW_INFO_BUILDING = new HashMap<>();

    static {
        VIEW_INFO_BUILDING.put(View.class, ViewInfo.class);
        VIEW_INFO_BUILDING.put(TextView.class, TextViewInfo.class);
        VIEW_INFO_BUILDING.put(ImageView.class, ImageViewInfo.class);
        VIEW_INFO_BUILDING.put(ViewGroup.class, ViewGroupInfo.class);
        VIEW_INFO_BUILDING.put(LinearLayout.class, LinearLayoutInfo.class);
    }
}
