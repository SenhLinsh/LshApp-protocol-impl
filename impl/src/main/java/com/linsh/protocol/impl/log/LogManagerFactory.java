package com.linsh.protocol.impl.log;


import com.linsh.protocol.config.LogConfig;
import com.linsh.protocol.impl.log.empty.EmptyLogManager;
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

    public static LogManager create(LogConfig config) {
        if (config.open()) {
            return new LshLogManager(config);
        }
        return new EmptyLogManager();
    }

    public static List<String> getTraces(LogManager manager) {
        if (manager instanceof LshLogManager) {
            return ((LshLogManager) manager).getTraces();
        }
        return null;
    }
}
