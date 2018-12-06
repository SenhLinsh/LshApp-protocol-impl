package com.linsh.protocol.impl.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linsh.protocol.impl.ui.layout.JsonListViewProtocolImpl;
import com.linsh.protocol.ui.layout.ListViewProtocol;
import com.linsh.protocol.ui.view.ViewProtocol;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/05
 *    desc   :
 * </pre>
 */
// TODO: 2018/12/6  public -> packaged
public class ProtocolRegister {

    private static final Map<Class<? extends ViewProtocol>, Map<Class<? extends ViewProtocol>, Boolean>> PROTOCOL_REGISTERS = new HashMap<>();
    private static final Map<String, Class<? extends ViewProtocol>> ALL_REGISTERS = new HashMap<>();
    private static final Map<Class<? extends View>, Class<? extends ViewInfo>> VIEW_INFO_REGISTER = new HashMap<>();

    static {
        registerProtocol(ListViewProtocol.class, JsonListViewProtocolImpl.class);
    }

    /**
     * 注册 ViewProtocol
     */
    static <T extends ViewProtocol> void registerProtocol(Class<T> protocol, Class<? extends T> protocolImpl) {
        registerProtocol(protocol, protocolImpl, false);
    }

    /**
     * 注册 ViewProtocol
     *
     * @param defaultLayout 是否自带默认布局, 如果自带默认布局, 在 ViewProtocol 没有找到合适的布局时, 将会使用该默认布局
     */
    // TODO: 2018/12/6  public -> packaged
    public static <T extends ViewProtocol> void registerProtocol(Class<T> protocol, Class<? extends T> protocolImpl, boolean defaultLayout) {
        Map<Class<? extends ViewProtocol>, Boolean> protocolImpls = PROTOCOL_REGISTERS.get(protocol);
        if (protocolImpls == null) {
            protocolImpls = new LinkedHashMap<>();
            PROTOCOL_REGISTERS.put(protocol, protocolImpls);
        }
        protocolImpls.put(protocolImpl, defaultLayout);
        ALL_REGISTERS.put(protocolImpl.getSimpleName(), protocolImpl);
    }

    /**
     * 解注册 ViewProtocol
     */
    static <T extends ViewProtocol> void unregisterProtocol(Class<T> protocol, Class<? extends T> protocolImpl) {
        Map<Class<? extends ViewProtocol>, Boolean> protocolImpls = PROTOCOL_REGISTERS.get(protocol);
        if (protocolImpls != null) {
            protocolImpls.remove(protocolImpl);
        }
        ALL_REGISTERS.remove(protocolImpl.getSimpleName());
    }

    /**
     * 查找是否存在自带布局的 ViewProtocol
     */
    static Class<? extends ViewProtocol> findProtocolImplWithDefaulLayout(Class<? extends ViewProtocol> protocol) {
        Map<Class<? extends ViewProtocol>, Boolean> protocolImpls = PROTOCOL_REGISTERS.get(protocol);
        if (protocolImpls != null) {
            for (Map.Entry<Class<? extends ViewProtocol>, Boolean> entry : protocolImpls.entrySet()) {
                if (entry.getValue()) {
                    return entry.getKey();
                }
            }
        }
        return null;
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
        VIEW_INFO_REGISTER.put(RecyclerView.class, RecyclerViewInfo.class);
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
}
