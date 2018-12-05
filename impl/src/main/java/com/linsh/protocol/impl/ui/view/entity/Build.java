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

    private static final Map<Class<? extends View>, Class<? extends ViewInfo>> VIEW_INFO_BUILDING = new HashMap<>();
    private static final Map<String, Class<? extends ProtocolInfo>> PROTOCOL_INFO_BUILDING = new HashMap<>();

    static {
        VIEW_INFO_BUILDING.put(View.class, ViewInfo.class);
        VIEW_INFO_BUILDING.put(TextView.class, TextViewInfo.class);
        VIEW_INFO_BUILDING.put(ImageView.class, ImageViewInfo.class);
        VIEW_INFO_BUILDING.put(ViewGroup.class, ViewGroupInfo.class);
        VIEW_INFO_BUILDING.put(LinearLayout.class, LinearLayoutInfo.class);
    }

    static Class<? extends ViewInfo> getViewInfoClass(Class<? extends View> viewClass) {
        Class clazz = viewClass;
        while (clazz != null) {
            Class<? extends ViewInfo> viewInfoClass = VIEW_INFO_BUILDING.get(clazz);
            if (viewInfoClass != null)
                return viewInfoClass;
            clazz = clazz.getSuperclass();
        }
        throw new IllegalArgumentException("无法匹配到指定的 ViewInfo: " + viewClass);
    }

    static Class<? extends ProtocolInfo> getProtocolInfo(String name) {
        return PROTOCOL_INFO_BUILDING.get(name);
    }
}
