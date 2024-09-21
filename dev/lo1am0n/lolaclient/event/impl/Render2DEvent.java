package dev.lo1am0n.lolaclient.event.impl;

import dev.lo1am0n.lolaclient.event.LolaEvent;

public class Render2DEvent extends LolaEvent {
    /*
    Used for drawing 2D Elements on the screen
     */
    private float partialTicks = 0;

    public Render2DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
