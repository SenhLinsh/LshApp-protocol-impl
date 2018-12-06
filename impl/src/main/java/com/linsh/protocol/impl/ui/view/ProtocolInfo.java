package com.linsh.protocol.impl.ui.view;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/02
 *    desc   :
 * </pre>
 */
public class ProtocolInfo extends JsonInfo {
    public String name;
    public String key;
    public Class impl;

    @Override
    protected void onDeserialize(JSONObject object) throws JSONException {
        super.onDeserialize(object);
        name = object.optString("name");
        key = object.optString("key");
        String impl = object.optString("impl");
        if (impl != null) {
            if (impl.contains(".")) {
                try {
                    this.impl = Class.forName(impl);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                this.impl = ProtocolRegister.getProtocolImpl(impl);
            }
        }
    }
}
