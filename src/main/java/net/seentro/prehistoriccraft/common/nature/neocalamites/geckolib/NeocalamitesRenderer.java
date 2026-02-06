package net.seentro.prehistoriccraft.common.nature.neocalamites.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import net.seentro.prehistoriccraft.common.nature.neocalamites.NeocalamitesBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class NeocalamitesRenderer extends GeoBlockRenderer<NeocalamitesBlockEntity> {
    public NeocalamitesRenderer(BlockEntityRendererProvider.Context context) {
        super(new NeocalamitesModel());
    }

    @Override
    public void render(NeocalamitesBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Vec3 offset = animatable.getBlockState().getOffset(animatable.getLevel(), animatable.getBlockPos());
        poseStack.translate(offset.x, offset.y, offset.z);

        super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
    }
}
