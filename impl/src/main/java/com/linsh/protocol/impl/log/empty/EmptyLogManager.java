package com.linsh.protocol.impl.log.empty;


import com.linsh.protocol.log.LogManager;
import com.linsh.protocol.log.LogPrinter;
import com.linsh.protocol.log.LogTracer;
import com.linsh.protocol.log.Logger;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/01
 *    desc   :
 * </pre>
 */
public class EmptyLogManager implements LogManager {

    private final LogTracer logTracer;
    private final LogPrinter logPrinter;
    private final Logger logger;

    public EmptyLogManager() {
        logTracer = new EmptyLogTracer();
        logPrinter = new EmptyLogPrinter();
        logger = new EmptyLogger();
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
}
