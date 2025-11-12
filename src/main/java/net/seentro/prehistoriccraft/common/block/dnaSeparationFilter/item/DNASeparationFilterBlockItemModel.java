package net.seentro.prehistoriccraft.common.block.dnaSeparationFilter.item;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import software.bernie.geckolib.model.GeoModel;

public class DNASeparationFilterBlockItemModel extends GeoModel<DNASeparationFilterBlockItem> {
    @Override
    public ResourceLocation getModelResource(DNASeparationFilterBlockItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/machine/dna_separation_filter.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DNASeparationFilterBlockItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/dna_separation_filter.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DNASeparationFilterBlockItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "animations/block/machine/dna_separation_filter_animations.json");
    }
}
