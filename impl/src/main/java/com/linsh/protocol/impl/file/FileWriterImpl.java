package com.linsh.protocol.impl.file;

import android.graphics.Bitmap;

import com.linsh.protocol.Client;
import com.linsh.protocol.Consumer;
import com.linsh.protocol.file.FileWriter;
import com.linsh.utilseverywhere.BitmapUtils;
import com.linsh.utilseverywhere.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/16
 *    desc   :
 * </pre>
 */
class FileWriterImpl implements FileWriter {

    private final File file;

    FileWriterImpl(File file) {
        this.file = file;
    }

    @Override
    public BufferedWriter writer() {
        try {
            return new BufferedWriter(new java.io.FileWriter(file, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void writer(final Consumer<BufferedWriter> callable) {
        Client.thread().io(new Runnable() {
            @Override
            public void run() {
                BufferedWriter reader = null;
                try {
                    reader = new BufferedWriter(new java.io.FileWriter(file, true));
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
    public FileWriter write(final String content) {
        return write(content, false);
    }

    @Override
    public FileWriter write(final String content, final boolean append) {
        Client.thread().io(new Runnable() {
            @Override
            public void run() {
                FileUtils.writeString(file, content, append);
            }
        });
        return this;
    }

    @Override
    public FileWriter write(List<String> content) {
        return write(content, false);
    }

    @Override
    public FileWriter write(final List<String> content, final boolean append) {
        Client.thread().io(new Runnable() {
            @Override
            public void run() {
                FileUtils.writeLines(file, content, append);
            }
        });
        return this;
    }

    @Override
    public FileWriter writeLine() {
        return writeLines(1);
    }

    @Override
    public FileWriter writeLines(final int lines) {
        Client.thread().io(new Runnable() {
            @Override
            public void run() {
                String[] arr = new String[lines];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = "";
                }
                FileUtils.writeLines(file, Arrays.asList(arr), true);
            }
        });
        return this;
    }

    @Override
    public FileWriter write(final Bitmap bitmap) {
        Client.thread().io(new Runnable() {
            @Override
            public void run() {
                BitmapUtils.saveBitmap(bitmap, file);
            }
        });
        return this;
    }

    @Override
    public FileWriter write(final byte[] bytes) {
        Client.thread().io(new Runnable() {
            @Override
            public void run() {
                FileUtils.writeBytes(file, bytes);
            }
        });
        return this;
    }

    @Override
    public FileWriter write(final InputStream stream) {
        Client.thread().io(new Runnable() {
            @Override
            public void run() {
                FileUtils.writeStream(file, stream);
            }
        });
        return this;
    }

    @Override
    public FileTask task() {
        return new FileTaskImpl(file);
    }
}
