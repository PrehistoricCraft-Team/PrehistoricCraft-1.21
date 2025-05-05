package net.seentro.prehistoriccraft.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;

import java.util.Set;

public class PrehistoricBlockLootTableProvider extends BlockLootSubProvider {
    protected PrehistoricBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        /* FOSSILIFEROUS STONE */
        dropOther(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE.get(), Blocks.DEEPSLATE);
        dropOther(PrehistoricBlocks.CARBONIFEROUS_FOSSILIFEROUS_STONE.get(), Blocks.DEEPSLATE);
        dropOther(PrehistoricBlocks.CRETACEOUS_FOSSILIFEROUS_STONE.get(), Blocks.STONE);
        dropOther(PrehistoricBlocks.DEVONIAN_FOSSILIFEROUS_STONE.get(), Blocks.DEEPSLATE);
        dropOther(PrehistoricBlocks.JURASSIC_FOSSILIFEROUS_STONE.get(), Blocks.STONE);
        dropOther(PrehistoricBlocks.NEOGENE_FOSSILIFEROUS_STONE.get(), Blocks.STONE);
        dropOther(PrehistoricBlocks.ORDOVICIAN_FOSSILIFEROUS_STONE.get(), Blocks.DEEPSLATE);
        dropOther(PrehistoricBlocks.PALEOGENE_FOSSILIFEROUS_STONE.get(), Blocks.STONE);
        dropOther(PrehistoricBlocks.PERMIAN_FOSSILIFEROUS_STONE.get(), Blocks.STONE);
        dropOther(PrehistoricBlocks.PRECAMBRIAN_FOSSILIFEROUS_STONE.get(), Blocks.DEEPSLATE);
        dropOther(PrehistoricBlocks.SILURIAN_FOSSILIFEROUS_STONE.get(), Blocks.DEEPSLATE);
        dropOther(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE.get(), Blocks.STONE);

        /* PLASTERED FOSSILIFEROUS STONE */
        /*dropMultipleItemsWithToolRequirement(PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE.get(),
                PrehistoricItems.EXCAVATOR_PICKAXE.get(), PrehistoricItems.CAMBRIAN_FOSSIL.get(), 2, 4);
         */
        dropMultipleItemsWithToolRequirement(PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE.get(),
                PrehistoricItems.EXCAVATOR_PICKAXE.get(), PrehistoricItems.CARBONIFEROUS_FOSSIL.get(), 2, 4);
        dropMultipleItemsWithToolRequirement(PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE.get(),
                PrehistoricItems.EXCAVATOR_PICKAXE.get(),  PrehistoricItems.CRETACEOUS_FOSSIL.get(), 2, 4);
        dropMultipleItemsWithToolRequirement(PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE.get(),
                PrehistoricItems.EXCAVATOR_PICKAXE.get(),  PrehistoricItems.DEVONIAN_FOSSIL.get(), 2, 4);
        dropMultipleItemsWithToolRequirement(PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE.get(),
                PrehistoricItems.EXCAVATOR_PICKAXE.get(),  PrehistoricItems.JURASSIC_FOSSIL.get(), 2, 4);
        dropMultipleItemsWithToolRequirement(PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE.get(),
                PrehistoricItems.EXCAVATOR_PICKAXE.get(),  PrehistoricItems.NEOGENE_FOSSIL.get(), 2, 4);
        dropMultipleItemsWithToolRequirement(PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE.get(),
                PrehistoricItems.EXCAVATOR_PICKAXE.get(),  PrehistoricItems.ORDOVICIAN_FOSSIL.get(), 2, 4);
        dropMultipleItemsWithToolRequirement(PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE.get(),
                PrehistoricItems.EXCAVATOR_PICKAXE.get(),  PrehistoricItems.PALEOGENE_FOSSIL.get(), 2, 4);
        dropMultipleItemsWithToolRequirement(PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE.get(),
                PrehistoricItems.EXCAVATOR_PICKAXE.get(),  PrehistoricItems.PERMIAN_FOSSIL.get(), 2, 4);
        dropMultipleItemsWithToolRequirement(PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE.get(),
                PrehistoricItems.EXCAVATOR_PICKAXE.get(),  PrehistoricItems.PRECAMBRIAN_FOSSIL.get(), 2, 4);
        dropMultipleItemsWithToolRequirement(PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE.get(),
                PrehistoricItems.EXCAVATOR_PICKAXE.get(),  PrehistoricItems.SILURIAN_FOSSIL.get(), 2, 4);
        dropMultipleItemsWithToolRequirement(PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE.get(),
                PrehistoricItems.EXCAVATOR_PICKAXE.get(),  PrehistoricItems.TRIASSIC_FOSSIL.get(), 2, 4);

        /* AMBER */
        dropSelf(PrehistoricBlocks.AMBER_BLOCK.get());
    }

    /* HELPER METHODS */

    protected LootTable.Builder createMultipleOreDrop(Block block, ItemLike item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block, LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    protected void dropMultipleItems(Block block, ItemLike item, float minDrops, float maxDrops) {
        this.add(block, this.createMultipleOreDrop(block, item, minDrops, maxDrops));
    }

    protected LootTable.Builder createMultipleOreDropWithToolRequirement(Block block, Item requiredTool, ItemLike dropItem, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> enchantmentRegistry = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        LootPoolEntryContainer.Builder<?> fallbackEntry = this.applyExplosionDecay(block,
                        LootItem.lootTableItem(dropItem)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .apply(ApplyBonusCount.addOreBonusCount(enchantmentRegistry.getOrThrow(Enchantments.FORTUNE)))
                )
                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(requiredTool)));

        return this.createSilkTouchDispatchTable(block, fallbackEntry);
    }

    protected void dropMultipleItemsWithToolRequirement(Block block, Item tool, ItemLike item, float minDrops, float maxDrops) {
        this.add(block, this.createMultipleOreDropWithToolRequirement(block, tool, item, minDrops, maxDrops));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return PrehistoricBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
