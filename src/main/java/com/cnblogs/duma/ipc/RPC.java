package com.cnblogs.duma.ipc;

import com.cnblogs.duma.conf.Configuration;

import java.lang.reflect.Constructor;
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
     * @param conf 配置
     * @param protocol 协议接口
     * @param engine 实现的引擎
     */
    public static void setProtocolEngine(Configuration conf, Class<?> protocol, Class<?> engine) {
        conf.setClass(RPC_ENGINE + "." + protocol.getName(), engine, RpcEngine.class);
    }

    public synchronized <T> RpcEngine getProtocolEngine(Class<T> protocol, Configuration conf) {
        RpcEngine engine = PROTOCOL_ENGINE.get(protocol);
        if(engine == null) {
            Class<?> clazz = conf.getClass(RPC_ENGINE + "." + protocol.getName(), SerializableRpcEngine.class);

            try{
                // 通过反射实例化 RpcEngine 的实现类
                Constructor constructor = clazz.getDeclaredConstructor();
                engine = (RpcEngine) constructor.newInstance();
                PROTOCOL_ENGINE.put(protocol, engine);
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return engine;
    }
}
