package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.seentro.prehistoriccraft.entity.dinosaur.PrehistoricDinosaurEntityTypes;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;

import java.util.stream.Stream;

public class PrehistoricEntityLootTableProvider extends EntityLootSubProvider {
    protected PrehistoricEntityLootTableProvider(HolderLookup.Provider registries) {
        super(FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return PrehistoricDinosaurEntityTypes.DINOSAUR_TYPES.getEntries().stream().map(DeferredHolder::value);
    }

    @Override
    public void generate() {
        add(PrehistoricDinosaurEntityTypes.DAYONGASPIS.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(PrehistoricItems.RAW_DAYONGASPIS).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.BONE_MEAL))
                                        .when(LootItemRandomChanceCondition.randomChance(0.05F))
                        )
        );
    }
}
