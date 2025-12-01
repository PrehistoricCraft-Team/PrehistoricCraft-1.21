package net.seentro.prehistoriccraft.common.block.dnaRecombinator.geckolib;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.seentro.prehistoriccraft.common.block.dnaRecombinator.DNARecombinatorBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class DNARecombinatorRenderer extends GeoBlockRenderer<DNARecombinatorBlockEntity> {
    public DNARecombinatorRenderer(BlockEntityRendererProvider.Context context) {
        super(new DNARecombinatorModel());
    }
}
