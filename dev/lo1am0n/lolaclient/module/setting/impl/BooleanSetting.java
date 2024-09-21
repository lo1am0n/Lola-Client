package dev.lo1am0n.lolaclient.module.setting.impl;

import dev.lo1am0n.lolaclient.module.setting.ModuleSetting;

public class BooleanSetting extends ModuleSetting {
    private boolean currentValue;

    public BooleanSetting(String name, String description, boolean currentValue) {
        super(name, description);

        this.currentValue = currentValue;
    }

    public boolean isCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(boolean currentValue) {
        this.currentValue = currentValue;
    }
}
