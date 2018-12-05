package com.linsh.protocol.impl.ui.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linsh.protocol.ui.view.ViewProtocol;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/05
 *    desc   :
 * </pre>
 */
public class ProtocolRegister {

    private static final Map<Class<? extends ViewProtocol>, Set<Class<? extends ViewProtocol>>> PROTOCOL_REGISTERS = new HashMap<>();
    private static final Map<String, Class<? extends ViewProtocol>> ALL_REGISTERS = new HashMap<>();
    private static final Map<Class<? extends View>, Class<? extends ViewInfo>> VIEW_INFO_REGISTER = new HashMap<>();
    private static final Map<String, Class<? extends ProtocolInfo>> PROTOCOL_INFO_REGISTER = new HashMap<>();


    static <T extends ViewProtocol> void registerProtocol(Class<T> protocol, Class<? extends T> protocolImpl) {
        Set<Class<? extends ViewProtocol>> protocolImpls = PROTOCOL_REGISTERS.get(protocol);
        if (protocolImpls == null) {
            protocolImpls = new LinkedHashSet<>();
            PROTOCOL_REGISTERS.put(protocol, protocolImpls);
        }
        protocolImpls.add(protocolImpl);
        ALL_REGISTERS.put(protocolImpl.getSimpleName(), protocolImpl);
    }

    static <T extends ViewProtocol> void unregisterProtocol(Class<T> protocol, Class<? extends T> protocolImpl) {
        Set<Class<? extends ViewProtocol>> protocolImpls = PROTOCOL_REGISTERS.get(protocol);
        if (protocolImpls != null) {
            protocolImpls.remove(protocolImpl);
        }
        ALL_REGISTERS.remove(protocolImpl.getSimpleName());
    }

    static Class<? extends ViewProtocol> getProtocolImpl(String protocolImplName) {
        return ALL_REGISTERS.get(protocolImplName);
    }

    static {
        VIEW_INFO_REGISTER.put(View.class, ViewInfo.class);
        VIEW_INFO_REGISTER.put(TextView.class, TextViewInfo.class);
        VIEW_INFO_REGISTER.put(ImageView.class, ImageViewInfo.class);
        VIEW_INFO_REGISTER.put(ViewGroup.class, ViewGroupInfo.class);
        VIEW_INFO_REGISTER.put(LinearLayout.class, LinearLayoutInfo.class);
    }

    static Class<? extends ViewInfo> getViewInfoClass(Class<? extends View> viewClass) {
        Class clazz = viewClass;
        while (clazz != null) {
            Class<? extends ViewInfo> viewInfoClass = VIEW_INFO_REGISTER.get(clazz);
            if (viewInfoClass != null)
                return viewInfoClass;
            clazz = clazz.getSuperclass();
        }
        throw new IllegalArgumentException("无法匹配到指定的 ViewInfo: " + viewClass);
    }

    static Class<? extends ProtocolInfo> getProtocolInfo(String name) {
        return PROTOCOL_INFO_REGISTER.get(name);
    }
}
