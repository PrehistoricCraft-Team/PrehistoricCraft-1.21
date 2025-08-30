package net.seentro.prehistoriccraft.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.fossilAnalysisTable.FossilAnalysisTableBlock;
import net.seentro.prehistoriccraft.common.block.fossiliferousStone.FossiliferousStoneBlock;
import net.seentro.prehistoriccraft.common.block.gypsumCrystal.GypsumCrystalBlock;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.TissueExtractionChamberBlock;

import java.util.function.Supplier;

import static net.seentro.prehistoriccraft.core.FossilTypes.*;

public class PrehistoricBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(PrehistoricCraft.MODID);

    /* FOSSILIFEROUS STONE */
    public static final DeferredBlock<Block> CAMBRIAN_FOSSILIFEROUS_STONE = registerBlock("cambrian_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), CAMBRIAN));
    public static final DeferredBlock<Block> CARBONIFEROUS_FOSSILIFEROUS_STONE = registerBlock("carboniferous_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), CARBONIFEROUS));
    public static final DeferredBlock<Block> CRETACEOUS_FOSSILIFEROUS_STONE = registerBlock("cretaceous_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), CRETACEOUS));
    public static final DeferredBlock<Block> DEVONIAN_FOSSILIFEROUS_STONE = registerBlock("devonian_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), DEVONIAN));
    public static final DeferredBlock<Block> JURASSIC_FOSSILIFEROUS_STONE = registerBlock("jurassic_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), JURASSIC));
    public static final DeferredBlock<Block> NEOGENE_FOSSILIFEROUS_STONE = registerBlock("neogene_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), NEOGENE));
    public static final DeferredBlock<Block> ORDOVICIAN_FOSSILIFEROUS_STONE = registerBlock("ordovician_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), ORDOVICIAN));
    public static final DeferredBlock<Block> PALEOGENE_FOSSILIFEROUS_STONE = registerBlock("paleogene_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), PALEOGENE));
    public static final DeferredBlock<Block> PERMIAN_FOSSILIFEROUS_STONE = registerBlock("permian_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), PERMIAN));
    public static final DeferredBlock<Block> PRECAMBRIAN_FOSSILIFEROUS_STONE = registerBlock("precambrian_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), PRECAMBRIAN));
    public static final DeferredBlock<Block> SILURIAN_FOSSILIFEROUS_STONE = registerBlock("silurian_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), SILURIAN));
    public static final DeferredBlock<Block> TRIASSIC_FOSSILIFEROUS_STONE = registerBlock("triassic_fossiliferous_stone", () -> new FossiliferousStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), TRIASSIC));

    /* PLASTERED FOSSILIFEROUS STONE */
    public static final DeferredBlock<Block> PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE = registerBlock("plastered_cambrian_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE = registerBlock("plastered_carboniferous_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE = registerBlock("plastered_cretaceous_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE = registerBlock("plastered_devonian_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_JURASSIC_FOSSILIFEROUS_STONE = registerBlock("plastered_jurassic_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_NEOGENE_FOSSILIFEROUS_STONE = registerBlock("plastered_neogene_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE = registerBlock("plastered_ordovician_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE = registerBlock("plastered_paleogene_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_PERMIAN_FOSSILIFEROUS_STONE = registerBlock("plastered_permian_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE = registerBlock("plastered_precambrian_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_SILURIAN_FOSSILIFEROUS_STONE = registerBlock("plastered_silurian_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE = registerBlock("plastered_triassic_fossiliferous_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    /* ORES */
    public static final DeferredBlock<Block> AMBER_BLOCK = registerBlock("amber_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DEEPSLATE_AMBER_ORE = registerBlock("deepslate_amber_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SULFUR_ORE = registerBlock("sulfur_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.LAPIS_ORE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DEEPSLATE_SULFUR_ORE = registerBlock("deepslate_sulfur_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_LAPIS_ORE).requiresCorrectToolForDrops()));

    /* BLOCK ENTITIES */
    public static final DeferredBlock<Block> FOSSIL_ANALYSIS_TABLE = registerBlock("fossil_analysis_table", () -> new FossilAnalysisTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion()));
    public static final DeferredBlock<Block> TISSUE_EXTRACTION_CHAMBER = registerBlock("tissue_extraction_chamber", () -> new TissueExtractionChamberBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE).noOcclusion()));

    /* GYPSUM & PLASTER */
    public static final DeferredBlock<Block> GYPSUM_CRYSTAL = registerBlock("gypsum_crystal", () -> new GypsumCrystalBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).noOcclusion().strength(1.5F).pushReaction(PushReaction.DESTROY).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> GYPSUM_CRYSTAL_BLOCK = registerBlock("gypsum_crystal_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).requiresCorrectToolForDrops()));

    /* DIRT */
    public static final DeferredBlock<Block> CRACKED_DIRT = registerBlock("cracked_dirt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT).strength(1.1F)));

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
