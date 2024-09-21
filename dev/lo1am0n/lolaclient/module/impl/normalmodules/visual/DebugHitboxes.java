package dev.lo1am0n.lolaclient.module.impl.normalmodules.visual;

import dev.lo1am0n.lolaclient.module.LolaModule;
import dev.lo1am0n.lolaclient.module.LolaModuleType;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class DebugHitboxes extends LolaModule {
    public DebugHitboxes() {
        super("Hitboxes", "Shows debug hitboxes for all entities", LolaModuleType.Visual, Keyboard.KEY_NONE);
    }

    @Override
    public void onEnable() {
        Minecraft.getMinecraft().getRenderManager().setDebugBoundingBox(true);
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().getRenderManager().setDebugBoundingBox(false);
    }
}
