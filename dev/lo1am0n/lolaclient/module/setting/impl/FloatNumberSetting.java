package dev.lo1am0n.lolaclient.module.setting.impl;

import dev.lo1am0n.lolaclient.module.setting.ModuleSetting;

public class FloatNumberSetting extends ModuleSetting {
    private float currentValue;
    private float minimumValue;
    private float maximumValue;
    private float incrementBy;

    public FloatNumberSetting(String name, String description, float currentValue, float minimumValue, float maximumValue, float incrementBy) {
        super(name, description);
        this.currentValue = currentValue;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.incrementBy = incrementBy;
    }


    public float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }

    public float getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(float minimumValue) {
        this.minimumValue = minimumValue;
    }

    public float getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(float maximumValue) {
        this.maximumValue = maximumValue;
    }

    public float getIncrementBy() {
        return incrementBy;
    }

    public void setIncrementBy(float incrementBy) {
        this.incrementBy = incrementBy;
    }
}
