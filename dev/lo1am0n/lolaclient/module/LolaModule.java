package dev.lo1am0n.lolaclient.module;

import dev.lo1am0n.lolaclient.event.LolaEvent;
import dev.lo1am0n.lolaclient.event.impl.*;
import dev.lo1am0n.lolaclient.module.setting.ModuleSetting;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArrayList;

public class LolaModule {
    /*
    Before you say anything:
    No, this is not a hacked client style module, although it works a similar way.
     */

    private String name;
    private String description;
    private boolean enabled = false;
    private int keybind = Keyboard.KEY_NONE;
    private LolaModuleType category = LolaModuleType.Misc;
    private CopyOnWriteArrayList<ModuleSetting> moduleSettings = new CopyOnWriteArrayList<>();

    public LolaModule(String name, String description, LolaModuleType category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
    public LolaModule(String name, String description, LolaModuleType category, boolean enabled) {
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.category = category;
    }
    public LolaModule(String name, String description, LolaModuleType category, int keybind) {
        this.name = name;
        this.description = description;
        this.keybind = keybind;
        this.category = category;
    }
    public LolaModule(String name, String description, LolaModuleType category, int keybind, boolean enabled) {
        this.name = name;
        this.description = description;
        this.keybind = keybind;
        this.enabled = enabled;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (this.enabled) {
            onEnable();
        }
        else {
            onDisable();
        }
    }

    public LolaModuleType getCategory() {
        return category;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setModuleSettings(CopyOnWriteArrayList<ModuleSetting> moduleSettings) {
        this.moduleSettings = moduleSettings;
    }

    public CopyOnWriteArrayList<ModuleSetting> getModuleSettings() {
        return moduleSettings;
    }

    public void handleEvent(LolaEvent e) {
        // taken from my old hacked client :)

        for (Method theMethod : this.getClass().getMethods()) {
            if (theMethod.getParameterTypes().length > 0 && theMethod.getParameterTypes()[0] == e.getClass()) {
                try {
                    theMethod.invoke(this, e);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void onEnable() {}
    public void onDisable() {}

    public void onPacket(PacketReceiveEvent e) {}
    public void onPacket(PacketSendEvent e) {}

    public void onRender(Render2DEvent e) {}
    public void onRender(Render3DEvent e) {}

    public void onHitReg(HitRegistrationEvent e) {}

    public void onUpdate(PreMotionEvent e) {}

    public void onServerConnect(ServerJoinEvent e) {}
}
