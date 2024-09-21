package dev.lo1am0n.lolaclient.module.impl.normalmodules.visual;

import dev.lo1am0n.lolaclient.event.impl.PacketSendEvent;
import dev.lo1am0n.lolaclient.event.impl.Render3DEvent;
import dev.lo1am0n.lolaclient.module.LolaModule;
import dev.lo1am0n.lolaclient.module.LolaModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FreeLook extends LolaModule {
    public FreeLook() {
        super("Free Look", "Allows you to move your camera without changing rotations (bannable?)", LolaModuleType.Visual);
    }

    private float rotationYaw = 0.0f;
    private float rotationPitch = 0.0f;

    private float cameraYaw = 0.0f;
    private float cameraPitch = 0.0f;

    @Override
    public void onEnable() {
        rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;

        cameraYaw = Minecraft.getMinecraft().thePlayer.cameraYaw;
        cameraPitch = Minecraft.getMinecraft().thePlayer.cameraPitch;

        Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
        Minecraft.getMinecraft().entityRenderer.loadEntityShader((Entity)null);
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
        Minecraft.getMinecraft().entityRenderer.loadEntityShader(Minecraft.getMinecraft().getRenderViewEntity());
    }

    @Override
    public void onRender(Render3DEvent e) {
        Minecraft.getMinecraft().thePlayer.rotationYawHead = rotationYaw;
        Minecraft.getMinecraft().thePlayer.rotationPitchHead = rotationPitch;

        Minecraft.getMinecraft().thePlayer.rotationYawMove = rotationYaw;
    }

    @Override
    public void onPacket(PacketSendEvent e) {
        if (e.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
            C03PacketPlayer.C06PacketPlayerPosLook posLook = (C03PacketPlayer.C06PacketPlayerPosLook) e.getPacket();

            e.setCancelled(true);

            // Remove the old packet
            for (NetworkManager.InboundHandlerTuplePacketListener thing : Minecraft.getMinecraft().getNetHandler().getNetworkManager().outboundPacketsQueue) {
                if (thing.packet == e.getPacket()) {
                    Minecraft.getMinecraft().getNetHandler().getNetworkManager().outboundPacketsQueue.remove(thing);
                }
            }


            // Create and send new packet
            C03PacketPlayer.C04PacketPlayerPosition theNewPacket = new C03PacketPlayer.C04PacketPlayerPosition(posLook.getPositionX(), posLook.getPositionY(), posLook.getPositionZ(), posLook.isOnGround());
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(theNewPacket);
        }
        if (e.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook) {
            e.setCancelled(true);

            for (NetworkManager.InboundHandlerTuplePacketListener thing : Minecraft.getMinecraft().getNetHandler().getNetworkManager().outboundPacketsQueue) {
                if (thing.packet == e.getPacket()) {
                    Minecraft.getMinecraft().getNetHandler().getNetworkManager().outboundPacketsQueue.remove(thing);
                }
            }
        }
    }
}
