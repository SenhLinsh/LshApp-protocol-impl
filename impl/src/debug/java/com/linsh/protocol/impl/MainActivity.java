package com.linsh.protocol.impl;

import android.os.Bundle;
import android.view.View;

import com.linsh.protocol.Client;
import com.linsh.protocol.impl.activity.ObservableActivity;
import com.linsh.protocol.impl.view.ButtonViewProtocol;

public class MainActivity extends ObservableActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButtonViewProtocol protocol = Client.ui().view().getProtocol(this, ButtonViewProtocol.class);
        setContentView(protocol.getView());
        protocol.setText("Click");
        protocol.setOnclick(this);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
