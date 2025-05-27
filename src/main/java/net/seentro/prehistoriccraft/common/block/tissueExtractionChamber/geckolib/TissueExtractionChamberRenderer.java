package net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.geckolib;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.TissueExtractionChamberBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TissueExtractionChamberRenderer extends GeoBlockRenderer<TissueExtractionChamberBlockEntity> {
    public TissueExtractionChamberRenderer(BlockEntityRendererProvider.Context context) {
        super(new TissueExtractionChamberModel());
    }
}
