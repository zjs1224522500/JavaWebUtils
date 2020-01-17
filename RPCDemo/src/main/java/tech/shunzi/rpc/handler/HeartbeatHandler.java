package tech.shunzi.rpc.handler;

import tech.shunzi.rpc.entity.Cmder;
import tech.shunzi.rpc.entity.HeartbeatEntity;

public interface HeartbeatHandler {
    public Cmder sendHeartBeat(HeartbeatEntity info);
}
