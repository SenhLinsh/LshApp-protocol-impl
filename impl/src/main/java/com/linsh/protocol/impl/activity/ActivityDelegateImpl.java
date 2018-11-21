package com.linsh.protocol.impl.activity;

import android.app.Activity;

import com.linsh.protocol.activity.ActivityDelegate;
import com.linsh.protocol.activity.IObservableActivity;
import com.linsh.protocol.activity.ActivitySubscribe;

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

    public ActivityDelegateImpl(Activity activity) {
        if (activity instanceof IObservableActivity) {
            observableActivity = (IObservableActivity) activity;
            return;
        }
        throw new IllegalArgumentException("无法使用未实现 " + IObservableActivity.class.getName() + " 的 Activity");
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
}
