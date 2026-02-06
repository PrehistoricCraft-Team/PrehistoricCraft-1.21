package net.seentro.prehistoriccraft.common.nature.neocalamites.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.seentro.prehistoriccraft.common.block.acidCleaningChamber.item.AcidCleaningChamberBlockItemRenderer;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class NeocalamitesBlockItemRenderer extends GeoItemRenderer<NeocalamitesBlockItem> {
    public NeocalamitesBlockItemRenderer() {
        super(new NeocalamitesBlockItemModel());
    }
}
