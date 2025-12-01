package net.seentro.prehistoriccraft.common.block.dnaRecombinator.item;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import software.bernie.geckolib.model.GeoModel;

public class DNARecombinatorBlockItemModel extends GeoModel<DNARecombinatorBlockItem> {
    @Override
    public ResourceLocation getModelResource(DNARecombinatorBlockItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/machine/dna_recombinator.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DNARecombinatorBlockItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/dna_recombinator.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DNARecombinatorBlockItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "animations/block/machine/dna_recombinator.animation.json");
    }
}
