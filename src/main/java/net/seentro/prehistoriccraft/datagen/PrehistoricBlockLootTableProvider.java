package net.seentro.prehistoriccraft.datagen;

import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.seentro.prehistoriccraft.common.nature.dawnRedwood.DawnRedwoodSaplingBlock;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;

import java.util.Set;

public class PrehistoricBlockLootTableProvider extends BlockLootSubProvider {
    protected PrehistoricBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        /* NATURE */

        //DAWN REDWOOD
        dropSelf(PrehistoricBlocks.DAWN_REDWOOD_LOG.get());
        dropSelf(PrehistoricBlocks.DAWN_REDWOOD_WOOD.get());
        this.add(PrehistoricBlocks.DAWN_REDWOOD_CONES.get(), block ->
                createShearsOnlyDropOtherItem(block, PrehistoricItems.DAWN_REDWOOD_CONE));
        dropSelf(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_LOG.get());
        dropSelf(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_WOOD.get());
        dropSelf(PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get());
        dropSelf(PrehistoricBlocks.DAWN_REDWOOD_STAIRS.get());
        dropSelf(PrehistoricBlocks.DAWN_REDWOOD_SLAB.get());
        dropSelf(PrehistoricBlocks.DAWN_REDWOOD_FENCE.get());
        dropSelf(PrehistoricBlocks.DAWN_REDWOOD_FENCE_GATE.get());
        this.add(PrehistoricBlocks.DAWN_REDWOOD_DOOR.get(), this::createDoorTable);
        dropSelf(PrehistoricBlocks.DAWN_REDWOOD_TRAPDOOR.get());
        dropSelf(PrehistoricBlocks.DAWN_REDWOOD_PRESSURE_PLATE.get());
        dropSelf(PrehistoricBlocks.DAWN_REDWOOD_BUTTON.get());

        this.add(PrehistoricBlocks.DAWN_REDWOOD_SAPLING.get(), this::createInvisibleDrop);
        this.add(PrehistoricBlocks.POTTED_DAWN_REDWOOD_SAPLING.get(), createPotFlowerItemTable(PrehistoricBlocks.POTTED_DAWN_REDWOOD_SAPLING.get()));

        this.add(PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get(), block ->
                createLeavesDrops(block, PrehistoricBlocks.DAWN_REDWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(PrehistoricBlocks.DAWN_REDWOOD_SIGN.get(), block ->
                createSingleItemTable(PrehistoricItems.DAWN_REDWOOD_SIGN.get()));
        this.add(PrehistoricBlocks.DAWN_REDWOOD_WALL_SIGN.get(), block ->
                createSingleItemTable(PrehistoricItems.DAWN_REDWOOD_SIGN.get()));
        this.add(PrehistoricBlocks.DAWN_REDWOOD_HANGING_SIGN.get(), block ->
                createSingleItemTable(PrehistoricItems.DAWN_REDWOOD_HANGING_SIGN.get()));
        this.add(PrehistoricBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN.get(), block ->
                createSingleItemTable(PrehistoricItems.DAWN_REDWOOD_HANGING_SIGN.get()));

        /* FOSSILIFEROUS STONE */
        dropOther(PrehistoricBlocks.PRECAMBRIAN_FOSSILIFEROUS_STONE.get(), Blocks.DEEPSLATE);
        dropOther(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE.get(), Blocks.DEEPSLATE);
        dropOther(PrehistoricBlocks.ORDOVICIAN_FOSSILIFEROUS_STONE.get(), Blocks.DEEPSLATE);
        dropOther(PrehistoricBlocks.SILURIAN_FOSSILIFEROUS_STONE.get(), Blocks.DEEPSLATE);
        dropOther(PrehistoricBlocks.DEVONIAN_FOSSILIFEROUS_STONE.get(), Blocks.DEEPSLATE);
        dropOther(PrehistoricBlocks.CARBONIFEROUS_FOSSILIFEROUS_STONE.get(), Blocks.DEEPSLATE);
        dropOther(PrehistoricBlocks.PERMIAN_FOSSILIFEROUS_STONE.get(), Blocks.STONE);
        dropOther(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE.get(), Blocks.STONE);
        dropOther(PrehistoricBlocks.JURASSIC_FOSSILIFEROUS_STONE.get(), Blocks.STONE);
        dropOther(PrehistoricBlocks.CRETACEOUS_FOSSILIFEROUS_STONE.get(), Blocks.STONE);
        dropOther(PrehistoricBlocks.PALEOGENE_FOSSILIFEROUS_STONE.get(), Blocks.STONE);
        dropOther(PrehistoricBlocks.NEOGENE_FOSSILIFEROUS_STONE.get(), Blocks.STONE);

        /* PLASTERED FOSSILIFEROUS STONE */
        dropFossilToolMatching(PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE.get(), PrehistoricItems.PRECAMBRIAN_FOSSIL.get());
        dropFossilToolMatching(PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE.get(), PrehistoricItems.CAMBRIAN_FOSSIL.get());
        dropFossilToolMatching(PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE.get(), PrehistoricItems.ORDOVICIAN_FOSSIL.get());
        dropFossilToolMatching(PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE.get(), PrehistoricItems.SILURIAN_FOSSIL.get());
        dropFossilToolMatching(PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE.get(), PrehistoricItems.DEVONIAN_FOSSIL.get());
        dropFossilToolMatching(PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE.get(), PrehistoricItems.CARBONIFEROUS_FOSSIL.get());
        dropFossilToolMatching(PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE.get(), PrehistoricItems.PERMIAN_FOSSIL.get());
        dropFossilToolMatching(PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE.get(), PrehistoricItems.TRIASSIC_FOSSIL.get());
        dropFossilToolMatching(PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE.get(), PrehistoricItems.JURASSIC_FOSSIL.get());
        dropFossilToolMatching(PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE.get(), PrehistoricItems.CRETACEOUS_FOSSIL.get());
        dropFossilToolMatching(PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE.get(), PrehistoricItems.PALEOGENE_FOSSIL.get());
        dropFossilToolMatching(PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE.get(), PrehistoricItems.NEOGENE_FOSSIL.get());

        /* ORES */
        dropSelf(PrehistoricBlocks.AMBER_BLOCK.get());
        this.add(PrehistoricBlocks.DEEPSLATE_AMBER_ORE.get(), block ->
                createMultipleOreDrop(block, PrehistoricItems.AMBER.get(), 1, 3));
        dropSelf(PrehistoricBlocks.SULFUR_ORE.get());
        this.add(PrehistoricBlocks.DEEPSLATE_SULFUR_ORE.get(), block ->
                createMultipleOreDrop(block, PrehistoricItems.SULFUR.get(), 1, 3));


        /* BLOCK ENTITIES */
        dropSelf(PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get());
        dropSelf(PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get());
        this.add(PrehistoricBlocks.ACID_CLEANING_CHAMBER.get(), this::createDoorTable);
        this.add(PrehistoricBlocks.DNA_SEPARATION_FILTER.get(), this::createDoorTable);
        dropSelf(PrehistoricBlocks.DNA_RECOMBINATOR.get());

        /* GYPSUM */
        dropMultipleItems(PrehistoricBlocks.GYPSUM_CRYSTAL.get(), PrehistoricItems.GYPSUM_POWDER.get(), 1, 2);
        dropMultipleItems(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK.get(), PrehistoricItems.GYPSUM_POWDER.get(), 1, 2);

        /* CRACKED DIRT */
        dropSelf(PrehistoricBlocks.CRACKED_DIRT.get());
        dropSelf(PrehistoricBlocks.LOAM.get());
        dropSelf(PrehistoricBlocks.SILT.get());
        dropSelf(PrehistoricBlocks.LOAMY_SILT.get());
        dropSelf(PrehistoricBlocks.LOAMY_SAND.get());
        dropSelf(PrehistoricBlocks.SANDY_LOAM.get());
        dropSelf(PrehistoricBlocks.PEAT.get());
        dropOther(PrehistoricBlocks.LOAM_GRASS.get(), PrehistoricBlocks.LOAM.get());
        dropMultipleItems(PrehistoricBlocks.RAW_CLAY.get(), PrehistoricItems.RAW_CLAY_BALL.get(), 1, 3);
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

    protected LootTable.Builder createMatchingToolNumberedDrop(Block block, ItemLike item, ItemLike tool, float minDrops, float maxDrops) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(item)
                                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(tool)))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(block)))));
    }

    protected void dropOtherToolMatching(Block block, ItemLike item, ItemLike tool, float minDrops, float maxDrops) {
        this.add(block, this.createMatchingToolNumberedDrop(block, item, tool, minDrops, maxDrops));
    }

    protected void dropFossilToolMatching(Block block, ItemLike item) {
        this.add(block, this.createMatchingToolNumberedDrop(block, item, PrehistoricItems.EXCAVATOR_PICKAXE.get(), 1, 3));
    }

    protected LootTable.Builder createInvisibleDrop(Block block) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DawnRedwoodSaplingBlock.INVISIBLE, false))
                                ).add(LootItem.lootTableItem(block))
                );
    }

    protected LootTable.Builder createShearsOnlyDropOtherItem(Block block, ItemLike drop) {
        return this.createShearsDispatchTable(
                block,
                this.applyExplosionDecay(
                        block,
                        LootItem.lootTableItem(drop))
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return PrehistoricBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
