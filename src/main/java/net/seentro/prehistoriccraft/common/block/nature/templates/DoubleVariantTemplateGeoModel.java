package net.seentro.prehistoriccraft.common.block.nature.templates;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.woodHorsetail.WoodHorsetailBlock;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class DoubleVariantTemplateGeoModel<T extends BlockEntity & GeoAnimatable> extends GeoModel<T> {
    private final String textureLocation;
    private final String textureLocationSecondVariant;
    private final String templateLocation;
    public DoubleVariantTemplateGeoModel(String textureLocation, String textureLocationSecondVariant, String templateLocation) {
        this.textureLocation = textureLocation;
        this.textureLocationSecondVariant = textureLocationSecondVariant;
        this.templateLocation = templateLocation;
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, templateLocation);
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        if (animatable.getBlockState().getValue(WoodHorsetailBlock.VARIANT) == 2) {
            return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, textureLocationSecondVariant);
        }

        return ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, textureLocation);
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return null;
    }
}
