package com.linsh.protocol.impl.file;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.linsh.protocol.Client;
import com.linsh.protocol.Consumer;
import com.linsh.protocol.Task;
import com.linsh.protocol.file.FileReader;
import com.linsh.protocol.impl.TaskImpl;
import com.linsh.utilseverywhere.BitmapUtils;
import com.linsh.utilseverywhere.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/16
 *    desc   :
 * </pre>
 */
class FileReaderImpl implements FileReader {

    private final File file;

    FileReaderImpl(File file) {
        this.file = file;
    }

    @Override
    public void reader(final Consumer<BufferedReader> callable) {
        Client.thread().io(new Runnable() {
            @Override
            public void run() {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                } catch (Exception e) {
                    Client.log().print().w("获取 reader 失败", e);
                }
                try {
                    callable.accept(reader);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public BufferedReader reader() {
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        } catch (Exception e) {
            Client.log().print().w("获取 reader 失败", e);
        }
        return null;
    }

    @Override
    public Task<String> read() {
        return new TaskImpl<>(new Callable<String>() {
            @Override
            public String call() {
                return FileUtils.readAsString(file);
            }
        });
    }

    @Override
    public Task<List<String>> readLines() {
        return new TaskImpl<>(new Callable<List<String>>() {
            @Override
            public List<String> call() {
                return FileUtils.readLines(file);
            }
        });
    }

    @Override
    public <T> Task<T> readJson(final Class<T> classOfT) {
        return new TaskImpl<>(new Callable<T>() {
            @Override
            public T call() {
                String json = FileUtils.readAsString(file);
                return new Gson().fromJson(json, classOfT);
            }
        });
    }

    @Override
    public Task<Bitmap> readBitmap() {
        return new TaskImpl<>(new Callable<Bitmap>() {
            @Override
            public Bitmap call() {
                return BitmapUtils.getBitmap(file);
            }
        });
    }

    @Override
    public Task<byte[]> readBytes() {
        return new TaskImpl<>(new Callable<byte[]>() {
            @Override
            public byte[] call() {
                throw new IllegalArgumentException("该方法暂未开发");
            }
        });
    }
}
