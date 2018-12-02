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
    public <V extends ViewProtocol> V protocol(Activity activity, Class<V> protocol) {
        return protocol(activity, protocol, null);
    }

    @Override
    public <V extends ViewProtocol> V protocol(Activity activity, Class<V> protocol, String key) {
        JsonLayoutFinder jsonLayoutFinder = Client.activity().target(activity).useSubscriber(JsonLayoutFinder.class);
        return jsonLayoutFinder.findProtocol(activity, protocol, key);
    }

    @Override
    public <V extends ViewProtocol> V protocol(View view, Class<V> protocol) {
        return protocol(view, protocol, null);
    }

    @Override
    public <V extends ViewProtocol> V protocol(View view, Class<V> protocol, String key) {
        Context context = view.getContext();
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            JsonLayoutFinder jsonLayoutFinder = Client.activity().target(activity).useSubscriber(JsonLayoutFinder.class);
            return jsonLayoutFinder.findProtocol(activity, protocol, key);
        }
        throw new IllegalArgumentException("暂未开发 Activity 以外的界面");
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
