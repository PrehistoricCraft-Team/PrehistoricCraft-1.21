package net.seentro.prehistoriccraft.common.block.dnaSeparationFilter.geckolib;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.seentro.prehistoriccraft.common.block.dnaSeparationFilter.DNASeparationFilterBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class DNASeparationFilterRenderer extends GeoBlockRenderer<DNASeparationFilterBlockEntity> {
    public DNASeparationFilterRenderer(BlockEntityRendererProvider.Context context) {
        super(new DNASeparationFilterModel());
    }
}
