package net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.aridHorsetail;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.Vec3;
import net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.woodHorsetail.WoodHorsetailBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.templates.SingleGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class AridHorsetailRenderer extends GeoBlockRenderer<AridHorsetailBlockEntity> {
    public AridHorsetailRenderer(BlockEntityRendererProvider.Context context) {
        super(new SingleGeoModel<>("textures/block/arid_horsetail_1.png", "textures/block/arid_horsetail_2.png"));
    }

    @Override
    public void render(AridHorsetailBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Vec3 offset = animatable.getBlockState().getOffset(animatable.getLevel(), animatable.getBlockPos());
        poseStack.translate(offset.x, offset.y, offset.z);

        super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
    }
}
