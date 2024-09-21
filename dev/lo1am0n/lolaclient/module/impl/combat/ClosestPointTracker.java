package dev.lo1am0n.lolaclient.module.impl.combat;

import dev.lo1am0n.lolaclient.event.impl.PacketSendEvent;
import dev.lo1am0n.lolaclient.event.impl.PreMotionEvent;
import dev.lo1am0n.lolaclient.event.impl.Render3DEvent;
import dev.lo1am0n.lolaclient.module.LolaModule;
import dev.lo1am0n.lolaclient.module.LolaModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

public class ClosestPointTracker extends LolaModule {

    public EntityBat iHateBoundingBoxes = null;

    public ClosestPointTracker() {
        super("Closest Point Tracker", "Shows the closest hitbox point on enemies", LolaModuleType.Combat, Keyboard.KEY_NONE);
    }

    @Override
    public void onEnable() {
        // Create and spawn the bat at the player's position initially
        iHateBoundingBoxes = new EntityBat(Minecraft.getMinecraft().theWorld);
        iHateBoundingBoxes.setPosition(Minecraft.getMinecraft().thePlayer.posX,
                Minecraft.getMinecraft().thePlayer.posY,
                Minecraft.getMinecraft().thePlayer.posZ);
        iHateBoundingBoxes.setNoAI(true);

        Minecraft.getMinecraft().theWorld.spawnEntityInWorld(iHateBoundingBoxes);
    }

    @Override
    public void onDisable() {
        if (iHateBoundingBoxes != null) {
            Minecraft.getMinecraft().theWorld.removeEntity(iHateBoundingBoxes); // Updated method
            iHateBoundingBoxes = null;
        }
    }

    public Vec3 batPos = new Vec3(0, 0, 0);

    @Override
    public void onUpdate(PreMotionEvent e) {
    }

    @Override
    public void onRender(Render3DEvent e) {
        EntityLivingBase enemy = null;
        double enemyDist = Double.MAX_VALUE;

        // Find the closest enemy to the player
        for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (entity instanceof EntityPlayer && entity != Minecraft.getMinecraft().thePlayer) {
                double distToPlayer = entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer);
                if (distToPlayer < enemyDist && distToPlayer >= 1.25 && distToPlayer <= 4.5) {
                    enemy = (EntityLivingBase) entity;
                    enemyDist = distToPlayer;
                }
            }
        }

        if (enemy == null) {
            return; // No enemy found
        }

        Vec3 playerPos = new Vec3(Minecraft.getMinecraft().thePlayer.posX,
                Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(),
                Minecraft.getMinecraft().thePlayer.posZ);

        AxisAlignedBB enemyBB = enemy.getEntityBoundingBox();
        Vec3 closestPoint = getClosestPointOnBoundingBox(playerPos, enemyBB);

        if (closestPoint != null) {
            iHateBoundingBoxes.setPosition(closestPoint.xCoord, closestPoint.yCoord, closestPoint.zCoord);
            iHateBoundingBoxes.onUpdate();

            System.out.println("Closest Point: " + closestPoint + " | Bat Position: " + iHateBoundingBoxes.getPosition());
        }
    }

    private Vec3 getClosestPointOnBoundingBox(Vec3 playerPos, AxisAlignedBB boundingBox) {
        double closestX = clamp(playerPos.xCoord, boundingBox.minX, boundingBox.maxX);
        double closestY = clamp(playerPos.yCoord, boundingBox.minY, boundingBox.maxY);
        double closestZ = clamp(playerPos.zCoord, boundingBox.minZ, boundingBox.maxZ);
        return new Vec3(closestX, closestY, closestZ);
    }

    @Override
    public void onPacket(PacketSendEvent e) {
        // Makes sure that bat entity I added doesn't mess with anticheats
        if (e.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity packet = (C02PacketUseEntity) e.getPacket();

            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK && packet.getEntityFromWorld(Minecraft.getMinecraft().theWorld).getEntityId() == iHateBoundingBoxes.getEntityId()) {
                EntityLivingBase enemy = null;
                double enemyDist = Double.MAX_VALUE;

                for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                    if (entity instanceof EntityPlayer && entity != Minecraft.getMinecraft().thePlayer) {
                        double distToPlayer = entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer);
                        if (distToPlayer < enemyDist && distToPlayer >= 1.25 && distToPlayer <= 4.5) {
                            enemy = (EntityLivingBase) entity;
                            enemyDist = distToPlayer;
                        }
                    }
                }

                if (enemy == null) {
                    // whoops!
                    return;
                }

                e.setPacket(new C02PacketUseEntity(enemy, C02PacketUseEntity.Action.ATTACK));
            }
        }
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
