package net.seentro.prehistoriccraft.entity.dinosaur;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.entity.dinosaur.water.dayongaspis.DayongaspisEntity;

import java.util.function.Supplier;

public class PrehistoricDinosaurEntityTypes {
    public static final DeferredRegister<EntityType<?>> DINOSAUR_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, PrehistoricCraft.MODID);

    public static final Supplier<EntityType<DayongaspisEntity>> DAYONGASPIS = DINOSAUR_TYPES.register(
            "dayongaspis",
            () -> EntityType.Builder.of(DayongaspisEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.4f, 0.2f)
                    .build("dayongaspis"));

    public static void register (IEventBus eventBus) {
        DINOSAUR_TYPES.register(eventBus);
    }
}
