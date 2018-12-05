package com.linsh.protocol.impl.ui.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.linsh.protocol.Client;
import com.linsh.protocol.ui.view.ViewManager;
import com.linsh.protocol.ui.view.ViewProtocol;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/26
 *    desc   :
 * </pre>
 */
public class ViewManagerImpl implements ViewManager {

    @Override
    public <V extends ViewProtocol> V findProtocol(Activity activity, Class<V> protocol) {
        return findProtocol(activity, protocol, null);
    }

    @Override
    public <V extends ViewProtocol> V findProtocol(Activity activity, Class<V> protocol, String key) {
        JsonLayoutFinder jsonLayoutFinder = Client.activity().target(activity).useSubscriber(JsonLayoutFinder.class);
        return jsonLayoutFinder.findProtocol(activity, protocol, key);
    }

    @Override
    public <V extends ViewProtocol> V findProtocol(View view, Class<V> protocol) {
        return findProtocol(view, protocol, null);
    }

    @Override
    public <V extends ViewProtocol> V findProtocol(View view, Class<V> protocol, String key) {
        Context context = view.getContext();
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            JsonLayoutFinder jsonLayoutFinder = Client.activity().target(activity).useSubscriber(JsonLayoutFinder.class);
            return jsonLayoutFinder.findProtocol(activity, protocol, key);
        }
        throw new IllegalArgumentException("暂未开发 Activity 以外的界面");
    }

    @Override
    public <V extends ViewProtocol> V getProtocol(Context context, Class<V> protocol) {
        return getProtocol(context, null, protocol, null, false);
    }

    @Override
    public <V extends ViewProtocol> V getProtocol(Context context, Class<V> protocol, String key) {
        return getProtocol(context, null, protocol, key, false);
    }

    @Override
    public <V extends ViewProtocol> V getProtocol(ViewGroup parent, Class<V> protocol, boolean attachToRoot) {
        return getProtocol(parent.getContext(), parent, protocol, null, attachToRoot);
    }

    @Override
    public <V extends ViewProtocol> V getProtocol(ViewGroup parent, Class<V> protocol, String key, boolean attachToRoot) {
        return getProtocol(parent.getContext(), parent, protocol, key, attachToRoot);
    }

    private <V extends ViewProtocol> V getProtocol(Context context, ViewGroup parent, Class<V> protocol, String key, boolean attachToRoot) {
        File dir = new File(Client.config().ui().resDir(), "protocol/" + protocol.getSimpleName());
        V v = loadProtocolFromFile(context, dir, protocol, key, parent);
        if (v == null) {
            dir = new File(Client.config().ui().commonResDir(), "protocol/" + protocol.getSimpleName());
            v = loadProtocolFromFile(context, dir, protocol, key, parent);
        }
        return v;
    }

    private <V extends ViewProtocol> V loadProtocolFromFile(Context context, File dir, Class<V> protocol, String key, ViewGroup parent) {
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                try {
                    View inflate = JsonLayoutInflater.from(context).inflate(files[0], parent);
                    return findProtocol(inflate, protocol);
                } catch (Exception e) {
                    Client.log().print().e("ViewProtocol 加载失败", "protocol: " + protocol + ", key: " + key + ", file: " + file.getPath());
                }
            }
        }
        return null;
    }

    @Override
    public <T extends ViewProtocol> ViewManager registerProtocol(Class<T> protocol, Class<? extends T> protocolImpl) {
        ProtocolRegister.registerProtocol(protocol, protocolImpl);
        return this;
    }

    @Override
    public <T extends ViewProtocol> ViewManager unregisterProtocol(Class<T> protocol, Class<? extends T> protocolImpl) {
        ProtocolRegister.unregisterProtocol(protocol, protocolImpl);
        return this;
    }

    @Override
    public ViewProtocol inflate(String layoutName, Context context) {
        return inflate(layoutName, null, false, context);
    }

    @Override
    public ViewProtocol inflate(String layoutName, ViewGroup parent) {
        return inflate(layoutName, parent, true, parent.getContext());
    }

    @Override
    public ViewProtocol inflate(String layoutName, ViewGroup parent, boolean attach) {
        return inflate(layoutName, parent, attach, parent.getContext());
    }

    private ViewProtocol inflate(String layoutName, ViewGroup parent, boolean attach, Context context) {
        File file = new File(Client.config().ui().resDir(), "layout/" + layoutName);
        View inflate = JsonLayoutInflater.from(context).inflate(file, parent, attach);
        return new ViewProtocolImpl<>(inflate);
    }

    @Override
    public <V extends View> ViewProtocol<V> view(V view) {
        return new ViewProtocolImpl<>(view);
    }
}
