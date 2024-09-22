package dev.lo1am0n.lolaclient.module.impl.misc;

import dev.lo1am0n.lolaclient.module.LolaModule;
import dev.lo1am0n.lolaclient.module.LolaModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class LolaDebug extends LolaModule {
    public LolaDebug() {
        super("Debug Messages", "Shows developer information via. chat", LolaModuleType.Misc);
    }

    public static boolean debugMessages = false;

    public static void sendDebugChatMessage(String message) {
        if (!debugMessages) return;

        if (Minecraft.getMinecraft().thePlayer != null) {
            message = "[LOLA]: " + message;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
        }
    }
    @Override
    public void onEnable() {
        debugMessages = true;
    }

    @Override
    public void onDisable() {
        debugMessages = false;
    }
}
