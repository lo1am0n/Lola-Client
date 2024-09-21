package dev.lo1am0n.lolaclient.event.impl;

import dev.lo1am0n.lolaclient.event.LolaEvent;

public class ServerJoinEvent extends LolaEvent {
    private String ip;
    private int port;
    public ServerJoinEvent(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
