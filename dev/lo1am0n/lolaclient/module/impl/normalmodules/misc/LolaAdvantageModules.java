package dev.lo1am0n.lolaclient.module.impl.normalmodules.misc;

import dev.lo1am0n.lolaclient.module.LolaModule;
import dev.lo1am0n.lolaclient.module.LolaModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class LolaAdvantageModules extends LolaModule {
    public LolaAdvantageModules() {
        super("Advantage Modules", "Enables bannable/advantage modules if you have permission to", LolaModuleType.Misc);
    }

    @Override
    public void onEnable() {
        if (Minecraft.getMinecraft().thePlayer.getName() == "Lo1am0n" || Minecraft.getMinecraft().thePlayer.getName() == "LolaClientDebug") {
            Minecraft.getMinecraft().getLolaClient().allowAdvantageModules = true;
            LolaDebug.sendDebugChatMessage("Successfully enabled bannable modules!");
        }
        else {
            LolaDebug.sendDebugChatMessage("Unable to enable advantage (bannable) modules: No permission.");
        }
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().getLolaClient().allowAdvantageModules = false;
    }
}
