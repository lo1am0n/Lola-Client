package dev.lo1am0n.lolaclient.module.impl.combat.closestpointtracker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCustomEntityCPT extends RenderLiving<CustomEntityCPT>
{
    private static final ResourceLocation batTextures = new ResourceLocation("textures/entity/bat.png");

    public RenderCustomEntityCPT(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelBat(), 0.25F);
    }

    @Override
    protected ResourceLocation getEntityTexture(CustomEntityCPT entity) {
        return null;
    }

    @Override
    public void doRender(CustomEntityCPT entity, double x, double y, double z, float entityYaw, float partialTicks) {
        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;

        // this.doRenderEntity(entity, d0 - this.renderPosX, d1 - this.renderPosY, d2 - this.renderPosZ, f, partialTicks, p_147936_3_);

        RenderManager.renderDebugBoundingBox2(entity, d0 - RenderManager.posX2, d1 - RenderManager.posY2, d2 - RenderManager.posZ2, Minecraft.getMinecraft().thePlayer.rotationYaw, 1.0F);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
