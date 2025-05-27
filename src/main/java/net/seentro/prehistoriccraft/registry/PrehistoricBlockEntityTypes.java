package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.fossilAnalysisTable.FossilAnalysisTableBlockEntity;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.TissueExtractionChamberBlockEntity;

import java.util.Set;
import java.util.function.Supplier;

public class PrehistoricBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, PrehistoricCraft.MODID);

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

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
