package net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.geckolib;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.TissueExtractionChamberBlockEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.DefaultedGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class TissueExtractionChamberModel extends GeoModel<TissueExtractionChamberBlockEntity> {
    @Override
    public ResourceLocation getModelResource(TissueExtractionChamberBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/machine/tissue_extraction_chamber.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TissueExtractionChamberBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/tissue_extraction_chamber.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TissueExtractionChamberBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "animations/block/machine/tissue_extraction_chamber.animation.json");
    }
}
