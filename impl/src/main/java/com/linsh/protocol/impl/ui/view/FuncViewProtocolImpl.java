package com.linsh.protocol.impl.ui.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.linsh.protocol.Client;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/02
 *    desc   :
 * </pre>
 */
public class FuncViewProtocolImpl implements FuncViewProtocol {

    private final View view;
    public Map<String, FuncInfo> funcs;
    public HashMap<String, View> views;

    public FuncViewProtocolImpl(View view) {
        this.view = view;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setFuncs(Map<String, FuncInfo> funcs) {
        this.funcs = funcs;
    }

    protected Object excuteMethod(String name, Object... params) {
        if (funcs == null) return null;
        FuncInfo funcInfo = funcs.get(name);
        if (funcInfo == null) return null;
        if (funcInfo.id != null) {
            Context context = getView().getContext();
            if (context instanceof Activity) {
                View childView = views.get(funcInfo.id);
                if (childView == null) {
                    JsonLayoutFinder finder = Client.activity().target((Activity) context).useSubscriber(JsonLayoutFinder.class);
                    childView = finder.findViewByKey(this.view, funcInfo.id);
                    views.put(funcInfo.id, childView);
                }
                return childView;
            }
        }
        return null;
    }
}
