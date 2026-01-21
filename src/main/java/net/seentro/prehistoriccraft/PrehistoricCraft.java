package net.seentro.prehistoriccraft;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.seentro.prehistoriccraft.common.block.acidCleaningChamber.geckolib.AcidCleaningChamberRenderer;
import net.seentro.prehistoriccraft.common.block.dnaRecombinator.DNARecombinatorBlockEntity;
import net.seentro.prehistoriccraft.common.block.dnaRecombinator.geckolib.DNARecombinatorRenderer;
import net.seentro.prehistoriccraft.common.block.dnaSeparationFilter.DNASeparationFilterBlock;
import net.seentro.prehistoriccraft.common.block.dnaSeparationFilter.DNASeparationFilterBlockEntity;
import net.seentro.prehistoriccraft.common.block.dnaSeparationFilter.geckolib.DNASeparationFilterRenderer;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.TissueExtractionChamberBlockEntity;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.geckolib.TissueExtractionChamberRenderer;
import net.seentro.prehistoriccraft.common.entity.PrehistoricBoatRenderer;
import net.seentro.prehistoriccraft.common.fluid.BaseFluidType;
import net.seentro.prehistoriccraft.common.fluid.FluidBottleWrapper;
import net.seentro.prehistoriccraft.common.nature.dawnRedwood.geckolib.DawnRedwoodSaplingRenderer;
import net.seentro.prehistoriccraft.common.screen.acidCleaningChamber.AcidCleaningChamberScreen;
import net.seentro.prehistoriccraft.common.screen.dnaRecombinator.DNARecombinatorScreen;
import net.seentro.prehistoriccraft.common.screen.dnaSeparationFilter.DNASeparationFilterScreen;
import net.seentro.prehistoriccraft.common.screen.fossilAnalysisTable.FossilAnalysisTableScreen;
import net.seentro.prehistoriccraft.common.screen.tissueExtractionChamber.TissueExtractionChamberScreen;
import net.seentro.prehistoriccraft.core.json.tissueExtractionChamber.TimePeriodTissueLoader;
import net.seentro.prehistoriccraft.data.FossilSpeciesLoader;
import net.seentro.prehistoriccraft.entity.dinosaur.PrehistoricDinosaurEntityTypes;
import net.seentro.prehistoriccraft.entity.dinosaur.water.dayongaspis.DayongaspisEntity;
import net.seentro.prehistoriccraft.entity.dinosaur.water.dayongaspis.DayongaspisRenderer;
import net.seentro.prehistoriccraft.registry.*;
import org.slf4j.Logger;
import software.bernie.geckolib.util.ClientUtil;

@Mod(PrehistoricCraft.MODID)
public class PrehistoricCraft {
    public static final String MODID = "prehistoriccraft";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PrehistoricCraft(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::blockColorRegistrationEvent);
        modEventBus.addListener(this::itemColorRegistrationEvent);
        modEventBus.addListener(this::registerCapabilities);

        PrehistoricBlocks.register(modEventBus);
        PrehistoricItems.register(modEventBus);
        PrehistoricCreativeTabs.register(modEventBus);
        PrehistoricDataComponents.register(modEventBus);
        PrehistoricBlockEntityTypes.register(modEventBus);
        PrehistoricMenuTypes.register(modEventBus);
        PrehistoricFluidTypes.register(modEventBus);
        PrehistoricFluids.register(modEventBus);
        PrehistoricFeatures.register(modEventBus);
        PrehistoricFoliagePlacerTypes.register(modEventBus);
        PrehistoricTrunkPlacerTypes.register(modEventBus);
        PrehistoricDinosaurEntityTypes.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidBottleWrapper(stack),
                PrehistoricItems.BOTTLE_OF_BLICE.get()
        );

        event.registerBlock(
                Capabilities.ItemHandler.BLOCK,
                (level, pos, state, be, side) -> {
                    if (!state.is(PrehistoricBlocks.DNA_SEPARATION_FILTER.get())) {
                        return null;
                    }

                    BlockPos bottomPos = state.getValue(DNASeparationFilterBlock.HALF) == DoubleBlockHalf.UPPER
                            ? pos.below()
                            : pos;

                    BlockEntity realBe = level.getBlockEntity(bottomPos);
                    if (realBe instanceof DNASeparationFilterBlockEntity filter) {
                        return filter.getHopperItemHandler(side);
                    }

                    return null;
                },
                PrehistoricBlocks.DNA_SEPARATION_FILTER.get()
        );

        event.registerBlock(
                Capabilities.ItemHandler.BLOCK,
                (level, pos, state, be, side) -> {
                    if (!state.is(PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get())) {
                        return null;
                    }

                    BlockEntity realBe = level.getBlockEntity(pos);
                    if (realBe instanceof TissueExtractionChamberBlockEntity chamber) {
                        return chamber.getHopperItemHandler(side);
                    }

                    return null;
                },
                PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get()
        );

        event.registerBlock(
                Capabilities.ItemHandler.BLOCK,
                (level, pos, state, be, side) -> {
                    BlockEntity realBe = level.getBlockEntity(pos);
                    if (realBe instanceof DNARecombinatorBlockEntity chamber)
                        return chamber.getHopperItemHandler(side);

                    return null;
                },
                PrehistoricBlocks.DNA_RECOMBINATOR.get()
        );
    }

    private void itemColorRegistrationEvent(RegisterColorHandlersEvent.Item itemEvent) {
        itemEvent.register(
                (itemStack, color) -> {
                    BlockState blockstate = ((BlockItem)itemStack.getItem()).getBlock().defaultBlockState();
                    return itemEvent.getBlockColors().getColor(blockstate, null, null, color);
                },
                PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get(),
                PrehistoricBlocks.DAWN_REDWOOD_CONES.get(),
                PrehistoricBlocks.LOAM_GRASS.get()
        );
    }

    private void blockColorRegistrationEvent(RegisterColorHandlersEvent.Block blockEvent) {
        blockEvent.register((state, tintGetter, pos, color) -> tintGetter != null && pos != null
                        ? getDawnRedwoodFoliageColor(pos)
                        : getDawnRedwoodDefaultColor(),
                PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get(),
                PrehistoricBlocks.DAWN_REDWOOD_CONES.get()
        );

        blockEvent.register((state, tintGetter, pos, color) -> tintGetter != null && pos != null
                        ? BiomeColors.getAverageFoliageColor(tintGetter, pos)
                        : FoliageColor.getDefaultColor(),
                PrehistoricBlocks.DAWN_REDWOOD_SAPLING.get()
        );

        blockEvent.register((state, tintGetter, pos, color) -> tintGetter != null && pos != null
                        ? BiomeColors.getAverageGrassColor(tintGetter, pos)
                        : GrassColor.getDefaultColor(),
                PrehistoricBlocks.LOAM_GRASS.get()
        );
    }

    private static int getDawnRedwoodDefaultColor() {
        return -4325567;
    }

    private int getDawnRedwoodFoliageColor(BlockPos pos) {
        Holder<Biome> biome = ClientUtil.getLevel().getBiome(pos);
        if (biome.is(Tags.Biomes.IS_FOREST))
            return 7793716;

        if (biome.is(Tags.Biomes.IS_DRY_OVERWORLD))
            return 14216539;

        if (biome.is(Tags.Biomes.IS_SWAMP))
            return 13232762;

        if (biome.is(Tags.Biomes.IS_JUNGLE))
            return 5832486;

        if (biome.is(Tags.Biomes.IS_MOUNTAIN))
            return 4250938;

        if (biome.is(Tags.Biomes.IS_MUSHROOM))
            return 13368477;

        return 12451649;
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(PrehistoricBlocks.DAWN_REDWOOD_SAPLING.getId(), PrehistoricBlocks.POTTED_DAWN_REDWOOD_SAPLING);
        });
    }

    @SubscribeEvent
    public void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new TimePeriodTissueLoader());
        event.addListener(FossilSpeciesLoader.INSTANCE);
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            BlockEntityRenderers.register(PrehistoricBlockEntityTypes.TISSUE_EXTRACTION_CHAMBER_BLOCK_ENTITY.get(), TissueExtractionChamberRenderer::new);
            BlockEntityRenderers.register(PrehistoricBlockEntityTypes.ACID_CLEANING_CHAMBER_BLOCK_ENTITY.get(), AcidCleaningChamberRenderer::new);
            BlockEntityRenderers.register(PrehistoricBlockEntityTypes.DAWN_REDWOOD_SAPLING_BLOCK_ENTITY.get(), DawnRedwoodSaplingRenderer::new);
            BlockEntityRenderers.register(PrehistoricBlockEntityTypes.DNA_SEPARATION_FILTER_BLOCK_ENTITY.get(), DNASeparationFilterRenderer::new);
            BlockEntityRenderers.register(PrehistoricBlockEntityTypes.DNA_RECOMBINATOR_BLOCK_ENTITY.get(), DNARecombinatorRenderer::new);

            EntityRenderers.register(PrehistoricDinosaurEntityTypes.DAYONGASPIS.get(), DayongaspisRenderer::new);

            event.enqueueWork(() -> {
                Sheets.addWoodType(PrehistoricWoodTypes.DAWN_REDWOOD);
                ItemProperties.register(
                    PrehistoricItems.DNA_IN_A_PETRI_DISH.get(),
                    ResourceLocation.fromNamespaceAndPath(
                            PrehistoricCraft.MODID,
                            "contaminated"
                    ),
                    (stack, level, entity, seed) -> {
                        boolean contaminated = stack.getOrDefault(
                                PrehistoricDataComponents.DNA_CONTAMINATED.get(),
                                false
                        );
                        return contaminated ? 1.0F : 0.0F;
                    }
                );
            });
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(PrehistoricMenuTypes.FOSSIL_ANALYSIS_TABLE_MENU.get(), FossilAnalysisTableScreen::new);
            event.register(PrehistoricMenuTypes.TISSUE_EXTRACTION_CHAMBER_MENU.get(), TissueExtractionChamberScreen::new);
            event.register(PrehistoricMenuTypes.ACID_CLEANING_CHAMBER_MENU.get(), AcidCleaningChamberScreen::new);
            event.register(PrehistoricMenuTypes.DNA_SEPARATION_FILTER_MENU.get(), DNASeparationFilterScreen::new);
            event.register(PrehistoricMenuTypes.DNA_RECOMBINATOR_MENU.get(), DNARecombinatorScreen::new);
        }

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(PrehistoricBlockEntityTypes.PREHISTORIC_SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(PrehistoricBlockEntityTypes.PREHISTORIC_HANGING_SIGN.get(), HangingSignRenderer::new);

            event.registerEntityRenderer(EntityType.BOAT, context -> new PrehistoricBoatRenderer(context, false));
        }

        @SubscribeEvent
        public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerFluidType(((BaseFluidType) PrehistoricFluidTypes.BLICE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    PrehistoricFluidTypes.BLICE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) PrehistoricFluidTypes.ACID_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    PrehistoricFluidTypes.ACID_FLUID_TYPE.get());
        }
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(PrehistoricDinosaurEntityTypes.DAYONGASPIS.get(), DayongaspisEntity.createAttributes().build());
        }
    }
}
