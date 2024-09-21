package dev.lo1am0n.lolaclient.module.setting.impl;

import dev.lo1am0n.lolaclient.module.setting.ModuleSetting;

public class DoubleNumberSetting extends ModuleSetting {
    private double currentValue;
    private double minimumValue;
    private double maximumValue;
    private double incrementBy;

    public DoubleNumberSetting(String name, String description, double currentValue, double minimumValue, double maximumValue, double incrementBy) {
        super(name, description);
        this.currentValue = currentValue;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.incrementBy = incrementBy;
    }


    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(double minimumValue) {
        this.minimumValue = minimumValue;
    }

    public double getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(double maximumValue) {
        this.maximumValue = maximumValue;
    }

    public double getIncrementBy() {
        return incrementBy;
    }

    public void setIncrementBy(double incrementBy) {
        this.incrementBy = incrementBy;
    }
}
