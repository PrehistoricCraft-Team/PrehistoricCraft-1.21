package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.machines.acidCleaningChamber.AcidCleaningChamberBlockEntity;
import net.seentro.prehistoriccraft.common.block.machines.dnaRecombinator.DNARecombinatorBlockEntity;
import net.seentro.prehistoriccraft.common.block.machines.dnaSeparationFilter.DNASeparationFilterBlockEntity;
import net.seentro.prehistoriccraft.common.block.machines.fossilAnalysisTable.FossilAnalysisTableBlockEntity;
import net.seentro.prehistoriccraft.common.block.machines.tissueExtractionChamber.TissueExtractionChamberBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.plantStructures.dawnRedwood.DawnRedwoodSaplingBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.plantStructures.neocalamites.NeocalamitesBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.signs.PrehistoricHangingSignBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.signs.PrehistoricSignBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.aridHorsetail.AridHorsetailBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.marshHorsetail.MarshHorsetailBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.roughHorsetail.RoughHorsetailBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.woodHorsetail.WoodHorsetailBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.simplePlants.kerpia.KerpiaBlockEntity;

import java.util.Arrays;
import java.util.function.Supplier;

public class PrehistoricBlockEntityTypes {
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister
                        .create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PrehistoricCraft.MODID);

        // MACHINES

        public static final Supplier<BlockEntityType<FossilAnalysisTableBlockEntity>> FOSSIL_ANALYSIS_TABLE_BLOCK_ENTITY =
                createBlockEntity("fossil_analysis_table", FossilAnalysisTableBlockEntity::new, PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE);

        public static final Supplier<BlockEntityType<TissueExtractionChamberBlockEntity>> TISSUE_EXTRACTION_CHAMBER_BLOCK_ENTITY =
                createBlockEntity("tissue_extraction_chamber", TissueExtractionChamberBlockEntity::new, PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER);

        public static final Supplier<BlockEntityType<AcidCleaningChamberBlockEntity>> ACID_CLEANING_CHAMBER_BLOCK_ENTITY =
                createBlockEntity("acid_cleaning_chamber", AcidCleaningChamberBlockEntity::new, PrehistoricBlocks.ACID_CLEANING_CHAMBER);

        public static final Supplier<BlockEntityType<DNASeparationFilterBlockEntity>> DNA_SEPARATION_FILTER_BLOCK_ENTITY =
                createBlockEntity("dna_separation_filter", DNASeparationFilterBlockEntity::new, PrehistoricBlocks.DNA_SEPARATION_FILTER);

        public static final Supplier<BlockEntityType<DNARecombinatorBlockEntity>> DNA_RECOMBINATOR_BLOCK_ENTITY =
                createBlockEntity("dna_recombinator", DNARecombinatorBlockEntity::new, PrehistoricBlocks.DNA_RECOMBINATOR);

        // NATURE

        public static final Supplier<BlockEntityType<PrehistoricSignBlockEntity>> PREHISTORIC_SIGN_BLOCK_ENTITY =
                createBlockEntity("prehistoric_sign", PrehistoricSignBlockEntity::new,
                        PrehistoricBlocks.DAWN_REDWOOD_SIGN, PrehistoricBlocks.DAWN_REDWOOD_WALL_SIGN);

        public static final Supplier<BlockEntityType<PrehistoricHangingSignBlockEntity>> PREHISTORIC_HANGING_SIGN_BLOCK_ENTITY =
                createBlockEntity("prehistoric_hanging_sign", PrehistoricHangingSignBlockEntity::new,
                        PrehistoricBlocks.DAWN_REDWOOD_HANGING_SIGN, PrehistoricBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN);

        public static final Supplier<BlockEntityType<DawnRedwoodSaplingBlockEntity>> DAWN_REDWOOD_SAPLING_BLOCK_ENTITY =
                createBlockEntity("dawn_redwood_sapling", DawnRedwoodSaplingBlockEntity::new, PrehistoricBlocks.DAWN_REDWOOD_SAPLING);

        public static final Supplier<BlockEntityType<NeocalamitesBlockEntity>> NEOCALAMITES_BLOCK_ENTITY =
                createBlockEntity("neocalamites", NeocalamitesBlockEntity::new, PrehistoricBlocks.NEOCALAMITES);

        public static final Supplier<BlockEntityType<KerpiaBlockEntity>> KERPIA_BLOCK_ENTITY =
                createBlockEntity("kerpia", KerpiaBlockEntity::new, PrehistoricBlocks.KERPIA);

        public static final Supplier<BlockEntityType<WoodHorsetailBlockEntity>> WOOD_HORSETAIL_BLOCK_ENTITY =
                createBlockEntity("wood_horsetail", WoodHorsetailBlockEntity::new, PrehistoricBlocks.WOOD_HORSETAIL);

        public static final Supplier<BlockEntityType<AridHorsetailBlockEntity>> ARID_HORSETAIL_BLOCK_ENTITY =
                createBlockEntity("arid_horsetail", AridHorsetailBlockEntity::new, PrehistoricBlocks.ARID_HORSETAIL);

        public static final Supplier<BlockEntityType<RoughHorsetailBlockEntity>> ROUGH_HORSETAIL_BLOCK_ENTITY =
                createBlockEntity("rough_horsetail", RoughHorsetailBlockEntity::new, PrehistoricBlocks.ROUGH_HORSETAIL);

        public static final Supplier<BlockEntityType<MarshHorsetailBlockEntity>> MARSH_HORSETAIL_BLOCK_ENTITY =
                createBlockEntity("marsh_horsetail", MarshHorsetailBlockEntity::new, PrehistoricBlocks.MARSH_HORSETAIL);

        @SafeVarargs
        private static <T extends BlockEntity> Supplier<BlockEntityType<T>> createBlockEntity(String name, BlockEntityType.BlockEntitySupplier<T> blockEntity, DeferredBlock<? extends Block>... block) {
                return BLOCK_ENTITY_TYPES.register(name + "_block_entity", () -> BlockEntityType.Builder.of(blockEntity, Arrays.stream(block).map(DeferredBlock::get).toArray(Block[]::new)).build(null));
        }

        public static void register(IEventBus eventBus) {
                BLOCK_ENTITY_TYPES.register(eventBus);
        }
}
