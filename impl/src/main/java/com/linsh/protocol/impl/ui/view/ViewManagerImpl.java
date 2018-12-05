package com.linsh.protocol.impl.ui.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.linsh.protocol.Client;
import com.linsh.protocol.ui.view.ViewManager;
import com.linsh.protocol.ui.view.ViewProtocol;
import com.linsh.utilseverywhere.ActivityLifecycleUtils;

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
    public <V extends ViewProtocol> V getProtocol(Class<V> protocol) {
        return getProtocol(protocol, null);
    }

    @Override
    public <V extends ViewProtocol> V getProtocol(Class<V> protocol, String key) {
        File dir = new File(Client.config().ui().resDir(), "protocol/" + protocol.getSimpleName());
        V v = loadProtocolFromFile(dir, protocol, key);
        if (v == null) {
            dir = new File(Client.config().ui().commonResDir(), "protocol/" + protocol.getSimpleName());
            v = loadProtocolFromFile(dir, protocol, key);
        }
        return v;
    }

    private <V extends ViewProtocol> V loadProtocolFromFile(File dir, Class<V> protocol, String key) {
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                try {
                    // TODO: 2018/12/5  ActivityLifecycleUtils.getTopActivity()
                    View inflate = JsonLayoutInflater.from(ActivityLifecycleUtils.getTopActivity()).inflate(files[0], null);
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
    public <V extends View> ViewProtocol<V> view(V view) {
        return new ViewProtocolImpl<>(view);
    }

    @Override
    public ViewProtocol inflate(int layout, Context context) {
        View view = LayoutInflater.from(context).inflate(layout, new FrameLayout(context), false);
        return new ViewProtocolImpl<>(view);
    }

    @Override
    public ViewProtocol inflate(int layout, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewProtocolImpl<>(view);
    }

    @Override
    public ViewProtocol inflate(int layout, ViewGroup parent, boolean attach) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, attach);
        return new ViewProtocolImpl<>(view);
    }
}
