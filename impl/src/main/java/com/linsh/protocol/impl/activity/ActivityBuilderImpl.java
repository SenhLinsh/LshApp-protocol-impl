package com.linsh.protocol.impl.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.linsh.protocol.activity.ActivityBuilder;
import com.linsh.protocol.activity.ActivitySubscribe;
import com.linsh.protocol.activity.IObservableActivity;
import com.linsh.utilseverywhere.ContextUtils;

import java.io.Serializable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   :
 * </pre>
 */
class ActivityBuilderImpl implements ActivityBuilder {

    static final String INTENT_EXTRA_PREFIX = "intent_extra_prefix_";
    private Intent intent;

    public ActivityBuilderImpl() {
        intent = new Intent();
    }

    public ActivityBuilderImpl(Class<? extends Activity> target) {
        intent = new Intent(ContextUtils.get(), target);
    }

    public ActivityBuilderImpl(Intent intent) {
        this.intent = intent;
    }

    @Override
    public ActivityBuilder target(Class<? extends Activity> activity) {
        intent.setClass(ContextUtils.get(), activity);
        return this;
    }

    @Override
    public ActivityBuilder target(String className) {
        intent.setClassName(ContextUtils.get(), className);
        return this;
    }

    @Override
    public ActivityBuilder targetInPath(String path) {
        throw new IllegalArgumentException("暂未实现此功能");
    }

    @Override
    public ActivityBuilder putExtra(String... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "String" + i, values[i]);
        }
        return this;
    }

    @Override
    public ActivityBuilder putExtra(int... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "int" + i, values[i]);
        }
        return this;
    }

    @Override
    public ActivityBuilder putExtra(long... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "long" + i, values[i]);
        }
        return this;
    }

    @Override
    public ActivityBuilder putExtra(float... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "float" + i, values[i]);
        }
        return this;
    }

    @Override
    public ActivityBuilder putExtra(double... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "double" + i, values[i]);
        }
        return this;
    }

    @Override
    public ActivityBuilder putExtra(boolean... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "boolean" + i, values[i]);
        }
        return this;
    }

    @Override
    public ActivityBuilder putExtra(Serializable... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "Serializable" + i, values[i]);
        }
        return this;
    }

    @Override
    public ActivityBuilder putExtra(Parcelable... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "Parcelable" + i, values[i]);
        }
        return this;
    }

    @Override
    public ActivityBuilder putJsonExtra(Object... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "Json" + i, new Gson().toJson(values[i]));
        }
        return this;
    }

    @Override
    public ActivityBuilder putExtra(String key, Object value) {
        if (value instanceof String)
            intent.putExtra(key, (String) value);
        else if (value instanceof Integer)
            intent.putExtra(key, (int) value);
        else if (value instanceof Long)
            intent.putExtra(key, (long) value);
        else if (value instanceof Float)
            intent.putExtra(key, (float) value);
        else if (value instanceof Double)
            intent.putExtra(key, (double) value);
        else if (value instanceof Parcelable)
            intent.putExtra(key, (Parcelable) value);
        else if (value instanceof Serializable)
            intent.putExtra(key, (Serializable) value);
        else
            throw new IllegalArgumentException("不支持该类型的 Extra : " + value.getClass());
        return this;
    }

    @Override
    public ActivityBuilder setData(Uri data) {
        intent.setData(data);
        return this;
    }

    @Override
    public ActivityBuilder setAction(String action) {
        intent.setAction(action);
        return this;
    }

    @Override
    public ActivityBuilder setType(String type) {
        intent.setType(type);
        return this;
    }

    @Override
    public ActivityBuilder setDataAndType(Uri data, String type) {
        intent.setDataAndType(data, type);
        return this;
    }

    @Override
    public ActivityBuilder addFlags(int flags) {
        intent.addFlags(flags);
        return this;
    }

    @Override
    public ActivityBuilder addCategory(String category) {
        intent.addCategory(category);
        return this;
    }

    @Override
    public ActivityBuilder newTask() {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return this;
    }

    @Override
    public ActivityBuilder subscribe(Class<? extends ActivitySubscribe> subscriber) {
        intent.putExtra(INTENT_EXTRA_PREFIX + subscriber.getName(), subscriber.getName());
        return this;
    }

    @Override
    public Intent getIntent() {
        return intent;
    }

    @Override
    public void start() {
        newTask();
        ContextUtils.get().startActivity(intent);
    }

    @Override
    public void start(Activity activity) {
        activity.startActivity(intent);
    }

    @Override
    public void startForResult(Activity activity, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startForResult(Activity activity, int requestCode, ActivitySubscribe.OnActivityResult subscriber) {
        if (!(activity instanceof IObservableActivity))
            throw new IllegalArgumentException("该 Activity 未实现 " + IObservableActivity.class + " 接口");
        activity.startActivityForResult(intent, requestCode);
        ((IObservableActivity) activity).subscribe(subscriber);
    }
}
