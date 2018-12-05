package com.linsh.protocol.impl.thread;

import com.linsh.protocol.config.ThreadConfig;
import com.linsh.protocol.thread.ThreadManager;
import com.linsh.utilseverywhere.HandlerUtils;

import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/04
 *    desc   :
 * </pre>
 */
public class LshThreadManager implements ThreadManager {

    public LshThreadManager(ThreadConfig config) {
    }

    @Override
    public void ui(Runnable task) {
        HandlerUtils.postRunnable(task);
    }

    @Override
    public void single(Runnable task) {
        Schedulers.single().scheduleDirect(task);
    }

    @Override
    public void piece(Runnable task) {
        Schedulers.io().scheduleDirect(task);
    }

    @Override
    public void io(Runnable task) {
        Schedulers.io().scheduleDirect(task);
    }

    @Override
    public void newThread(Runnable task) {
        Schedulers.newThread().scheduleDirect(task);
    }
}
