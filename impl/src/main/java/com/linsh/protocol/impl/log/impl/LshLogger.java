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
        log(Log.INFO, getClassName(), msg, null);
    }

    @Override
    public void i(String msg, Throwable thr) {
        log(Log.INFO, getClassName(), msg, thr);
    }

    @Override
    public void i(String tag, String msg) {
        log(Log.INFO, getClassName() + ": " + tag, msg, null);
    }

    @Override
    public void i(String tag, String msg, Throwable thr) {
        log(Log.INFO, getClassName() + ": " + tag, msg, thr);
    }

    @Override
    public void w(String msg) {
        log(Log.WARN, getClassName(), msg, null);
    }

    @Override
    public void w(Throwable thr) {
        log(Log.WARN, getClassName(), null, thr);
    }

    @Override
    public void w(String msg, Throwable thr) {
        log(Log.WARN, getClassName(), msg, thr);
    }

    @Override
    public void w(String tag, String msg) {
        log(Log.WARN, getClassName() + ": " + tag, msg, null);
    }

    @Override
    public void w(String tag, String msg, Throwable thr) {
        log(Log.WARN, getClassName() + ": " + tag, msg, thr);
    }

    @Override
    public void e(String msg) {
        log(Log.ERROR, getClassName(), msg, null);
    }

    @Override
    public void e(Throwable thr) {
        log(Log.ERROR, getClassName(), null, thr);
    }

    @Override
    public void e(String msg, Throwable thr) {
        log(Log.ERROR, getClassName(), msg, thr);
    }

    @Override
    public void e(String tag, String msg) {
        log(Log.ERROR, getClassName() + ": " + tag, msg, null);
    }

    @Override
    public void e(String tag, String msg, Throwable thr) {
        log(Log.ERROR, getClassName() + ": " + tag, msg, thr);
    }

    /**
     * 打印到控制台
     */
    void log(int priority, String tag, String msg, Throwable thr) {
        tag = "IILogger: " + tag;
        msg = msg == null ? thr == null ? "" : Log.getStackTraceString(thr) :
                thr == null ? msg : msg + "\n" + Log.getStackTraceString(thr);
        Log.println(priority, tag, msg);
    }

    /**
     * 获取调用 Log 方法的类名
     */
    private static String getClassName() {
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        String result = thisMethodStack.getClassName();
        String[] split = result.split("\\.");
        try {
            result = split[split.length - 1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
