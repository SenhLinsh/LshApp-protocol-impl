package com.linsh.protocol.impl.value;

import com.linsh.protocol.config.ValueConfig;
import com.linsh.protocol.value.ColorCreator;
import com.linsh.protocol.value.IdCreator;
import com.linsh.protocol.value.TextSizeCreator;
import com.linsh.protocol.value.ValueManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/23
 *    desc   :
 * </pre>
 */
public class LshValueManagerImpl implements ValueManager {

    private final ValueConfig config;

    public LshValueManagerImpl(ValueConfig config) {
        this.config = config;
    }

    @Override
    public ColorCreator color() {
        return config.color();
    }

    @Override
    public TextSizeCreator textSize() {
        return config.textSize();
    }

    @Override
    public IdCreator id() {
        return new IdCreatorImpl();
    }
}
