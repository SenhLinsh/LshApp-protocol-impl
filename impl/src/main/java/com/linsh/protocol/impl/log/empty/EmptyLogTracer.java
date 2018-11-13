package com.linsh.protocol.impl.log.empty;

import com.linsh.protocol.log.LogTracer;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/01
 *    desc   :
 * </pre>
 */
class EmptyLogTracer implements LogTracer {
    @Override
    public void i(String msg) {

    }

    @Override
    public void i(String tag, String msg) {

    }
}
