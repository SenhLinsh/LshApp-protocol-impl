package com.linsh.protocol.impl.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linsh.protocol.impl.ui.layout.JsonListViewProtocolImpl;
import com.linsh.protocol.ui.layout.ListViewProtocol;
import com.linsh.protocol.ui.view.Info;
import com.linsh.protocol.ui.view.Register;
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
class ProtocolRegister implements Register {

    private static final Register INSTANCE = new ProtocolRegister();
    private static final Map<Class<? extends ViewProtocol>, Map<Class<? extends ViewProtocol>, Boolean>> PROTOCOL_REGISTERS = new HashMap<>();
    private static final Map<String, Class<? extends ViewProtocol>> ALL_REGISTERS = new HashMap<>();
    private static final Map<String, Class[]> INFO_REGISTER = new HashMap<>();

    static {
        INSTANCE.registerProtocol(ListViewProtocol.class, JsonListViewProtocolImpl.class, true);

        INSTANCE.registerInfo("View", ViewInfo.class, View.class);
        INSTANCE.registerInfo("View", ViewInfo.class, View.class);
        INSTANCE.registerInfo("TextView", TextViewInfo.class, TextView.class);
        INSTANCE.registerInfo("ImageView", ImageViewInfo.class, ImageView.class);
        INSTANCE.registerInfo("ViewGroup", ViewGroupInfo.class, ViewGroup.class);
        INSTANCE.registerInfo("LinearLayout", LinearLayoutInfo.class, LinearLayout.class);
        INSTANCE.registerInfo("RecyclerView", RecyclerViewInfo.class, RecyclerView.class);
    }

    private ProtocolRegister() {
    }

    static Register getInstance() {
        return INSTANCE;
    }

    /**
     * 注册 ViewProtocol
     */
    public <T extends ViewProtocol> Register registerProtocol(Class<T> protocol, Class<? extends T> protocolImpl) {
        return registerProtocol(protocol, protocolImpl, false);
    }

    /**
     * 注册 ViewProtocol
     *
     * @param defaultLayout 是否自带默认布局, 如果自带默认布局, 在 ViewProtocol 没有找到合适的布局时, 将会使用该默认布局
     */
    // TODO: 2018/12/6  public -> packaged
    public <T extends ViewProtocol> Register registerProtocol(Class<T> protocol, Class<? extends T> protocolImpl, boolean defaultLayout) {
        Map<Class<? extends ViewProtocol>, Boolean> protocolImpls = PROTOCOL_REGISTERS.get(protocol);
        if (protocolImpls == null) {
            protocolImpls = new LinkedHashMap<>();
            PROTOCOL_REGISTERS.put(protocol, protocolImpls);
        }
        protocolImpls.put(protocolImpl, defaultLayout);
        ALL_REGISTERS.put(protocolImpl.getSimpleName(), protocolImpl);
        return this;
    }

    /**
     * 解注册 ViewProtocol
     */
    public <T extends ViewProtocol> Register unregisterProtocol(Class<T> protocol, Class<? extends T> protocolImpl) {
        Map<Class<? extends ViewProtocol>, Boolean> protocolImpls = PROTOCOL_REGISTERS.get(protocol);
        if (protocolImpls != null) {
            protocolImpls.remove(protocolImpl);
        }
        ALL_REGISTERS.remove(protocolImpl.getSimpleName());
        return this;
    }

    @Override
    public <I, T extends Info<? super I>> Register registerInfo(String name, Class<T> info, Class<I> target) {
        INFO_REGISTER.put(name, new Class[]{info, target});
        return this;
    }

    /**
     * 查找是否存在自带布局的 ViewProtocol
     */
    static Class<? extends ViewProtocol> findProtocolImplWithDefaultLayout(Class<? extends ViewProtocol> protocol) {
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

    static Class[] getViewAndInfoClass(String name) {
        return INFO_REGISTER.get(name);
    }
}
