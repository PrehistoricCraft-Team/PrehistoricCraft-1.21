package net.seentro.prehistoriccraft.common.block.machines.acidCleaningChamber.geckolib;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.seentro.prehistoriccraft.common.block.machines.acidCleaningChamber.AcidCleaningChamberBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class AcidCleaningChamberRenderer extends GeoBlockRenderer<AcidCleaningChamberBlockEntity> {
    public AcidCleaningChamberRenderer(BlockEntityRendererProvider.Context context) {
        super(new AcidCleaningChamberModel());
    }
}
