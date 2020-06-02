package com.cnblogs.duma.ipc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用户客户端与服务端连接的协议
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ProtocolInfo {

    // 协议名称
    String protocolName();

    // 协议版本
    long protocolVersion() default -1;
}
