package net.seentro.prehistoriccraft.common.block.acidCleaningChamber.geckolib;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.acidCleaningChamber.AcidCleaningChamberBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class AcidCleaningChamberModel extends GeoModel<AcidCleaningChamberBlockEntity> {
    @Override
    public ResourceLocation getModelResource(AcidCleaningChamberBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/acid_cleaning_chamber.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AcidCleaningChamberBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/acid_cleaning_chamber.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AcidCleaningChamberBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "animations/acid_cleaning_chamber.animation.json");
    }
}
