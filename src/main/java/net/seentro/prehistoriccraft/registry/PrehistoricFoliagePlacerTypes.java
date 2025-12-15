package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.worldgen.tree.DawnRedwoodFoliagePlacer;

import java.util.function.Supplier;

public class PrehistoricFoliagePlacerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES =
            DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE, PrehistoricCraft.MODID);

    public static final Supplier<FoliagePlacerType<DawnRedwoodFoliagePlacer>> DAWN_REDWOOD_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("dawn_redwood_foliage_placer", () -> new FoliagePlacerType<>(DawnRedwoodFoliagePlacer.CODEC));

    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACER_TYPES.register(eventBus);
    }
}
