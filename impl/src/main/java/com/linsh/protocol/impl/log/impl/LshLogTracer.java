package com.linsh.protocol.impl.log.impl;

import com.linsh.protocol.log.LogTracer;

import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/01
 *    desc   : 日志追踪器
 * </pre>
 */
class LshLogTracer implements LogTracer {

    private static final int MAX_SIZE = 100;
    private LinkedList<String> traces;

    LshLogTracer() {
        traces = new LinkedList<>();
    }

    @Override
    public void i(String msg) {
        i(null, msg);
    }

    @Override
    public void i(String tag, String msg) {
        String log = LshLogManager.getLog("I", tag, msg, null);
        traces.offer(log);
        while (traces.size() > MAX_SIZE) {
            traces.pop();
        }
    }

    List<String> getTraces() {
        return traces;
    }
}
