package net.seentro.prehistoriccraft.common.nature.dawnRedwood.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.nature.dawnRedwood.DawnRedwoodSaplingBlockEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.specialty.DynamicGeoBlockRenderer;
import software.bernie.geckolib.renderer.specialty.DynamicGeoEntityRenderer;
import software.bernie.geckolib.util.Color;

public class DawnRedwoodSaplingRenderer extends DynamicGeoBlockRenderer<DawnRedwoodSaplingBlockEntity> {
    public DawnRedwoodSaplingRenderer(BlockEntityRendererProvider.Context context) {
        super(new DawnRedwoodSaplingModel());
    }

    @Override
    protected @Nullable ResourceLocation getTextureOverrideForBone(GeoBone bone, DawnRedwoodSaplingBlockEntity animatable, float partialTick) {
        if (bone.getName().equals("bb_overlay")) {
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/dawn_redwood_sapling_3_wood.png");
        }

        return super.getTextureOverrideForBone(bone, animatable, partialTick);
    }

    @Override
    public Color getRenderColor(DawnRedwoodSaplingBlockEntity animatable, float partialTick, int packedLight) {
        BlockState state = animatable.getBlockState();
        Level level = animatable.getLevel();
        BlockPos pos = animatable.getBlockPos();

        if (level != null) {
            return new Color(Minecraft.getInstance().getBlockColors().getColor(state, level, pos, 0));
        }

        return Color.WHITE;
    }

    @Override
    public void renderRecursively(PoseStack poseStack, DawnRedwoodSaplingBlockEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        if (bone.getName().equals("bb_overlay")) {
            colour = Color.WHITE.argbInt();
        }

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }

    @Override
    public @Nullable RenderType getRenderType(DawnRedwoodSaplingBlockEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }

    @Override
    public AABB getRenderBoundingBox(DawnRedwoodSaplingBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 5, pos.getZ() + 1.0);
    }

    @Override
    public boolean shouldRender(DawnRedwoodSaplingBlockEntity blockEntity, Vec3 cameraPos) {
        return Vec3.atCenterOf(blockEntity.getBlockPos()).multiply(1.0, 0.0, 1.0).closerThan(cameraPos.multiply(1.0, 0.0, 1.0), (double)this.getViewDistance());
    }

    @Override
    public boolean shouldRenderOffScreen(DawnRedwoodSaplingBlockEntity blockEntity) {
        return true;
    }
}
