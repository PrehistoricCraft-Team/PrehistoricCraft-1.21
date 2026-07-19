package net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.marshHorsetail;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import net.seentro.prehistoriccraft.common.block.nature.templates.DoubleVariantTemplateGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MarshHorsetailRenderer extends GeoBlockRenderer<MarshHorsetailBlockEntity> {
    public MarshHorsetailRenderer(BlockEntityRendererProvider.Context context) {
        super(new DoubleVariantTemplateGeoModel<>("textures/block/marsh_horsetail_1.png",
                "textures/block/marsh_horsetail_2.png", "geo/block/templates/18x16_plant.geo.json"));
    }

    @Override
    public void render(MarshHorsetailBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Vec3 offset = animatable.getBlockState().getOffset(animatable.getLevel(), animatable.getBlockPos());
        poseStack.translate(offset.x, offset.y, offset.z);

        super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
    }
}
