package net.seentro.prehistoriccraft.common.block.dnaRecombinator.item;

import software.bernie.geckolib.renderer.GeoItemRenderer;

public class DNARecombinatorBlockItemRenderer extends GeoItemRenderer<DNARecombinatorBlockItem> {
    public DNARecombinatorBlockItemRenderer() {
        super(new DNARecombinatorBlockItemModel());
    }
}
