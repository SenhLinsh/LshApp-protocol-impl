package com.linsh.protocol.impl.file;

import android.content.Context;
import android.os.Build;

import com.linsh.protocol.Callback;
import com.linsh.protocol.Client;
import com.linsh.protocol.file.FileBuilder;
import com.linsh.protocol.file.FileReader;
import com.linsh.protocol.file.FileWriter;
import com.linsh.utilseverywhere.ContextUtils;
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
class FileBuilderImpl implements FileBuilder {

    static final int TYPE_PATH = 0;
    static final int TYPE_DATA = 3;
    static final int TYPE_CACHE = 4;

    private String filename;
    private final int dirType;
    private boolean external;
    private boolean force;

    public FileBuilderImpl(String path) {
        this.filename = path;
        this.dirType = 0;
    }

    FileBuilderImpl(int dirType, String filename) {
        this.filename = filename;
        this.dirType = dirType;
    }

    @Override
    public FileBuilder parent(String name) {
        if (dirType == 0) {
            int index = filename.lastIndexOf("/");
            if (index > 0) {
                filename = filename.substring(0, index) + "/" + name + filename.substring(index);
            } else {
                Client.log().print().e("无法给路径(" + filename + ")添加文件夹 " + name);
            }
        } else {
            int index = filename.lastIndexOf("/");
            if (index > 0) {
                filename = filename.substring(0, index) + "/" + name + filename.substring(index);
            } else {
                filename = name + "/" + filename;
            }
        }
        return this;
    }

    @Override
    public FileBuilder external(boolean external) {
        this.external = external;
        return this;
    }

    @Override
    public FileBuilder external(boolean external, boolean force) {
        this.external = external;
        this.force = force;
        return this;
    }

    @Override
    public File file() {
        File file = null;
        switch (dirType) {
            case TYPE_PATH:
                file = new File(filename);
                break;
            case TYPE_DATA:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    file = new File(ContextUtils.get().getDataDir(), filename);
                } else {
                    file = new File(ContextUtils.get().getDir("data", Context.MODE_PRIVATE), filename);
                }
                break;
            case TYPE_CACHE:
                if ((force && external) || (!force && UEPermission.Storage.check())) {
                    file = new File(ContextUtils.getExternalCacheDir(), filename);
                } else {
                    file = new File(ContextUtils.getCacheDir(), filename);
                }
                break;
        }
        return file;
    }

    @Override
    public FileReader read() {
        return new FileReaderImpl(file());
    }

    @Override
    public FileWriter write() {
        return new FileWriterImpl(file());
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public void deleteAll(Callback<File> callback) {

    }
}
