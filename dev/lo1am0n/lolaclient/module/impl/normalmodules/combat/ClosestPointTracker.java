package dev.lo1am0n.lolaclient.module.impl.normalmodules.combat;

import dev.lo1am0n.lolaclient.event.impl.*;
import dev.lo1am0n.lolaclient.module.LolaModule;
import dev.lo1am0n.lolaclient.module.LolaModuleType;
import dev.lo1am0n.lolaclient.module.impl.normalmodules.combat.closestpointtracker.CustomEntityCPT;
import dev.lo1am0n.lolaclient.module.setting.impl.DoubleNumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

public class ClosestPointTracker extends LolaModule {

    public CustomEntityCPT iHateBoundingBoxes = null;
    public boolean shouldRaycast = false;

    public ClosestPointTracker() {
        super("Closest Point Tracker", "Shows the closest hitbox point on enemies", LolaModuleType.Combat, Keyboard.KEY_NONE);

        // Color purple = new Color(152, 96, 255);
        this.getModuleSettings().add(new DoubleNumberSetting("Color (R)", "The amount of red in the hitbox color", 152, 1, 255, 1));
        this.getModuleSettings().add(new DoubleNumberSetting("Color (G)", "The amount of green in the hitbox color", 96, 1, 255, 1));
        this.getModuleSettings().add(new DoubleNumberSetting("Color (B)", "The amount of blue in the hitbox color", 255, 1, 255, 1));
        this.getModuleSettings().add(new DoubleNumberSetting("Size (Blocks)", "The hitbox size", 0.1, 0.1, 0.5, 0.05));
    }

    public static int CPT_COLOR_R = 255;
    public static int CPT_COLOR_G = 255;
    public static int CPT_COLOR_B = 255;
    public static double CPT_HITBOX_SIZE = 0.1;

    @Override
    public void onEnable() {
        if (iHateBoundingBoxes == null) {
            // Create and spawn the bat at the player's position initially
            iHateBoundingBoxes = new CustomEntityCPT(Minecraft.getMinecraft().theWorld);
            iHateBoundingBoxes.setPosition(Minecraft.getMinecraft().thePlayer.posX,
                    Minecraft.getMinecraft().thePlayer.posY + 10,
                    Minecraft.getMinecraft().thePlayer.posZ);
            iHateBoundingBoxes.setNoAI(true);

            Minecraft.getMinecraft().theWorld.spawnEntityInWorld(iHateBoundingBoxes);
        }
    }

    @Override
    public void onDisable() {
        if (iHateBoundingBoxes != null) {
            Minecraft.getMinecraft().theWorld.removeEntity(iHateBoundingBoxes); // Updated method
            iHateBoundingBoxes = null;
        }
    }

    @Override
    public void onUpdate(PreMotionEvent e) {
    }

    @Override
    public void onRender(Render3DEvent e) {
        DoubleNumberSetting rgb_r = (DoubleNumberSetting) this.getModuleSettingByName("Color (R)");
        DoubleNumberSetting rgb_g = (DoubleNumberSetting) this.getModuleSettingByName("Color (G)");
        DoubleNumberSetting rgb_b = (DoubleNumberSetting) this.getModuleSettingByName("Color (B)");
        DoubleNumberSetting size = (DoubleNumberSetting) this.getModuleSettingByName("Size (Blocks)");

        CPT_COLOR_R = (int) rgb_r.getCurrentValue();
        CPT_COLOR_G = (int) rgb_g.getCurrentValue();
        CPT_COLOR_B = (int) rgb_b.getCurrentValue();
        CPT_HITBOX_SIZE = size.getCurrentValue();

        EntityLivingBase enemy = null;
        double enemyDist = Double.MAX_VALUE;

        // Find the closest enemy to the player
        for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (entity instanceof EntityPlayer && entity != Minecraft.getMinecraft().thePlayer) {
                double distToPlayer = entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer);
                if (distToPlayer < enemyDist && distToPlayer >= 1.25 && distToPlayer <= 5.5) {
                    enemy = (EntityLivingBase) entity;
                    enemyDist = distToPlayer;
                }
            }
        }

        if (enemy == null) {
            if (iHateBoundingBoxes != null) {
                iHateBoundingBoxes.setPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 10, Minecraft.getMinecraft().thePlayer.posZ);
                iHateBoundingBoxes.onUpdate();
            }
            return; // No enemy found
        }

        Vec3 playerPos = new Vec3(Minecraft.getMinecraft().thePlayer.posX,
                Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(),
                Minecraft.getMinecraft().thePlayer.posZ);

        AxisAlignedBB enemyBB = enemy.getEntityBoundingBox();
        Vec3 closestPoint = getClosestPointOnBoundingBox(playerPos, enemyBB);

        if (closestPoint != null) {
            if (iHateBoundingBoxes == null) {
                iHateBoundingBoxes = new CustomEntityCPT(Minecraft.getMinecraft().theWorld);
                iHateBoundingBoxes.setPosition(Minecraft.getMinecraft().thePlayer.posX,
                        Minecraft.getMinecraft().thePlayer.posY + 10,
                        Minecraft.getMinecraft().thePlayer.posZ);
                iHateBoundingBoxes.setNoAI(true);

                Minecraft.getMinecraft().theWorld.spawnEntityInWorld(iHateBoundingBoxes);
            }


            if (iHateBoundingBoxes != null) {
                iHateBoundingBoxes.setPosition(closestPoint.xCoord, closestPoint.yCoord, closestPoint.zCoord);
                iHateBoundingBoxes.onUpdate();

                Minecraft.getMinecraft().theWorld.removeEntity(iHateBoundingBoxes); // Updated method
                iHateBoundingBoxes = null;
            }
        }
    }

    private Vec3 getClosestPointOnBoundingBox(Vec3 playerPos, AxisAlignedBB boundingBox) {
        double closestX = clamp(playerPos.xCoord, boundingBox.minX, boundingBox.maxX);
        double closestY = clamp(playerPos.yCoord, boundingBox.minY, boundingBox.maxY);
        double closestZ = clamp(playerPos.zCoord, boundingBox.minZ, boundingBox.maxZ);
        return new Vec3(closestX, closestY, closestZ);
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
