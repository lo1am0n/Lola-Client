package dev.lo1am0n.lolaclient.event.impl;

import dev.lo1am0n.lolaclient.event.LolaEvent;
import net.minecraft.network.Packet;

public class PacketSendEvent extends LolaEvent {
    private Packet packet;

    public PacketSendEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
