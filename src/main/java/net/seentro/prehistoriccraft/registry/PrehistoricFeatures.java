package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.worldgen.features.DawnRedwoodBigTreeFeature;
import net.seentro.prehistoriccraft.worldgen.features.DawnRedwoodTreeFeature;

import java.util.function.Supplier;

public class PrehistoricFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, PrehistoricCraft.MODID);

    public static final Supplier<Feature<NoneFeatureConfiguration>> DAWN_REDWOOD_FEATURE = FEATURES.register("dawn_redwood", () -> new DawnRedwoodTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> DAWN_REDWOOD_BIG_FEATURE = FEATURES.register("dawn_redwood_big", () -> new DawnRedwoodBigTreeFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
