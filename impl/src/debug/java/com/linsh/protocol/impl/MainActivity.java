package com.linsh.protocol.impl;

import android.os.Bundle;

import com.linsh.protocol.Client;
import com.linsh.protocol.impl.activity.ObservableActivity;
import com.linsh.protocol.impl.ui.view.JsonLayoutInflater;
import com.linsh.protocol.impl.view.TitleTextItemViewProtocol;
import com.linsh.utilseverywhere.ResourceUtils;

public class MainActivity extends ObservableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String textFromAssets = ResourceUtils.getTextFromAssets("TitleText.info");
        setContentView(JsonLayoutInflater.from(this).inflate(textFromAssets, null));
    }

    @Override
    protected void onResume() {
        super.onResume();
        TitleTextItemViewProtocol protocol = Client.ui().view().findProtocol(this, TitleTextItemViewProtocol.class);
        protocol.setTitle("I am title");
        protocol.setText("I just made it!!!\nwow~~~~");
    }
}
