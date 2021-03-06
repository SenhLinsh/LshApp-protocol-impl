package com.linsh.protocol.impl;

import android.app.Application;

import com.linsh.protocol.Client;
import com.linsh.protocol.ClientIniter;
import com.linsh.protocol.Config;
import com.linsh.protocol.config.FileConfig;
import com.linsh.protocol.config.HttpConfig;
import com.linsh.protocol.config.ImageConfig;
import com.linsh.protocol.config.LogConfig;
import com.linsh.protocol.config.ThreadConfig;
import com.linsh.protocol.config.UIConfig;
import com.linsh.protocol.config.ValueConfig;
import com.linsh.protocol.impl.view.ButtonViewProtocol;
import com.linsh.protocol.impl.view.JsonIdButtonViewProtocol;
import com.linsh.protocol.impl.view.JsonIdTextItemViewProtocol;
import com.linsh.protocol.impl.view.JsonIdTitleTextItemViewProtocol;
import com.linsh.protocol.impl.view.TextItemViewProtocol;
import com.linsh.protocol.impl.view.TitleTextItemViewProtocol;
import com.linsh.protocol.value.TextSizeCreator;
import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.Utils;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/05
 *    desc   :
 * </pre>
 */
public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        ClientIniter.initClient(new LshManagerFactory(getConfig()));

        Client.ui().view().register().registerProtocol(TextItemViewProtocol.class, JsonIdTextItemViewProtocol.class);
        Client.ui().view().register().registerProtocol(TitleTextItemViewProtocol.class, JsonIdTitleTextItemViewProtocol.class, true);
        Client.ui().view().register().registerProtocol(ButtonViewProtocol.class, JsonIdButtonViewProtocol.class, true);
    }

    protected Config getConfig() {
        return new Config.Builder()
                .http(new HttpConfig.Builder()
                        .baseUrl("http://www.github.com/")
                        .build())
                .image(new ImageConfig.Builder()
                        .build())
                .thread(new ThreadConfig())
                .file(new FileConfig.Builder()
                        .appDir(new File("sdcard/linsh/common"))
                        .build())
                .log(new LogConfig.Builder()
                        .cacheDir(ContextUtils.getCacheDir())
                        .build())
                .ui(new UIConfig.Builder()
                        .resDir(new File("sdcard/linsh/common/res"))
                        .commonResDir(new File("sdcard/linsh/common/res"))
                        .build())
                .value(new ValueConfig.Builder()
                        .textSize(new TextSizeCreator() {
                            @Override
                            public int superTitle() {
                                return 25;
                            }

                            @Override
                            public int title() {
                                return 20;
                            }

                            @Override
                            public int subtitle() {
                                return 20;
                            }

                            @Override
                            public int superText() {
                                return 20;
                            }

                            @Override
                            public int text() {
                                return 17;
                            }

                            @Override
                            public int subtext() {
                                return 15;
                            }

                            @Override
                            public int littleText() {
                                return 12;
                            }
                        })
                .build())
                .build();
    }

}
