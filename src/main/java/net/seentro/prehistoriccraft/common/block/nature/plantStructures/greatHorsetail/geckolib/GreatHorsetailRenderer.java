package net.seentro.prehistoriccraft.common.block.nature.plantStructures.greatHorsetail.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.Vec3;
import net.seentro.prehistoriccraft.common.block.nature.plantStructures.greatHorsetail.GreatHorsetailBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.plantStructures.neocalamites.NeocalamitesBlock;
import net.seentro.prehistoriccraft.common.block.nature.plantStructures.neocalamites.NeocalamitesBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.plantStructures.neocalamites.geckolib.NeocalamitesModel;
import net.seentro.prehistoriccraft.core.multiblock.QuadrupleInvisibleSegmentProperty;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GreatHorsetailRenderer extends GeoBlockRenderer<GreatHorsetailBlockEntity> {
    public GreatHorsetailRenderer(BlockEntityRendererProvider.Context context) {
        super(new GreatHorsetailModel());
    }

    @Override
    public void render(GreatHorsetailBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Vec3 offset = animatable.getBlockState().getOffset(animatable.getLevel(), animatable.getBlockPos());
        poseStack.translate(offset.x, offset.y, offset.z);

        super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
    }

    @Override
    public boolean shouldRender(GreatHorsetailBlockEntity blockEntity, Vec3 cameraPos) {
        return blockEntity.getBlockState().getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) != DoubleBlockHalf.UPPER;
    }
}
