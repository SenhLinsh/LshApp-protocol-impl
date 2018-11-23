package com.linsh.protocol.impl.value;

import android.os.Build;
import android.view.View;

import com.linsh.protocol.value.IdCreator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/23
 *    desc   :
 * </pre>
 */
class IdCreatorImpl implements IdCreator {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    @Override
    public int create() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        }
        return sNextGeneratedId.incrementAndGet();
    }

}
