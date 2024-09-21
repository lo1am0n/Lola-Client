package dev.lo1am0n.lolaclient.event.impl;

import dev.lo1am0n.lolaclient.event.LolaEvent;

public class Render3DEvent extends LolaEvent {
    /*
    Used for drawing 3D stuff on the screen
    */
    private float partialTicks = 0;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
