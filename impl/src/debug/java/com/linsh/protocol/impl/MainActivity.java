package com.linsh.protocol.impl;

import android.os.Bundle;
import android.view.View;

import com.linsh.protocol.impl.activity.ObservableActivity;
import com.linsh.protocol.impl.ui.view.JsonLayoutInflater;
import com.linsh.utilseverywhere.ResourceUtils;

public class MainActivity extends ObservableActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = JsonLayoutInflater.from(this).inflate(ResourceUtils.getTextFromAssets("PageMain.info"), null);
        setContentView(view);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
