package com.linsh.protocol.impl.activity;

import android.app.Activity;
import android.content.Intent;

import com.linsh.protocol.activity.ActivityBuilder;
import com.linsh.protocol.activity.ActivityDelegate;
import com.linsh.protocol.activity.ActivityManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   :
 * </pre>
 */
public class LshActivityManager implements ActivityManager {
    @Override
    public ActivityDelegate target(Activity activity) {
        return new ActivityDelegateImpl(activity);
    }

    @Override
    public ActivityBuilder build() {
        return new ActivityBuilderImpl();
    }

    @Override
    public ActivityBuilder build(Class<? extends Activity> target) {
        return new ActivityBuilderImpl(target);
    }

    @Override
    public ActivityBuilder build(Intent intent) {
        return new ActivityBuilderImpl(intent);
    }
}
