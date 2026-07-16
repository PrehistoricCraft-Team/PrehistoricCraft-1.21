package net.seentro.prehistoriccraft.common.block.nature.simplePlants.kerpia.geckolib;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.seentro.prehistoriccraft.common.block.nature.simplePlants.kerpia.KerpiaBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class KerpiaRenderer extends GeoBlockRenderer<KerpiaBlockEntity> {
    public KerpiaRenderer(BlockEntityRendererProvider.Context context) {
        super(new KerpiaModel());
    }
}
