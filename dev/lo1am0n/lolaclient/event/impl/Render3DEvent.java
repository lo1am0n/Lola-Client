package dev.lo1am0n.lolaclient.event.impl;

import dev.lo1am0n.lolaclient.event.LolaEvent;

public class Render3DEvent extends LolaEvent {
    /*
    Used for drawing 3D stuff on the screen
    */
    private int partialTicks = 0;

    public Render3DEvent(int partialTicks) {
        this.partialTicks = partialTicks;
    }

    public int getPartialTicks() {
        return partialTicks;
    }
}
