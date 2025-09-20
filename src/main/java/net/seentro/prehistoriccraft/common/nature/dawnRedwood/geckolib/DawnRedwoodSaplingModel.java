package net.seentro.prehistoriccraft.common.nature.dawnRedwood.geckolib;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.nature.dawnRedwood.DawnRedwoodSaplingBlockEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class DawnRedwoodSaplingModel extends GeoModel<DawnRedwoodSaplingBlockEntity> {
    @Override
    public ResourceLocation getModelResource(DawnRedwoodSaplingBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/dawn_redwood_sapling_3.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DawnRedwoodSaplingBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/dawn_redwood_sapling_3_leaves.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DawnRedwoodSaplingBlockEntity animatable) {
        return null;
    }
}
