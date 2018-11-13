package com.linsh.protocol.impl.log.empty;

import com.linsh.protocol.log.Logger;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/01
 *    desc   :
 * </pre>
 */
class EmptyLogger implements Logger {
    @Override
    public void i(String msg) {

    }

    @Override
    public void i(String msg, Throwable thr) {

    }

    @Override
    public void i(String tag, String msg) {

    }

    @Override
    public void i(String tag, String msg, Throwable thr) {

    }

    @Override
    public void w(String msg) {

    }

    @Override
    public void w(Throwable thr) {

    }

    @Override
    public void w(String msg, Throwable thr) {

    }

    @Override
    public void w(String tag, String msg) {

    }

    @Override
    public void w(String tag, String msg, Throwable thr) {

    }

    @Override
    public void e(String msg) {

    }

    @Override
    public void e(Throwable thr) {

    }

    @Override
    public void e(String msg, Throwable thr) {

    }

    @Override
    public void e(String tag, String msg) {

    }

    @Override
    public void e(String tag, String msg, Throwable thr) {

    }
}
