package net.seentro.prehistoriccraft.common.block.nature.simplePlants.kerpia.geckolib;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.nature.simplePlants.kerpia.KerpiaBlockEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class KerpiaModel extends GeoModel<KerpiaBlockEntity> {
    @Override
    public ResourceLocation getModelResource(KerpiaBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/nature/kerpia.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KerpiaBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/kerpia.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KerpiaBlockEntity animatable) {
        return null;
    }
}
