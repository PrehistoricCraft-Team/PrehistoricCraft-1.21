package net.seentro.prehistoriccraft.common.nature.dawnRedwood.geckolib;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.nature.dawnRedwood.DawnRedwoodSaplingBlockEntity;
import net.seentro.prehistoriccraft.common.nature.plantStructures.ThreeStageFlowerPlantStructure;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("deprecation")
public class DawnRedwoodSaplingModel extends GeoModel<DawnRedwoodSaplingBlockEntity> {
    @Override
    public ResourceLocation getModelResource(DawnRedwoodSaplingBlockEntity animatable) {
        return switch (animatable.getBlockState().getValue(ThreeStageFlowerPlantStructure.STAGES)) {
            case 2 -> ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/nature/dawn_redwood_sapling_2.geo.json");
            case 3 -> ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/nature/dawn_redwood_sapling_3.geo.json");
            default -> ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/nature/dawn_redwood_sapling.geo.json");
        };
    }

    @Override
    public ResourceLocation getTextureResource(DawnRedwoodSaplingBlockEntity animatable) {
        return switch (animatable.getBlockState().getValue(ThreeStageFlowerPlantStructure.STAGES)) {
            case 2 -> ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/dawn_redwood_sapling_2_leaves.png");
            case 3 -> ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/dawn_redwood_sapling_3_leaves.png");
            default -> ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/dawn_redwood_sapling_leaves.png");
        };
    }

    @Override
    public ResourceLocation getAnimationResource(DawnRedwoodSaplingBlockEntity animatable) {
        return null;
    }
}
