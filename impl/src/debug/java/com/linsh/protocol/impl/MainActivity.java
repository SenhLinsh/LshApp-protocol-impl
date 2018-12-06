package com.linsh.protocol.impl;

import android.os.Bundle;

import com.linsh.protocol.Client;
import com.linsh.protocol.impl.activity.ObservableActivity;
import com.linsh.protocol.impl.ui.view.JsonLayoutInflater;
import com.linsh.protocol.impl.view.TitleTextItemViewProtocol;
import com.linsh.protocol.ui.layout.DataSetViewProtocol;
import com.linsh.protocol.ui.layout.ListViewProtocol;
import com.linsh.protocol.ui.layout.OnBindItemViewListener;
import com.linsh.utilseverywhere.ResourceUtils;

public class MainActivity extends ObservableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String textFromAssets = ResourceUtils.getTextFromAssets("PageMain.info");
        setContentView(JsonLayoutInflater.from(this).inflate(textFromAssets, null));

        ListViewProtocol<String> listView = Client.ui().view().findProtocol(this, ListViewProtocol.class);
        listView.addItemView(TitleTextItemViewProtocol.class, new OnBindItemViewListener<String, TitleTextItemViewProtocol>() {
            @Override
            public void onBindItemView(DataSetViewProtocol<String> dataSet, TitleTextItemViewProtocol itemView, int position) {
                itemView.setTitle("Hello World");
                itemView.setText(dataSet.getItemData(position));
            }
        });
        listView.setData(new String[]{"Item1", "Item2", "Item3", "Item4"});
        listView.getView().getAdapter().notifyDataSetChanged();
    }
}
