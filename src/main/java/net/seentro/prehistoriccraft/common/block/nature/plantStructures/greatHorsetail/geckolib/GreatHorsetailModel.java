package net.seentro.prehistoriccraft.common.block.nature.plantStructures.greatHorsetail.geckolib;

import net.minecraft.resources.ResourceLocation;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.nature.plantStructures.greatHorsetail.GreatHorsetailBlock;
import net.seentro.prehistoriccraft.common.block.nature.plantStructures.greatHorsetail.GreatHorsetailBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class GreatHorsetailModel extends GeoModel<GreatHorsetailBlockEntity> {
    @Override
    public ResourceLocation getModelResource(GreatHorsetailBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "geo/block/templates/20x32_plant.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GreatHorsetailBlockEntity animatable) {
        return switch(animatable.getBlockState().getValue(GreatHorsetailBlock.VARIANT)) {
            case 2 -> ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/great_horsetail_2.png");
            case 3 -> ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/great_horsetail_3.png");
            default -> ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/block/great_horsetail_1.png");
        };
    }

    @Override
    public ResourceLocation getAnimationResource(GreatHorsetailBlockEntity animatable) {
        return null;
    }
}
