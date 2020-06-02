package com.cnblogs.duma.ipc;

import com.cnblogs.duma.conf.Configuration;

import javax.net.SocketFactory;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class RPC {

    public static final String RPC_ENGINE = "rpc.engine";

    /**
     * 接口与 RPC 引擎对应关系的缓存
     */
    private static final Map<Class<?>, RpcEngine> PROTOCOL_ENGINE = new HashMap<>();

    /**
     * 为协议（接口）设置 RPC 引擎
     *
     * @param conf     配置
     * @param protocol 协议接口
     * @param engine   实现的引擎
     */
    public static void setProtocolEngine(Configuration conf, Class<?> protocol, Class<?> engine) {
        conf.setClass(RPC_ENGINE + "." + protocol.getName(), engine, RpcEngine.class);
    }

    /**
     * 根据协议和配置返回该协议对应的RPC引擎
     *
     * @param protocol
     * @param conf
     * @param <T>
     * @return
     */
    static synchronized <T> RpcEngine getProtocolEngine(Class<T> protocol, Configuration conf) {
        RpcEngine engine = PROTOCOL_ENGINE.get(protocol);
        if (engine == null) {
            Class<?> clazz = conf.getClass(RPC_ENGINE + "." + protocol.getName(), SerializableRpcEngine.class);

            try {
                // 通过反射实例化 RpcEngine 的实现类
                Constructor constructor = clazz.getDeclaredConstructor();
                engine = (RpcEngine) constructor.newInstance();
                PROTOCOL_ENGINE.put(protocol, engine);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return engine;
    }

    public static <T> T getProtocolProxy(Class<T> protocol,
                                         long clinetVersion,
                                         InetSocketAddress address,
                                         Configuration conf,
                                         SocketFactory factory,
                                         int rpcTimeOut) throws IOException {
        return getProtocolEngine(protocol, conf).getProxy(protocol, clinetVersion, address, conf, factory, rpcTimeOut);
    }

    /**
     * 获得协议版本号
     *
     * @param protocol
     * @return
     * @throws IllegalAccessException
     */
    public static long getProtocolVersion(Class<?> protocol) {
        try {
            if (protocol == null) {
                throw new IllegalAccessException("Null protocol");
            }

            long version;

            ProtocolInfo anno = protocol.getAnnotation(ProtocolInfo.class);
            if (anno != null) {
                version = anno.protocolVersion();
                if (version != -1) {
                    return version;
                }
            }

            Field versionField = protocol.getField("versionId");
            versionField.setAccessible(true);
            return versionField.getLong(protocol);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
