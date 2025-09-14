package net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.item;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import software.bernie.geckolib.model.GeoModel;

public class TissueExtractionChamberBlockItemModel extends GeoModel<TissueExtractionChamberBlockItem> {
    @Override
    public ResourceLocation getModelResource(TissueExtractionChamberBlockItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/tissue_extraction_chamber.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TissueExtractionChamberBlockItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/tissue_extraction_chamber.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TissueExtractionChamberBlockItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "animations/tissue_extraction_chamber.animation.json");
    }
}
