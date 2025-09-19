package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.InvisibleBlockEntity;
import net.seentro.prehistoriccraft.common.block.acidCleaningChamber.AcidCleaningChamberBlockEntity;
import net.seentro.prehistoriccraft.common.block.fossilAnalysisTable.FossilAnalysisTableBlockEntity;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.TissueExtractionChamberBlockEntity;
import net.seentro.prehistoriccraft.common.nature.signs.PrehistoricHangingSignBlockEntity;
import net.seentro.prehistoriccraft.common.nature.signs.PrehistoricSignBlockEntity;
import net.seentro.prehistoriccraft.common.nature.signs.PrehistoricStandingSignBlock;
import net.seentro.prehistoriccraft.core.multiblock.InvisibleMultiblockCreatorBlockEntity;

import java.util.Set;
import java.util.function.Supplier;

public class PrehistoricBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PrehistoricCraft.MODID);

    public static final Supplier<BlockEntityType<FossilAnalysisTableBlockEntity>> FOSSIL_ANALYSIS_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("fossil_analysis_table_block_entity",
            () -> BlockEntityType.Builder.of(
                    FossilAnalysisTableBlockEntity::new, PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get())
                    .build(null));

    public static final Supplier<BlockEntityType<TissueExtractionChamberBlockEntity>> TISSUE_EXTRACTION_CHAMBER_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("tissue_extraction_chamber_block_entity",
                    () -> BlockEntityType.Builder.of(
                                    TissueExtractionChamberBlockEntity::new, PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get())
                            .build(null));

    public static final Supplier<BlockEntityType<AcidCleaningChamberBlockEntity>> ACID_CLEANING_CHAMBER_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("acid_cleaning_chamber_block_entity",
                    () -> BlockEntityType.Builder.of(
                                    AcidCleaningChamberBlockEntity::new, PrehistoricBlocks.ACID_CLEANING_CHAMBER.get())
                            .build(null));

    // NATURE

    public static final Supplier<BlockEntityType<PrehistoricSignBlockEntity>> PREHISTORIC_SIGN =
            BLOCK_ENTITY_TYPES.register("prehistoric_sign",
                    () -> BlockEntityType.Builder.of(
                                    PrehistoricSignBlockEntity::new, PrehistoricBlocks.DAWN_REDWOOD_SIGN.get(), PrehistoricBlocks.DAWN_REDWOOD_WALL_SIGN.get())
                            .build(null));

    public static final Supplier<BlockEntityType<PrehistoricHangingSignBlockEntity>> PREHISTORIC_HANGING_SIGN =
            BLOCK_ENTITY_TYPES.register("prehistoric_hanging_sign",
                    () -> BlockEntityType.Builder.of(
                                    PrehistoricHangingSignBlockEntity::new, PrehistoricBlocks.DAWN_REDWOOD_HANGING_SIGN.get(), PrehistoricBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN.get())
                            .build(null));

    // OTHER
    public static final Supplier<BlockEntityType<InvisibleMultiblockCreatorBlockEntity>> INVISIBLE_MULTIBLOCK_CREATOR_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("invisible_multiblock_creator_block_entity",
                    () -> BlockEntityType.Builder.of(
                                    InvisibleMultiblockCreatorBlockEntity::new, PrehistoricBlocks.DAWN_REDWOOD_SAPLING_STAGE_3.get())
                            .build(null));

    // OTHER
    public static final Supplier<BlockEntityType<InvisibleBlockEntity>> INVISIBLE_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("invisible_block_entity",
                    () -> BlockEntityType.Builder.of(
                                    InvisibleBlockEntity::new, PrehistoricBlocks.INVISIBLE_BLOCK.get())
                            .build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
