package com.linsh.protocol.impl;

import com.linsh.protocol.Config;
import com.linsh.protocol.ManagerFactory;
import com.linsh.protocol.activity.ActivityManager;
import com.linsh.protocol.config.DbConfig;
import com.linsh.protocol.config.FileConfig;
import com.linsh.protocol.config.HttpConfig;
import com.linsh.protocol.config.ImageConfig;
import com.linsh.protocol.config.LogConfig;
import com.linsh.protocol.config.ThreadConfig;
import com.linsh.protocol.config.UIConfig;
import com.linsh.protocol.config.ValueConfig;
import com.linsh.protocol.db.DbManager;
import com.linsh.protocol.file.FileManager;
import com.linsh.protocol.http.HttpManager;
import com.linsh.protocol.image.ImageManager;
import com.linsh.protocol.impl.activity.LshActivityManager;
import com.linsh.protocol.impl.db.RealmDbManager;
import com.linsh.protocol.impl.file.LshFileManager;
import com.linsh.protocol.impl.http.RetrofitManager;
import com.linsh.protocol.impl.image.GlideImageManager;
import com.linsh.protocol.impl.log.LogManagerFactory;
import com.linsh.protocol.impl.thread.LshThreadManager;
import com.linsh.protocol.impl.ui.LshUIManager;
import com.linsh.protocol.impl.value.LshValueManager;
import com.linsh.protocol.log.LogManager;
import com.linsh.protocol.thread.ThreadManager;
import com.linsh.protocol.ui.UIManager;
import com.linsh.protocol.value.ValueManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/28
 *    desc   :
 * </pre>
 */
public class LshManagerFactory implements ManagerFactory {

    private final Config config;
    private final ActivityManager activityManager;
    private final DbManager dbManager;
    private final FileManager fileManager;
    private final HttpManager httpManager;
    private final ImageManager imageManager;
    private final LogManager logManager;
    private final ThreadManager threadManager;
    private final UIManager uiManager;
    private final ValueManager valueManager;

    public LshManagerFactory(Config config) {
        this.config = config;
        this.activityManager = new LshActivityManager();
        this.dbManager = new RealmDbManager(config.db());
        this.fileManager = new LshFileManager(config.file());
        this.httpManager = new RetrofitManager(config.http());
        this.imageManager = new GlideImageManager(config.image());
        this.logManager = LogManagerFactory.create(config.log());
        this.threadManager = new LshThreadManager(config.thread());
        this.uiManager = new LshUIManager(config.ui());
        this.valueManager = new LshValueManager(config.value());
    }

    @Override
    public Config config() {
        return config;
    }

    @Override
    public ActivityManager activity() {
        return activityManager;
    }

    @Override
    public DbManager db() {
        return dbManager;
    }

    @Override
    public DbManager db(DbConfig config) {
        return new RealmDbManager(config);
    }

    @Override
    public FileManager file() {
        return fileManager;
    }

    @Override
    public FileManager file(FileConfig config) {
        return new LshFileManager(config);
    }

    @Override
    public HttpManager http() {
        return httpManager;
    }

    @Override
    public HttpManager http(HttpConfig config) {
        return new RetrofitManager(config);
    }

    @Override
    public ImageManager image() {
        return imageManager;
    }

    @Override
    public ImageManager image(ImageConfig config) {
        return new GlideImageManager(config);
    }

    @Override
    public LogManager log() {
        return logManager;
    }

    @Override
    public LogManager log(LogConfig config) {
        return LogManagerFactory.create(config);
    }

    @Override
    public ThreadManager thread() {
        return threadManager;
    }

    @Override
    public ThreadManager thread(ThreadConfig config) {
        return new LshThreadManager(config);
    }

    @Override
    public UIManager ui() {
        return uiManager;
    }

    @Override
    public UIManager ui(UIConfig config) {
        return new LshUIManager(config);
    }

    @Override
    public ValueManager value() {
        return valueManager;
    }

    @Override
    public ValueManager value(ValueConfig config) {
        return new LshValueManager(config);
    }
}
