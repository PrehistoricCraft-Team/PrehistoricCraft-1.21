package net.seentro.prehistoriccraft.common.block.acidCleaningChamber.item;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import software.bernie.geckolib.model.GeoModel;

public class AcidCleaningChamberBlockItemModel extends GeoModel<AcidCleaningChamberBlockItem> {
    @Override
    public ResourceLocation getModelResource(AcidCleaningChamberBlockItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/machine/acid_cleaning_chamber.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AcidCleaningChamberBlockItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/acid_cleaning_chamber.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AcidCleaningChamberBlockItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "animations/block/machine/acid_cleaning_chamber.animation.json");
    }
}
