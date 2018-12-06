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
    public String impl;

    @Override
    protected void onDeserialize(JSONObject object) throws JSONException {
        super.onDeserialize(object);
        name = object.optString("name");
        key = object.optString("key");
        impl = object.optString("impl");
    }
}
