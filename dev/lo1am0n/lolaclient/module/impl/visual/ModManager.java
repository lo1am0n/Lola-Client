package dev.lo1am0n.lolaclient.module.impl.visual;

import dev.lo1am0n.lolaclient.module.LolaModule;
import dev.lo1am0n.lolaclient.module.LolaModuleType;
import dev.lo1am0n.lolaclient.module.impl.visual.modmanager.ModManagerGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class ModManager extends LolaModule {
    public ModManager() {
        super("Mod Manager", "Shows a GUI to manage all mods", LolaModuleType.Visual, Keyboard.KEY_RSHIFT);
    }

    public ModManagerGui actualGui; // looks awfully familiar, doesn't it cheaters?

    @Override
    public void onEnable() {
        if (actualGui == null) {
            actualGui = new ModManagerGui();
        }

        Minecraft.getMinecraft().displayGuiScreen(actualGui);
    }

    @Override
    public void onDisable() {
        if (actualGui == null) {
            actualGui = new ModManagerGui();
        }

        Minecraft.getMinecraft().displayGuiScreen(actualGui);
    }
}
