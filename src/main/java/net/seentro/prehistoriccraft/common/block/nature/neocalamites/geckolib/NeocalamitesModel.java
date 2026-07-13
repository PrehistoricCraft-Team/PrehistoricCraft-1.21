package net.seentro.prehistoriccraft.common.block.nature.neocalamites.geckolib;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.nature.neocalamites.NeocalamitesBlock;
import net.seentro.prehistoriccraft.common.block.nature.neocalamites.NeocalamitesBlockEntity;
import net.seentro.prehistoriccraft.core.multiblock.QuadrupleInvisibleSegmentProperty;
import software.bernie.geckolib.model.GeoModel;

public class NeocalamitesModel extends GeoModel<NeocalamitesBlockEntity> {
    @Override
    public ResourceLocation getModelResource(NeocalamitesBlockEntity animatable) {
        // Check if it's a top, middle or base segment, otherwise return the stem
        if (animatable.getBlockState().getValue(NeocalamitesBlock.SEGMENT) == QuadrupleInvisibleSegmentProperty.TOP)
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/nature/neocalamites/neocalamites_top.geo.json");

        if (animatable.getBlockState().getValue(NeocalamitesBlock.SEGMENT) == QuadrupleInvisibleSegmentProperty.MIDDLE)
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/nature/neocalamites/neocalamites_middle.geo.json");

        if (animatable.getBlockState().getValue(NeocalamitesBlock.SEGMENT) == QuadrupleInvisibleSegmentProperty.BASE)
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/nature/neocalamites/neocalamites_base.geo.json");

        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/nature/neocalamites/neocalamites_stem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NeocalamitesBlockEntity animatable) {
        // Check if it's a top, middle or base segment, otherwise return the stem
        if (animatable.getBlockState().getValue(NeocalamitesBlock.SEGMENT) == QuadrupleInvisibleSegmentProperty.TOP)
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/neocalamites_top.png");

        if (animatable.getBlockState().getValue(NeocalamitesBlock.SEGMENT) == QuadrupleInvisibleSegmentProperty.MIDDLE)
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/neocalamites_middle.png");

        if (animatable.getBlockState().getValue(NeocalamitesBlock.SEGMENT) == QuadrupleInvisibleSegmentProperty.BASE)
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/neocalamites_base.png");


        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/neocalamites_stem.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NeocalamitesBlockEntity animatable) {
        return null;
    }
}
