package com.linsh.protocol.impl.ui.view.entity;

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

    @Override
    protected void onDeserialize(JSONObject object) throws JSONException {
        super.onDeserialize(object);
        key = object.optString("key");
    }
}
