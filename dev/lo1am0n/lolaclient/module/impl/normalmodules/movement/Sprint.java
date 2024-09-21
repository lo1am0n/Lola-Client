package dev.lo1am0n.lolaclient.module.impl.normalmodules.movement;

import dev.lo1am0n.lolaclient.event.impl.PreMotionEvent;
import dev.lo1am0n.lolaclient.module.LolaModule;
import dev.lo1am0n.lolaclient.module.LolaModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class Sprint extends LolaModule {
    public Sprint() {
        super("Sprint", "Automatically sprints for you", LolaModuleType.Movement);
    }

    @Override
    public void onUpdate(PreMotionEvent e) {
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), true);
    }

    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), false);
    }
}
