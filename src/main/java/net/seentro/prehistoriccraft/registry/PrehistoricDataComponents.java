package net.seentro.prehistoriccraft.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;

import java.util.function.UnaryOperator;

public class PrehistoricDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, PrehistoricCraft.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> FOSSIL_QUALITY = register("fossil_quality",
            builder -> builder.persistent(Codec.STRING));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> FOSSIL_SPECIES = register("fossil_species",
            builder -> builder.persistent(Codec.STRING));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> TISSUE_SIZE = register("tissue_size",
            builder -> builder.persistent(Codec.STRING));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> DNA_SPECIES = register("dna_species",
            builder -> builder.persistent(Codec.STRING));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> DNA_QUALITY = register("dna_quality",
            builder -> builder.persistent(Codec.INT));
    
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> DNA_CONTAMINATED = register("dna_contaminated",
            builder -> builder.persistent(Codec.BOOL));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> DNA_SOURCE_TYPE = register("dna_source_type",
            builder -> builder.persistent(Codec.STRING));

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
