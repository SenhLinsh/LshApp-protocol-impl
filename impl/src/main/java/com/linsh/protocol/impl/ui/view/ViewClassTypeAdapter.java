package com.linsh.protocol.impl.ui.view;

import android.view.View;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/06
 *    desc   :
 * </pre>
 */
public class ViewClassTypeAdapter extends TypeAdapter<Class<? extends View>> {

    @Override
    public void write(JsonWriter out, Class<? extends View> value) throws IOException {
        out.value(value.getName());
    }

    @Override
    public Class<? extends View> read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            return (Class<? extends View>) Class.forName(in.nextString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
