package com.cnblogs.duma;

import com.cnblogs.duma.conf.Configuration;

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
}
