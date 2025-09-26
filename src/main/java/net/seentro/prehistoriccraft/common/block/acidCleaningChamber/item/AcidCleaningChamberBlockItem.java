package net.seentro.prehistoriccraft.common.block.acidCleaningChamber.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class AcidCleaningChamberBlockItem extends BlockItem implements GeoItem {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public AcidCleaningChamberBlockItem(Properties properties) {
        super(PrehistoricBlocks.ACID_CLEANING_CHAMBER.get(), properties);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private AcidCleaningChamberBlockItemRenderer renderer;

            @Override
            public @Nullable BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new AcidCleaningChamberBlockItemRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
