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
class JsonLayoutFinder implements ActivitySubscribe {

    private HashMap<String, Integer> keyIds = new HashMap<>();
    private HashMap<String, Integer> protocolIds = new HashMap<>();

    @Override
    public void attach(Activity activity) {
    }

    void setKeyId(View view, String keyId) {
        Integer id = keyIds.get(keyId);
        if (id == null) {
            id = view.getId();
            if (id == View.NO_ID) {
                id = Client.value().id().create();
            }
            keyIds.put(keyId, id);
        }
        view.setId(id);
    }

    void setViewProtocol(View view, ProtocolInfo protocolInfo) {
        int id = view.getId();
        if (id == View.NO_ID) {
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

    private ViewProtocol getProtocolInstance(View view, ProtocolInfo protocolInfo) {
        Class<? extends ViewProtocol> impl = null;
        // 带 . 默认直接反射实例化
        if (protocolInfo.impl != null) {
            if (protocolInfo.impl.contains(".")) {
                try {
                    impl = (Class<? extends ViewProtocol>) Class.forName(protocolInfo.impl);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                impl = ProtocolRegister.getProtocolImpl(protocolInfo.impl);
            }
        }
        if (impl != null) {
            try {
                Object instance = ClassUtils.newInstance(impl, new Class[]{View.class}, new Object[]{view});
                if (instance instanceof ViewProtocol) {
                    return (ViewProtocol) instance;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Client.log().print().w("实例化 ViewProtocol 失败", "protocolInfo.name: " + protocolInfo.name + ", key: " + protocolInfo.key);
        }
        throw new IllegalArgumentException("没有找到合适的实现类来生成实例");
    }

    View findViewByKeyId(Activity activity, String keyId) {
        Integer id = keyIds.get(keyId);
        if (id == null)
            return null;
        return activity.findViewById(id);
    }

    View findViewByKeyId(View view, String keyId) {
        Integer id = keyIds.get(keyId);
        if (id == null)
            return null;
        return view.findViewById(id);
    }

    <T extends ViewProtocol> T findProtocol(Activity activity, Class<T> protocol, String key) {
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

    <T extends ViewProtocol> T findProtocol(View view, Class<T> protocol, String key) {
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
