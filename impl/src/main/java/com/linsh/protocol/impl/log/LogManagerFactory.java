package com.linsh.protocol.impl.log;


import com.linsh.protocol.impl.log.impl.LshLogManager;
import com.linsh.protocol.log.LogManager;

import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/01
 *    desc   :
 * </pre>
 */
public class LogManagerFactory {

    private static final LogManager LOG_MANAGER = new LshLogManager();

    public static LogManager create() {
        return LOG_MANAGER;
    }

    public static List<String> getTraces() {
        if (LOG_MANAGER instanceof LshLogManager) {
            return ((LshLogManager) LOG_MANAGER).getTraces();
        }
        return null;
    }
}
