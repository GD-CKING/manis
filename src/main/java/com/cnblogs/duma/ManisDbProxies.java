package com.cnblogs.duma;

import com.cnblogs.duma.conf.Configuration;
import com.cnblogs.duma.ipc.RPC;
import com.cnblogs.duma.ipc.SerializableRpcEngine;
import com.cnblogs.duma.protocol.ManagerManisDbProtocolSerializable;
import com.cnblogs.duma.protocol.ManagerProtocol;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

public class ManisDbProxies {

    public static class ProxyInfo<PROXYTRPE> {

        private final PROXYTRPE proxy;

        private final InetSocketAddress address;

        public ProxyInfo(PROXYTRPE proxy, InetSocketAddress address) {
            this.proxy = proxy;
            this.address = address;
        }

        public PROXYTRPE getProxy() {
            return proxy;
        }

        public InetSocketAddress getAddress() {
            return address;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> ProxyInfo<T> createProxy(Configuration conf,
                                               URI uri, Class<T> xface) throws IOException {
        return null;
    }

    private static ManagerProtocol createManisDbProxyWithManagerProtocol(Configuration conf,
                                                                         InetSocketAddress address) throws IOException {
        RPC.setProtocolEngine(conf, ManagerManisDbProtocolSerializable.class, SerializableRpcEngine.class);

        final long version = RPC.getProtocolVersion(ManagerManisDbProtocolSerializable.class);

        int rpcTimeOut = 6000;

        ManagerManisDbProtocolSerializable proxy =
                RPC.getProtocolProxy(ManagerManisDbProtocolSerializable.class, version,
                            address, conf, SocketFactory.getDefault(), rpcTimeOut);
        return proxy;
    }
}
