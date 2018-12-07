package com.linsh.protocol.impl.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.JsonObject;
import com.linsh.utilseverywhere.ClassUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/06
 *    desc   :
 * </pre>
 */
class InfoIdentifier {

    static ViewGroupInfo.LayoutParamsInfo getLayoutParamsInfo(ViewInfo parent) {
        ViewGroupInfo.LayoutParamsInfo layoutParamsInfo = null;
        if (parent instanceof ViewGroupInfo) {
            if (parent instanceof LinearLayoutInfo) {
                layoutParamsInfo = new LinearLayoutInfo.LayoutParamsInfo();
            }
        }
        if (layoutParamsInfo == null) {
            layoutParamsInfo = new ViewGroupInfo.LayoutParamsInfo();
        }
        return layoutParamsInfo;
    }

    static ViewInfo getViewInfo(JsonObject jsonObject) {
        String name = jsonObject.get("name").getAsString();
        if (name == null)
            throw new IllegalArgumentException("ViewInfo 必须携带 name 属性");

        Class<? extends View> viewClass;
        Class<? extends ViewInfo> viewInfoClass = null;
        try {
            Class[] viewAndInfoClass = ProtocolRegister.getViewAndInfoClass(name);
            if (viewAndInfoClass != null) {
                viewClass = viewAndInfoClass[1];
                viewInfoClass = viewAndInfoClass[0];
            } else {
                String className = adaptName(name);
                viewClass = (Class<? extends View>) Class.forName(className);
                Class tempClass = viewClass.getSuperclass();
                while (tempClass != null) {
                    viewAndInfoClass = ProtocolRegister.getViewAndInfoClass(tempClass.getSimpleName());
                    if (viewAndInfoClass != null) {
                        viewInfoClass = viewAndInfoClass[0];
                        break;
                    }
                }
            }
            if (viewInfoClass == null)
                throw new IllegalArgumentException("无法找到匹配的 ViewInfo, name = " + name);

            ViewInfo info = (ViewInfo) ClassUtils.newInstance(viewInfoClass);
            info.name = viewClass;
            return info;
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("无法解析 View 的类: " + name);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("ViewInfo 指定的类名必须继承自 " + View.class.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String adaptName(String name) {
        if (name == null)
            return null;
        if (name.contains("."))
            return name;
        switch (name) {
            case "View":
                return View.class.getName();
            case "RecyclerView":
                return RecyclerView.class.getName();
        }
        return "android.widget." + name;
    }
}
