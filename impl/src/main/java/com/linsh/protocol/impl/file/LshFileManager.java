package com.linsh.protocol.impl.file;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;

import com.linsh.protocol.Client;
import com.linsh.protocol.Config;
import com.linsh.protocol.activity.ActivitySubscribe;
import com.linsh.protocol.file.FileBuilder;
import com.linsh.protocol.file.FileManager;
import com.linsh.protocol.file.PermissionCallback;
import com.linsh.utilseverywhere.SDCardUtils;
import com.linsh.utilseverywhere.UEPermission;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/16
 *    desc   :
 * </pre>
 */
public class LshFileManager implements FileManager {

    private final String appDir;

    public LshFileManager(Config config) {
        String appDir = config.getString(Config.FILE_STRING_APP_DIR);
        if (appDir == null)
            throw new IllegalArgumentException("");
        this.appDir = appDir.endsWith("/") ? appDir : appDir + "/";
    }

    @Override
    public FileBuilder path(String path) {
        return new FileBuilderImpl(FileBuilderImpl.TYPE_PATH, path);
    }

    @Override
    public FileBuilder sd(String filename) {
        File root = SDCardUtils.getRootDirectory();
        return new FileBuilderImpl(FileBuilderImpl.TYPE_PATH,
                (root != null ? root.getAbsolutePath() : "sdcard/") + filename);
    }

    @Override
    public FileBuilder app(String filename) {
        return new FileBuilderImpl(FileBuilderImpl.TYPE_PATH,
                appDir + filename);
    }

    @Override
    public FileBuilder data(String filename) {
        return new FileBuilderImpl(FileBuilderImpl.TYPE_DATA, filename);
    }

    @Override
    public FileBuilder cache(String filename) {
        return new FileBuilderImpl(FileBuilderImpl.TYPE_CACHE, filename);
    }

    @Override
    public boolean checkPermission() {
        return UEPermission.Storage.check();
    }

    @Override
    public void checkOrRequestPermission(Activity activity) {
        if (!checkPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                UEPermission.Storage.request(activity, Client.value().id().create());
            }
        }
    }

    @Override
    public void checkOrRequestPermission(final Activity activity, final PermissionCallback callback) {
        if (!checkPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                final int id = Client.value().id().create();
                Client.activity().target(activity).subscribe(new ActivitySubscribe.OnRequestPermissionsResult() {
                    @Override
                    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                        if (id == requestCode) {
                            UEPermission.Storage.checkResult(permissions, grantResults);
                            Client.activity().target(activity).unsubscribe(this);
                        }
                    }

                    @Override
                    public void attach(Activity activity) {

                    }
                });
                UEPermission.Storage.request(activity, id);
            }
        }
    }

    @Override
    public void requestPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            UEPermission.Storage.request(activity, Client.value().id().create());
        }
    }
}
