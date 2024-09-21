package dev.lo1am0n.lolaclient.module.setting.impl;

import dev.lo1am0n.lolaclient.module.setting.ModuleSetting;

import java.util.concurrent.CopyOnWriteArrayList;

public class ModeSetting extends ModuleSetting {
    private CopyOnWriteArrayList<String> modes;
    private String currentMode;

    public ModeSetting(String name, String description, CopyOnWriteArrayList<String> modes, String currentMode) {
        super(name, description);

        this.modes = modes;
        this.currentMode = currentMode;
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(String currentMode) {
        this.currentMode = currentMode;
    }

    public CopyOnWriteArrayList<String> getModes() {
        return modes;
    }
}
