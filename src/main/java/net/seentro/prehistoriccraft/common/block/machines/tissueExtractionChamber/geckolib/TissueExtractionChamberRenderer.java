package net.seentro.prehistoriccraft.common.block.machines.tissueExtractionChamber.geckolib;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.seentro.prehistoriccraft.common.block.machines.tissueExtractionChamber.TissueExtractionChamberBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TissueExtractionChamberRenderer extends GeoBlockRenderer<TissueExtractionChamberBlockEntity> {
    public TissueExtractionChamberRenderer(BlockEntityRendererProvider.Context context) {
        super(new TissueExtractionChamberModel());
    }
}
