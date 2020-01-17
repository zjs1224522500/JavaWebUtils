package tech.shunzi.rpc.handler.impl;

import tech.shunzi.rpc.entity.Cmder;
import tech.shunzi.rpc.entity.HeartbeatEntity;
import tech.shunzi.rpc.handler.HeartbeatHandler;
import tech.shunzi.rpc.listener.HeartbeatListener;

import java.util.Map;

public class HeartbeatHandlerImpl implements HeartbeatHandler {
    public Cmder sendHeartBeat(HeartbeatEntity info) {
        HeartbeatListener listener = HeartbeatListener.getInstance();

        // 添加节点
        if (!listener.checkNodeValid(info.getNodeID())) {
            listener.registerNode(info.getNodeID(), info);
        }

        // 其他操作
        Cmder cmder = new Cmder();
        cmder.setNodeID(info.getNodeID());
        // ...

        System.out.println("current all the nodes: ");
        Map<String, Object> nodes = listener.getNodes();
        for (Map.Entry e : nodes.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
        System.out.println("handle a heartbeat");
        return cmder;
    }
}