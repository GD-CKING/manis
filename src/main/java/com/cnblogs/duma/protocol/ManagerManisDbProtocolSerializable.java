package com.cnblogs.duma.protocol;

import com.cnblogs.duma.ipc.ProtocolInfo;

/**
 * @Description:
 * @Author chenchuqin
 * @Date 2020/6/2 17:52
 */
@ProtocolInfo(protocolName = ManisConstants.MANAGER_MANISDB_PROTOCOL_NAME,
        protocolVersion = 1)
public interface ManagerManisDbProtocolSerializable extends ManagerProtocol {
}
