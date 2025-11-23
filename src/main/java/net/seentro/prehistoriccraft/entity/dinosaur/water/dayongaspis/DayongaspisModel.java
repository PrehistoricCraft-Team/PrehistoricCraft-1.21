package net.seentro.prehistoriccraft.entity.dinosaur.water.dayongaspis;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import software.bernie.geckolib.model.GeoModel;

public class DayongaspisModel extends GeoModel<DayongaspisEntity> {
    @Override
    public ResourceLocation getModelResource(DayongaspisEntity dayongaspisEntity) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/dinosaur/water/dayongaspis.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DayongaspisEntity dayongaspisEntity) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/dinosaur/water/dayongaspis.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DayongaspisEntity dayongaspisEntity) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "animations/dinosaur/water/dayongaspis.animation.json");
    }
}
