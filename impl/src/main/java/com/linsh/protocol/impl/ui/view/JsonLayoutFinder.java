package com.linsh.protocol.impl.ui.view;

import android.app.Activity;
import android.view.View;

import com.linsh.protocol.Client;
import com.linsh.protocol.activity.ActivitySubscribe;
import com.linsh.protocol.impl.R;
import com.linsh.protocol.ui.view.ViewProtocol;
import com.linsh.utilseverywhere.ClassUtils;

import java.util.HashMap;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/29
 *    desc   :
 * </pre>
 */
public class JsonLayoutFinder implements ActivitySubscribe {

    private HashMap<String, Integer> keyIds;
    private HashMap<String, Integer> protocolIds;

    @Override
    public void attach(Activity activity) {
        keyIds = new HashMap<>();
        protocolIds = new HashMap<>();
    }

    public void setKey(View view, String keyId) {
        Integer id = keyIds.get(keyId);
        if (id == null) {
            id = Client.value().id().create();
            keyIds.put(keyId, id);
        }
        view.setId(id);
    }

    public void setViewProtocol(View view, ViewProtocolInfo protocolInfo) {
        int id = view.getId();
        if (id <= 0) {
            id = Client.value().id().create();
            view.setId(id);
        }
        protocolIds.put(protocolInfo.name, id);
        if (protocolInfo.key != null) {
            protocolIds.put(protocolInfo.name + "-" + protocolInfo.key, id);
        }
        ViewProtocol protocol = getProtocolInstance(view, protocolInfo);
        view.setTag(R.id.tag_view_protocol, protocol);
    }

    private ViewProtocol getProtocolInstance(View view, ViewProtocolInfo protocolInfo) {
        if (protocolInfo.name.contains(".")) {
            try {
                Class<?> protocolClass = ClassUtils.getClass(protocolInfo.name);
                if (!protocolClass.isInterface()) {
                    Object instance = ClassUtils.newInstance(protocolClass, new Class[]{View.class}, new Object[]{view});
                    if (instance instanceof ViewProtocol) {
                        if (instance instanceof FuncViewProtocol)
                            ((FuncViewProtocol) instance).setFuncs(protocolInfo.funcs);
                        return (ViewProtocol) instance;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Client.log().print().w("实例化 ViewProtocol 失败", "protocolInfo.name: " + protocolInfo.name + ", key: " + protocolInfo.key);
        }
        // TODO: 2018/12/2
        return null;
    }

    public View findViewByKey(Activity activity, String keyId) {
        Integer id = keyIds.get(keyId);
        if (id == null)
            return null;
        return activity.findViewById(id);
    }

    public View findViewByKey(View view, String keyId) {
        Integer id = keyIds.get(keyId);
        if (id == null)
            return null;
        return view.findViewById(id);
    }

    public <T extends ViewProtocol> T findProtocol(Activity activity, Class<T> protocol, String key) {
        Integer id = protocolIds.get(protocol.getSimpleName() + "-" + key);
        if (id == null) {
            id = protocolIds.get(protocol.getSimpleName());
        }
        if (id != null) {
            View target = activity.findViewById(id);
            if (target != null) {
                Object tag = target.getTag(R.id.tag_view_protocol);
                if (tag instanceof ViewProtocol) {
                    return (T) tag;
                }
            }
            Client.log().print().w("findProtocol 失败", "protocol: " + protocol.getName() + ", key: " + key);
        }
        return null;
    }

    public <T extends ViewProtocol> T findProtocol(View view, Class<T> protocol, String key) {
        Integer id = protocolIds.get(protocol.getSimpleName() + "-" + key);
        if (id == null) {
            id = protocolIds.get(protocol.getSimpleName());
        }
        if (id != null) {
            View target = view.findViewById(id);
            if (target != null) {
                Object tag = target.getTag(R.id.tag_view_protocol);
                if (tag instanceof ViewProtocol) {
                    return (T) tag;
                }
            }
            Client.log().print().w("findProtocol 失败", "protocol: " + protocol.getName() + ", key: " + key);
        }
        return null;
    }
}
