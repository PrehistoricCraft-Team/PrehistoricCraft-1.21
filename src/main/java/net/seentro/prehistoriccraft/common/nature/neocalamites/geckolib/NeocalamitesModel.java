package net.seentro.prehistoriccraft.common.nature.neocalamites.geckolib;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.nature.neocalamites.NeocalamitesBlock;
import net.seentro.prehistoriccraft.common.nature.neocalamites.NeocalamitesBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class NeocalamitesModel extends GeoModel<NeocalamitesBlockEntity> {
    @Override
    public ResourceLocation getModelResource(NeocalamitesBlockEntity animatable) {
        // Check if it's the top, middle or base block
        if (animatable.getBlockState().getValue(NeocalamitesBlock.IS_TOP))
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/nature/neocalamites/neocalamites_top.geo.json");

        if (animatable.getBlockState().getValue(NeocalamitesBlock.IS_MIDDLE))
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/nature/neocalamites/neocalamites_middle.geo.json");

        if (animatable.getBlockState().getValue(NeocalamitesBlock.IS_BASE))
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/nature/neocalamites/neocalamites_base.geo.json");

        // If not return stem
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/nature/neocalamites/neocalamites_stem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NeocalamitesBlockEntity animatable) {
        // Check if it's the top, middle or base block
        if (animatable.getBlockState().getValue(NeocalamitesBlock.IS_TOP))
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/neocalamites_top.png");

        if (animatable.getBlockState().getValue(NeocalamitesBlock.IS_MIDDLE))
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/neocalamites_middle.png");

        if (animatable.getBlockState().getValue(NeocalamitesBlock.IS_BASE))
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/neocalamites_base.png");

        // If not return stem
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/neocalamites_stem.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NeocalamitesBlockEntity animatable) {
        return null;
    }
}
