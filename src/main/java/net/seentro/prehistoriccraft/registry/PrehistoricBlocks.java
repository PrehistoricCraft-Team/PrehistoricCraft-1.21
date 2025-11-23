package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.LoamGrassBlock;
import net.seentro.prehistoriccraft.common.nature.*;
import net.seentro.prehistoriccraft.common.block.acidCleaningChamber.AcidCleaningChamberBlock;
import net.seentro.prehistoriccraft.common.block.dnaSeparationFilter.DNASeparationFilterBlock;
import net.seentro.prehistoriccraft.common.block.fossilAnalysisTable.FossilAnalysisTableBlock;
import net.seentro.prehistoriccraft.common.block.fossiliferousStone.FossiliferousStoneBlock;
import net.seentro.prehistoriccraft.common.block.gypsumCrystal.GypsumCrystalBlock;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.TissueExtractionChamberBlock;
import net.seentro.prehistoriccraft.common.nature.signs.PrehistoricHangingSignBlock;
import net.seentro.prehistoriccraft.common.nature.signs.PrehistoricStandingSignBlock;
import net.seentro.prehistoriccraft.common.nature.signs.PrehistoricWallHangingSignBlock;
import net.seentro.prehistoriccraft.common.nature.signs.PrehistoricWallSignBlock;

import java.util.function.Supplier;

import static net.seentro.prehistoriccraft.core.FossilTypes.*;

public class PrehistoricBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(PrehistoricCraft.MODID);

    /* NATURE */

    //DAWN REDWOOD
    public static final DeferredBlock<Block> DAWN_REDWOOD_LOG = registerBlock("dawn_redwood_log", () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<Block> DAWN_REDWOOD_WOOD = registerBlock("dawn_redwood_wood", () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<Block> STRIPPED_DAWN_REDWOOD_LOG = registerBlock("stripped_dawn_redwood_log", () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final DeferredBlock<Block> STRIPPED_DAWN_REDWOOD_WOOD = registerBlock("stripped_dawn_redwood_wood", () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
    public static final DeferredBlock<Block> DAWN_REDWOOD_LEAVES = registerBlock("dawn_redwood_leaves", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final DeferredBlock<Block> DAWN_REDWOOD_CONES = registerBlock("dawn_redwood_cones", () -> new FlammableConeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).instabreak().noCollission()));
    public static final DeferredBlock<Block> DAWN_REDWOOD_PLANKS = registerBlock("dawn_redwood_planks", () -> new FlammableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<StairBlock> DAWN_REDWOOD_STAIRS = registerBlock("dawn_redwood_stairs", () -> new StairBlock(PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<SlabBlock> DAWN_REDWOOD_SLAB = registerBlock("dawn_redwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));
    public static final DeferredBlock<FenceBlock> DAWN_REDWOOD_FENCE = registerBlock("dawn_redwood_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<FenceGateBlock> DAWN_REDWOOD_FENCE_GATE = registerBlock("dawn_redwood_fence_gate", () -> new FenceGateBlock(PrehistoricWoodTypes.DAWN_REDWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));
    public static final DeferredBlock<DoorBlock> DAWN_REDWOOD_DOOR = registerBlock("dawn_redwood_door", () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).noOcclusion()));
    public static final DeferredBlock<TrapDoorBlock> DAWN_REDWOOD_TRAPDOOR = registerBlock("dawn_redwood_trapdoor", () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).noOcclusion()));
    public static final DeferredBlock<PressurePlateBlock> DAWN_REDWOOD_PRESSURE_PLATE = registerBlock("dawn_redwood_pressure_plate", () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
    public static final DeferredBlock<ButtonBlock> DAWN_REDWOOD_BUTTON = registerBlock("dawn_redwood_button", () -> new ButtonBlock(BlockSetType.OAK, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<PrehistoricStandingSignBlock> DAWN_REDWOOD_SIGN = registerBlockOnly("dawn_redwood_sign", () -> new PrehistoricStandingSignBlock(PrehistoricWoodTypes.DAWN_REDWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)));
    public static final DeferredBlock<PrehistoricWallSignBlock> DAWN_REDWOOD_WALL_SIGN = registerBlockOnly("dawn_redwood_wall_sign", () -> new PrehistoricWallSignBlock(PrehistoricWoodTypes.DAWN_REDWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)));
    public static final DeferredBlock<PrehistoricHangingSignBlock> DAWN_REDWOOD_HANGING_SIGN = registerBlockOnly("dawn_redwood_hanging_sign", () -> new PrehistoricHangingSignBlock(PrehistoricWoodTypes.DAWN_REDWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)));
    public static final DeferredBlock<PrehistoricWallHangingSignBlock> DAWN_REDWOOD_WALL_HANGING_SIGN = registerBlockOnly("dawn_redwood_wall_hanging_sign", () -> new PrehistoricWallHangingSignBlock(PrehistoricWoodTypes.DAWN_REDWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)));
    public static final DeferredBlock<Block> DAWN_REDWOOD_SAPLING = registerBlock("dawn_redwood_sapling", () -> new SaplingBlock(PrehistoricTreeGrowers.DAWN_REDWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> POTTED_DAWN_REDWOOD_SAPLING = registerBlockOnly("potted_dawn_redwood_sapling", () -> new FlowerPotBlock((() -> (FlowerPotBlock) Blocks.FLOWER_POT), DAWN_REDWOOD_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_OAK_SAPLING)));

    /* FOSSILIFEROUS STONE */
    public static final DeferredBlock<Block> PRECAMBRIAN_FOSSILIFEROUS_STONE = registerBlock("precambrian_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), PRECAMBRIAN));
    public static final DeferredBlock<Block> CAMBRIAN_FOSSILIFEROUS_STONE = registerBlock("cambrian_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), CAMBRIAN));
    public static final DeferredBlock<Block> ORDOVICIAN_FOSSILIFEROUS_STONE = registerBlock("ordovician_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), ORDOVICIAN));
    public static final DeferredBlock<Block> SILURIAN_FOSSILIFEROUS_STONE = registerBlock("silurian_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), SILURIAN));
    public static final DeferredBlock<Block> DEVONIAN_FOSSILIFEROUS_STONE = registerBlock("devonian_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), DEVONIAN));
    public static final DeferredBlock<Block> CARBONIFEROUS_FOSSILIFEROUS_STONE = registerBlock("carboniferous_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), CARBONIFEROUS));
    public static final DeferredBlock<Block> PERMIAN_FOSSILIFEROUS_STONE = registerBlock("permian_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), PERMIAN));
    public static final DeferredBlock<Block> TRIASSIC_FOSSILIFEROUS_STONE = registerBlock("triassic_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), TRIASSIC));
    public static final DeferredBlock<Block> JURASSIC_FOSSILIFEROUS_STONE = registerBlock("jurassic_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), JURASSIC));
    public static final DeferredBlock<Block> CRETACEOUS_FOSSILIFEROUS_STONE = registerBlock("cretaceous_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), CRETACEOUS));
    public static final DeferredBlock<Block> PALEOGENE_FOSSILIFEROUS_STONE = registerBlock("paleogene_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), PALEOGENE));
    public static final DeferredBlock<Block> NEOGENE_FOSSILIFEROUS_STONE = registerBlock("neogene_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), NEOGENE));

    /* PLASTERED FOSSILIFEROUS STONE */
    public static final DeferredBlock<Block> PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE = registerBlock("plastered_precambrian_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE = registerBlock("plastered_cambrian_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE = registerBlock("plastered_ordovician_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_SILURIAN_FOSSILIFEROUS_STONE = registerBlock("plastered_silurian_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE = registerBlock("plastered_devonian_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE = registerBlock("plastered_carboniferous_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_PERMIAN_FOSSILIFEROUS_STONE = registerBlock("plastered_permian_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE = registerBlock("plastered_triassic_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_JURASSIC_FOSSILIFEROUS_STONE = registerBlock("plastered_jurassic_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE = registerBlock("plastered_cretaceous_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE = registerBlock("plastered_paleogene_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_NEOGENE_FOSSILIFEROUS_STONE = registerBlock("plastered_neogene_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    /* ORES */
    public static final DeferredBlock<Block> AMBER_BLOCK = registerBlock("amber_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DEEPSLATE_AMBER_ORE = registerBlock("deepslate_amber_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SULFUR_ORE = registerBlock("sulfur_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.LAPIS_ORE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DEEPSLATE_SULFUR_ORE = registerBlock("deepslate_sulfur_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_LAPIS_ORE).requiresCorrectToolForDrops()));

    /* BLOCK ENTITIES */
    public static final DeferredBlock<Block> FOSSIL_ANALYSIS_TABLE = registerBlock("fossil_analysis_table", () -> new FossilAnalysisTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion()));
    public static final DeferredBlock<Block> TISSUE_EXTRACTION_CHAMBER = registerBlockOnly("tissue_extraction_chamber", () -> new TissueExtractionChamberBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE).noOcclusion()));
    public static final DeferredBlock<Block> ACID_CLEANING_CHAMBER = registerBlockOnly("acid_cleaning_chamber", () -> new AcidCleaningChamberBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE).noOcclusion()));
    public static final DeferredBlock<Block> DNA_SEPARATION_FILTER = registerBlockOnly("dna_separation_filter", () -> new DNASeparationFilterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE).noOcclusion()));

    /* GYPSUM & PLASTER */
    public static final DeferredBlock<Block> GYPSUM_CRYSTAL = registerBlock("gypsum_crystal", () -> new GypsumCrystalBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).noOcclusion().strength(1.5F).pushReaction(PushReaction.DESTROY).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> GYPSUM_CRYSTAL_BLOCK = registerBlock("gypsum_crystal_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).requiresCorrectToolForDrops()));

    /* DIRT */
    public static final DeferredBlock<Block> CRACKED_DIRT = registerBlock("cracked_dirt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT).strength(1.1F)));
    public static final DeferredBlock<Block> PEAT = registerBlock("peat", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).strength(1.1F, 0.5F).sound((SoundType.ROOTED_DIRT))));
    public static final DeferredBlock<Block> LOAM = registerBlock("loam", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> SILT = registerBlock("silt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final DeferredBlock<Block> LOAMY_SILT = registerBlock("loamy_silt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT)));
    public static final DeferredBlock<Block> SANDY_LOAM = registerBlock("sandy_loam", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final DeferredBlock<Block> LOAMY_SAND = registerBlock("loamy_sand", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)));
    public static final DeferredBlock<Block> RAW_CLAY = registerBlock("raw_clay", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CLAY).sound(SoundType.MUD)));
    public static final DeferredBlock<Block> LOAM_GRASS = registerBlock("loam_grass_block", () -> new LoamGrassBlock(PrehistoricBlocks.LOAM.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)));


    private static <T extends Block> DeferredBlock<T> registerBlockOnly(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        PrehistoricItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
