package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.seentro.prehistoriccraft.PrehistoricCraft;

import java.util.function.Supplier;

public class PrehistoricEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, PrehistoricCraft.MODID);

    /*public static final Supplier<EntityType<PrehistoricBoatEntity>> PREHISTORIC_BOAT =
            ENTITY_TYPES.register("prehistoric_boat", () -> EntityType.Builder.<PrehistoricBoatEntity>of(PrehistoricBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("prehistoric_boat"));

    public static final Supplier<EntityType<PrehistoricChestBoatEntity>> PREHISTORIC_CHEST_BOAT =
            ENTITY_TYPES.register("prehistoric_chest_boat", () -> EntityType.Builder.<PrehistoricChestBoatEntity>of(PrehistoricChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("prehistoric_chest_boat"));

     */

    public static void register(IEventBus eventBus) {
        eventBus.register(ENTITY_TYPES);
    }
}
