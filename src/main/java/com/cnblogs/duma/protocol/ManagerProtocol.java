package com.cnblogs.duma.protocol;

public interface ManagerProtocol {

    /**
     * 设置支持的最大表的个数
     * @param tableNum 表数量
     * @return
     */
    boolean setMaxTable(int tableNum);
}
