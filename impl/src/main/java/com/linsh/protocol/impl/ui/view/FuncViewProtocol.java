package com.linsh.protocol.impl.ui.view;

import com.linsh.protocol.ui.view.ViewProtocol;

import java.util.Map;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/02
 *    desc   :
 * </pre>
 */
public interface FuncViewProtocol extends ViewProtocol {
    void setFuncs(Map<String, FuncInfo> funcs);
}
