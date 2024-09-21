package dev.lo1am0n.lolaclient.event.impl;

import dev.lo1am0n.lolaclient.event.LolaEvent;

public class Render2DEvent extends LolaEvent {
    /*
    Used for drawing 2D Elements on the screen
     */
    private int partialTicks = 0;

    public Render2DEvent(int partialTicks) {
        this.partialTicks = partialTicks;
    }

    public int getPartialTicks() {
        return partialTicks;
    }
}
