package com.linsh.protocol.impl.log.impl;

import android.Manifest;

import com.linsh.protocol.Client;
import com.linsh.protocol.log.LogPrinter;
import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.DateUtils;
import com.linsh.utilseverywhere.FileUtils;
import com.linsh.utilseverywhere.PermissionUtils;

import java.io.File;
import java.util.Date;

import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/01
 *    desc   : 输出日志到本地
 * </pre>
 */
class LshLogPrinter extends LshLogger implements LogPrinter {

    static File logDir;
    private static File logFile;
    private static Date refreshDate = new Date();

    static {
        if (PermissionUtils.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            logDir = new File(ContextUtils.getExternalCacheDir(), "log");
        } else {
            logDir = new File(ContextUtils.getCacheDir(), "log");
        }
        logFile = new File(logDir, DateUtils.getCurDateStr());
        // 清楚无效文件
        Schedulers.io().createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                clearExpiredLog();
            }
        });
    }

    @Override
    void log(int priority, String tag, String msg, Throwable thr) {
        super.log(priority, tag, msg, thr);
        refreshFileTime();
        final String log = LshLogManager.getLog(PRIORITY_STR[priority], tag, msg, thr);
        final File file = logFile;
        Schedulers.io().createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                FileUtils.writeString(file, log, true, true);
            }
        });
    }

    /**
     * 更新文件日期
     * <p>
     * 每天对应一个日志文件, 如果到了第二天, 则需要更新文件名
     */
    private static void refreshFileTime() {
        Date date = new Date();
        if (refreshDate.getYear() != date.getYear()
                || refreshDate.getMonth() != date.getMonth()
                || refreshDate.getDate() != date.getTime()) {
            logFile = new File(logDir, DateUtils.getCurDateStr());
            refreshDate = date;
        }
    }

    /**
     * 清除过期 & 无效日志文件
     */
    private static void clearExpiredLog() {
        File[] files = logDir.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                long date = DateUtils.parse(name, "yyyy-MM-dd");
                if (date > 0 && System.currentTimeMillis() - date < 7 * 24 * 60 * 1000L) {
                    continue;
                }
                boolean delete = file.delete();
                if (delete) {
                    Client.log().print().i("删除无效 log 文件: " + file.getName());
                } else {
                    Client.log().print().e("删除无效 log 文件失败");
                }
            }
        } else {
            Client.log().print().e("LshLogPrinter.logDir.listFiles() == null");
        }
    }
}
