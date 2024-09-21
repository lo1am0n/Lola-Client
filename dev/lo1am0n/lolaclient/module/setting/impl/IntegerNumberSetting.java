package dev.lo1am0n.lolaclient.module.setting.impl;

import dev.lo1am0n.lolaclient.module.setting.ModuleSetting;

public class IntegerNumberSetting extends ModuleSetting {
    private int currentValue;
    private int minimumValue;
    private int maximumValue;
    private int incrementBy;

    public IntegerNumberSetting(String name, String description, int currentValue, int minimumValue, int maximumValue, int incrementBy) {
        super(name, description);
        this.currentValue = currentValue;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.incrementBy = incrementBy;
    }


    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public int getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(int minimumValue) {
        this.minimumValue = minimumValue;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(int maximumValue) {
        this.maximumValue = maximumValue;
    }

    public int getIncrementBy() {
        return incrementBy;
    }

    public void setIncrementBy(int incrementBy) {
        this.incrementBy = incrementBy;
    }
}
