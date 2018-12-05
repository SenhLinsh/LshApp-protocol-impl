package com.linsh.protocol.impl.activity;

import android.app.Activity;

import com.linsh.protocol.Client;
import com.linsh.protocol.activity.ActivityDelegate;
import com.linsh.protocol.activity.ActivitySubscribe;
import com.linsh.protocol.activity.IObservableActivity;
import com.linsh.protocol.activity.IntentDelegate;
import com.linsh.protocol.ui.view.ViewProtocol;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   :
 * </pre>
 */
class ActivityDelegateImpl implements ActivityDelegate {

    private final IObservableActivity observableActivity;

    ActivityDelegateImpl(Activity activity) {
        if (!(activity instanceof IObservableActivity))
            throw new IllegalArgumentException("无法使用未实现 " + IObservableActivity.class.getName() + " 的 Activity");
        observableActivity = (IObservableActivity) activity;
    }

    @Override
    public ActivityDelegate subscribe(Class<? extends ActivitySubscribe> subscriber) {
        observableActivity.subscribe(subscriber);
        return this;
    }

    @Override
    public ActivityDelegate subscribe(ActivitySubscribe subscriber) {
        observableActivity.subscribe(subscriber);
        return this;
    }

    @Override
    public ActivityDelegate unsubscribe(Class<? extends ActivitySubscribe> subscriber) {
        observableActivity.unsubscribe(subscriber);
        return this;
    }

    @Override
    public ActivityDelegate unsubscribe(ActivitySubscribe subscriber) {
        observableActivity.unsubscribe(subscriber);
        return this;
    }

    @Override
    public <T extends ActivitySubscribe> T getSubscriber(Class<T> subscriber) {
        return observableActivity.subscribe(subscriber);
    }

    @Override
    public <T extends ActivitySubscribe> T useSubscriber(Class<T> subscriber) {
        return observableActivity.subscribe(subscriber);
    }

    @Override
    public <T extends ActivitySubscribe> T useSubscriber(T subscriber) {
        return observableActivity.subscribe(subscriber);
    }

    @Override
    public IntentDelegate intent() {
        return new IntentDelegateImpl();
    }

    @Override
    public <V extends ViewProtocol> V findViewProtocol(Class<V> protocol) {
        return Client.ui().view().findProtocol((Activity) observableActivity, protocol);
    }

    @Override
    public <V extends ViewProtocol> V findViewProtocol(Class<V> protocol, String key) {
        return Client.ui().view().findProtocol((Activity) observableActivity, protocol, key);
    }
}
