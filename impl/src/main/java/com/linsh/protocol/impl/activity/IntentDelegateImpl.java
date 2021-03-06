package com.linsh.protocol.impl.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.linsh.protocol.Client;
import com.linsh.protocol.activity.ActivitySubscribe;
import com.linsh.protocol.activity.IObservableActivity;
import com.linsh.protocol.activity.IntentDelegate;
import com.linsh.utilseverywhere.ContextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   :
 * </pre>
 */
class IntentDelegateImpl implements IntentDelegate {

    private static final String INTENT_EXTRA_PREFIX = "intent_extra_prefix_";
    private Context context;
    private Intent intent;
    private ArrayList<String> subscribers;

    IntentDelegateImpl() {
        intent = new Intent();
    }

    IntentDelegateImpl(Class<? extends Activity> target) {
        intent = new Intent(ContextUtils.get(), target);
    }

    IntentDelegateImpl(Intent intent) {
        this.intent = intent;
    }

    IntentDelegate context(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public IntentDelegate target(Class<? extends Activity> activity) {
        intent.setClass(ContextUtils.get(), activity);
        return this;
    }

    @Override
    public IntentDelegate target(String className) {
        intent.setClassName(ContextUtils.get(), className);
        return this;
    }

    @Override
    public IntentDelegate targetInPath(String path) {
        throw new IllegalArgumentException("暂未实现此功能");
    }

    @Override
    public IntentDelegate putExtra(String... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "String" + i, values[i]);
        }
        return this;
    }

    @Override
    public String getStringExtra() {
        return getStringExtra(0);
    }

    @Override
    public String getStringExtra(int index) {
        return intent.getStringExtra(INTENT_EXTRA_PREFIX + "String" + index);
    }

    @Override
    public IntentDelegate putExtra(int... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "int" + i, values[i]);
        }
        return this;
    }

    @Override
    public int getIntExtra() {
        return getIntExtra(0);
    }

    @Override
    public int getIntExtra(int index) {
        return intent.getIntExtra(INTENT_EXTRA_PREFIX + "int" + index, 0);
    }

    @Override
    public IntentDelegate putExtra(long... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "long" + i, values[i]);
        }
        return this;
    }

    @Override
    public long getLongExtra() {
        return getLongExtra(0);
    }

    @Override
    public long getLongExtra(int index) {
        return intent.getLongExtra(INTENT_EXTRA_PREFIX + "long" + index, 0);
    }

    @Override
    public IntentDelegate putExtra(float... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "float" + i, values[i]);
        }
        return this;
    }

    @Override
    public float getFloatExtra() {
        return getFloatExtra(0);
    }

    @Override
    public float getFloatExtra(int index) {
        return intent.getFloatExtra(INTENT_EXTRA_PREFIX + "float" + index, 0);
    }

    @Override
    public IntentDelegate putExtra(double... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "double" + i, values[i]);
        }
        return this;
    }

    @Override
    public double getDoubleExtra() {
        return getDoubleExtra(0);
    }

    @Override
    public double getDoubleExtra(int index) {
        return intent.getDoubleExtra(INTENT_EXTRA_PREFIX + "double" + index, 0);
    }

    @Override
    public IntentDelegate putExtra(boolean... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "boolean" + i, values[i]);
        }
        return this;
    }

    @Override
    public boolean getBooleanExtra() {
        return getBooleanExtra(0);
    }

    @Override
    public boolean getBooleanExtra(int index) {
        return intent.getBooleanExtra(INTENT_EXTRA_PREFIX + "boolean" + index, false);
    }

    @Override
    public IntentDelegate putExtra(Serializable... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "Serializable" + i, values[i]);
        }
        return this;
    }

    @Override
    public Serializable getSerializableExtra() {
        return getSerializableExtra(0);
    }

    @Override
    public Serializable getSerializableExtra(int index) {
        return intent.getSerializableExtra(INTENT_EXTRA_PREFIX + "Serializable" + index);
    }

    @Override
    public IntentDelegate putExtra(Parcelable... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "Parcelable" + i, values[i]);
        }
        return this;
    }

    @Override
    public Parcelable getParcelableExtra() {
        return getParcelableExtra(0);
    }

    @Override
    public Parcelable getParcelableExtra(int index) {
        return intent.getParcelableExtra(INTENT_EXTRA_PREFIX + "Parcelable" + index);
    }

    @Override
    public IntentDelegate putJsonExtra(Object... values) {
        for (Object value : values) {
            intent.putExtra(INTENT_EXTRA_PREFIX + value.getClass().getName(), new Gson().toJson(value));
        }
        return this;
    }

    @Override
    public <T> T getJsonExtra(Class<T> classOfT) {
        String json = intent.getStringExtra(INTENT_EXTRA_PREFIX + classOfT.getName());
        return new Gson().fromJson(json, classOfT);
    }

    @Override
    public IntentDelegate putExtra(String key, Object value) {
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
    public IntentDelegate setData(Uri data) {
        intent.setData(data);
        return this;
    }

    @Override
    public Uri getData() {
        return intent.getData();
    }

    @Override
    public IntentDelegate setAction(String action) {
        intent.setAction(action);
        return this;
    }

    @Override
    public IntentDelegate setType(String type) {
        intent.setType(type);
        return this;
    }

    @Override
    public IntentDelegate setDataAndType(Uri data, String type) {
        intent.setDataAndType(data, type);
        return this;
    }

    @Override
    public IntentDelegate addFlags(int flags) {
        intent.addFlags(flags);
        return this;
    }

    @Override
    public IntentDelegate addCategory(String category) {
        intent.addCategory(category);
        return this;
    }

    @Override
    public IntentDelegate newTask() {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return this;
    }

    @Override
    public IntentDelegate subscribe(Class<? extends ActivitySubscribe> subscriber) {
        if (subscribers == null) subscribers = new ArrayList<>();
        subscribers.add(subscriber.getName());
        intent.putStringArrayListExtra(INTENT_EXTRA_PREFIX + "activity_subscribes", subscribers);
        return this;
    }

    @Override
    public List<Class<? extends ActivitySubscribe>> getSubscribers() {
        ArrayList<String> subscribers = intent.getStringArrayListExtra(INTENT_EXTRA_PREFIX + "activity_subscribes");
        ArrayList<Class<? extends ActivitySubscribe>> res = new ArrayList<>();
        if (subscribers != null) {
            for (String subscriber : subscribers) {
                try {
                    res.add((Class<? extends ActivitySubscribe>) Class.forName(subscriber));
                } catch (ClassNotFoundException e) {
                    Client.log().print().e("getSubscribers 异常", e);
                } catch (Exception e) {
                    Client.log().print().e("getSubscribers 异常", e);
                }
            }
            return res;
        }
        return null;
    }

    @Override
    public Intent getIntent() {
        return intent;
    }

    @Override
    public void start() {
        if (context == null) {
            context = ContextUtils.get();
            newTask();
        } else if (!(context instanceof Activity)) {
            newTask();
        }
        context.startActivity(intent);
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
