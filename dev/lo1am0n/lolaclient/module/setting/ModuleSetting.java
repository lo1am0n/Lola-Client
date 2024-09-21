package dev.lo1am0n.lolaclient.module.setting;

public class ModuleSetting {
    private String name;
    private String description;

    public ModuleSetting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
