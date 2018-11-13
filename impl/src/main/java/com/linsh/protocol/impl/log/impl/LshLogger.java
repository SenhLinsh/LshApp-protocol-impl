package com.linsh.protocol.impl.log.impl;

import android.util.Log;

import com.linsh.protocol.log.Logger;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/01
 *    desc   :
 * </pre>
 */
class LshLogger implements Logger {

    static final String[] PRIORITY_STR = new String[]{"", "", "V", "D", "I", "W", "E"};

    @Override
    public void i(String msg) {
        log(Log.INFO, null, msg, null);
    }

    @Override
    public void i(String msg, Throwable thr) {
        log(Log.INFO, null, msg, thr);
    }

    @Override
    public void i(String tag, String msg) {
        log(Log.INFO, tag, msg, null);
    }

    @Override
    public void i(String tag, String msg, Throwable thr) {
        log(Log.INFO, tag, msg, thr);
    }

    @Override
    public void w(String msg) {
        log(Log.WARN, null, msg, null);
    }

    @Override
    public void w(Throwable thr) {
        log(Log.WARN, null, null, thr);
    }

    @Override
    public void w(String msg, Throwable thr) {
        log(Log.WARN, null, msg, thr);
    }

    @Override
    public void w(String tag, String msg) {
        log(Log.WARN, tag, msg, null);
    }

    @Override
    public void w(String tag, String msg, Throwable thr) {
        log(Log.WARN, tag, msg, thr);
    }

    @Override
    public void e(String msg) {
        log(Log.ERROR, null, msg, null);
    }

    @Override
    public void e(Throwable thr) {
        log(Log.ERROR, null, null, thr);
    }

    @Override
    public void e(String msg, Throwable thr) {
        log(Log.ERROR, null, msg, thr);
    }

    @Override
    public void e(String tag, String msg) {
        log(Log.ERROR, tag, msg, null);
    }

    @Override
    public void e(String tag, String msg, Throwable thr) {
        log(Log.ERROR, tag, msg, thr);
    }

    void log(int priority, String tag, String msg, Throwable thr) {
        tag = tag == null ? "" : tag;
        msg = msg == null ? thr == null ? "" : Log.getStackTraceString(thr) :
                thr == null ? msg : msg + "\n" + Log.getStackTraceString(thr);
        Log.println(priority, tag, msg);
    }
}
