package com.linsh.protocol.impl.activity;

import android.app.Activity;
import android.content.Intent;

import com.linsh.protocol.activity.ActivityDelegate;
import com.linsh.protocol.activity.ActivityManager;
import com.linsh.protocol.activity.IntentDelegate;

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
    public IntentDelegate build() {
        return new IntentDelegateImpl();
    }

    @Override
    public IntentDelegate build(Class<? extends Activity> target) {
        return new IntentDelegateImpl(target);
    }

    @Override
    public IntentDelegate build(Intent intent) {
        return new IntentDelegateImpl(intent);
    }
}
