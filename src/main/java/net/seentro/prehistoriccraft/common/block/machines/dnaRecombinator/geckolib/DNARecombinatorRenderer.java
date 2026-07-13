package net.seentro.prehistoriccraft.common.block.machines.dnaRecombinator.geckolib;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.seentro.prehistoriccraft.common.block.machines.dnaRecombinator.DNARecombinatorBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class DNARecombinatorRenderer extends GeoBlockRenderer<DNARecombinatorBlockEntity> {
    public DNARecombinatorRenderer(BlockEntityRendererProvider.Context context) {
        super(new DNARecombinatorModel());
    }
}
