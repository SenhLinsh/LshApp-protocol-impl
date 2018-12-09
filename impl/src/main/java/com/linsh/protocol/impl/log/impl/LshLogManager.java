package com.linsh.protocol.impl.log.impl;


import android.util.Log;

import com.linsh.protocol.config.LogConfig;
import com.linsh.protocol.log.LogManager;
import com.linsh.protocol.log.LogPrinter;
import com.linsh.protocol.log.LogTracer;
import com.linsh.protocol.log.Logger;
import com.linsh.utilseverywhere.DateUtils;

import java.io.File;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/01
 *    desc   :
 * </pre>
 */
public class LshLogManager implements LogManager {

    private final LogTracer logTracer;
    private final LogPrinter logPrinter;
    private final Logger logger;
    private final LogConfig config;

    public LshLogManager(LogConfig config) {
        this.config = config;
        this.logTracer = new LshLogTracer();
        this.logPrinter = new LshLogPrinter(config);
        this.logger = new LshLogger();
    }

    @Override
    public LogTracer trace() {
        return logTracer;
    }

    @Override
    public LogPrinter print() {
        return logPrinter;
    }

    @Override
    public Logger log() {
        return logger;
    }

    /**
     * 获取需要打印的字符串, 格式: yyyy-MM-dd HH:mm:ss [IWE]/${tag}: ${msg}
     * <p>
     * 注: 如果打印 Throwable, 默认控制打印字符串长度子在 500 之内
     */
    static String getLog(String priority, String tag, String msg, Throwable thr) {
        tag = tag == null ? "" : "/" + tag;
        msg = msg == null ? thr == null ? "" : getStackTraceString(thr)
                : thr == null ? msg : msg + "\n" + getStackTraceString(thr);
        return DateUtils.getCurDateTimeStr() + " " + priority + tag + ": " + msg;
    }

    private static String getStackTraceString(Throwable thr) {
        String str = Log.getStackTraceString(thr);
        if (str.length() > 500) {
            str = str.substring(0, 500) + "...";
        }
        return str;
    }

    public List<String> getTraces() {
        if (logTracer instanceof LshLogTracer) {
            return ((LshLogTracer) logTracer).getTraces();
        }
        return null;
    }

    /**
     * 清除过期 & 无效日志文件
     */
    public void clearExpiredLog() {
        File[] files = config.cacheDir().listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                long date = DateUtils.parse(name, "yyyy-MM-dd");
                if (date > 0 && System.currentTimeMillis() - date < 7 * 24 * 60 * 60 * 1000L) {
                    continue;
                }
                boolean delete = file.delete();
                if (delete) {
                    print().i("删除无效 log 文件: " + file.getName());
                } else {
                    print().e("删除无效 log 文件失败: " + file.getName());
                }
            }
        } else {
            print().e("LogPrinter.logDir.listFiles() == null");
        }
    }
}
