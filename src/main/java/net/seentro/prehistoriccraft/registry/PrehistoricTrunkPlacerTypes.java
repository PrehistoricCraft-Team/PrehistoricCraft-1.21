package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.worldgen.tree.DawnRedwoodTrunkPlacer;

import java.util.function.Supplier;

public class PrehistoricTrunkPlacerTypes {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, PrehistoricCraft.MODID);

    public static final Supplier<TrunkPlacerType<DawnRedwoodTrunkPlacer>> DAWN_REDWOOD_TRUNK_PLACER =
        TRUNK_PLACERS.register(
                "dawn_redwood_trunk_placer",
                () -> new TrunkPlacerType<>(DawnRedwoodTrunkPlacer.CODEC)
        );

    public static void register(IEventBus bus) {
        TRUNK_PLACERS.register(bus);
    }
}
