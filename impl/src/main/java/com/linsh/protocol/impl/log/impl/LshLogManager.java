package com.linsh.protocol.impl.log.impl;


import android.util.Log;

import com.linsh.protocol.log.LogManager;
import com.linsh.protocol.log.LogPrinter;
import com.linsh.protocol.log.LogTracer;
import com.linsh.protocol.log.Logger;
import com.linsh.utilseverywhere.DateUtils;

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

    public LshLogManager() {
        this(new LshLogTracer(), new LshLogPrinter(), new LshLogger());
    }

    public LshLogManager(LogTracer logTracer, LogPrinter logPrinter, Logger logger) {
        this.logTracer = logTracer;
        this.logPrinter = logPrinter;
        this.logger = logger;
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

    static String getLog(String priority, String tag, String msg, Throwable thr) {
        tag = tag == null ? "" : "/" + tag;
        msg = msg == null ? thr == null ? "" : Log.getStackTraceString(thr)
                : thr == null ? msg : msg + "\n" + Log.getStackTraceString(thr);
        return DateUtils.getCurDateTimeStr() + " " + priority + tag + ": " + msg;
    }

    public List<String> getTraces() {
        if (logTracer instanceof LshLogTracer) {
            return ((LshLogTracer) logTracer).getTraces();
        }
        return null;
    }
}
