package com.linsh.protocol.impl.log.impl;

import com.linsh.protocol.Client;
import com.linsh.protocol.config.LogConfig;
import com.linsh.protocol.log.LogPrinter;
import com.linsh.utilseverywhere.DateUtils;
import com.linsh.utilseverywhere.FileUtils;

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

    private File logDir;
    private File logFile;
    private Date refreshDate = new Date();

    public LshLogPrinter(LogConfig config) {
        logDir = config.cacheDir();
        logFile = new File(logDir, DateUtils.getCurDateStr());
        // 清楚无效文件
        Schedulers.io().createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                clearExpiredLog();
            }
        });
    }

    /**
     * 打印日志到本地
     */
    @Override
    void log(final int priority, final String tag, final String msg, final Throwable thr) {
        // 调用父类, 打印打控制台
        super.log(priority, tag, msg, thr);
        // 更新文件名
        refreshFileTime();
        // 后台打印
        final File file = logFile;
        Client.thread().io(new Runnable() {
            @Override
            public void run() {
                String log = LshLogManager.getLog(PRIORITY_STR[priority], tag, msg, thr);
                FileUtils.writeString(file, log, true, true);
            }
        });
    }

    /**
     * 更新文件日期
     * <p>
     * 每天对应一个日志文件, 如果到了第二天, 则需要更新文件名
     */
    private void refreshFileTime() {
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
    private void clearExpiredLog() {
        File[] files = logDir.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                long date = DateUtils.parse(name, "yyyy-MM-dd");
                if (date > 0 && System.currentTimeMillis() - date < 7 * 24 * 60 * 60 * 1000L) {
                    continue;
                }
                boolean delete = file.delete();
                if (delete) {
                    Client.log().print().i("删除无效 log 文件: " + file.getName());
                } else {
                    Client.log().print().e("删除无效 log 文件失败: " + file.getName());
                }
            }
        } else {
            Client.log().print().e("IILogPrinter.logDir.listFiles() == null");
        }
    }
}
