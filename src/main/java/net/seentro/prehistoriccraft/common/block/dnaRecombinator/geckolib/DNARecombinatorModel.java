package net.seentro.prehistoriccraft.common.block.dnaRecombinator.geckolib;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.dnaRecombinator.DNARecombinatorBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class DNARecombinatorModel extends GeoModel<DNARecombinatorBlockEntity> {
    @Override
    public ResourceLocation getModelResource(DNARecombinatorBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/machine/dna_recombinator.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DNARecombinatorBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/dna_recombinator.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DNARecombinatorBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "animations/block/machine/dna_recombinator.animation.json");
    }
}
