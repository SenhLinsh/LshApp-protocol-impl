package com.linsh.protocol.impl.db;

import android.support.annotation.NonNull;

import com.linsh.protocol.impl.TaskImpl;

import java.util.concurrent.Callable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/22
 *    desc   :
 * </pre>
 */
public class RealmTask<T> extends TaskImpl<T> {

    public RealmTask(@NonNull Callable<T> callable) {
        super(callable, Schedulers.single(), AndroidSchedulers.mainThread());
    }
}
