package net.seentro.prehistoriccraft.common.block.dnaSeparationFilter.geckolib;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.dnaSeparationFilter.DNASeparationFilterBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class DNASeparationFilterModel extends GeoModel<DNASeparationFilterBlockEntity> {
    @Override
    public ResourceLocation getModelResource(DNASeparationFilterBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/machine/dna_separation_filter.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DNASeparationFilterBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/dna_separation_filter.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DNASeparationFilterBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "animations/block/machine/dna_separation_filter_animation.json");
    }
}
