package net.seentro.prehistoriccraft;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
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
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.seentro.prehistoriccraft.common.block.acidCleaningChamber.geckolib.AcidCleaningChamberRenderer;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.geckolib.TissueExtractionChamberRenderer;
import net.seentro.prehistoriccraft.common.entity.PrehistoricBoatRenderer;
import net.seentro.prehistoriccraft.common.fluid.BaseFluidType;
import net.seentro.prehistoriccraft.common.fluid.FluidBottleWrapper;
import net.seentro.prehistoriccraft.common.nature.dawnRedwood.geckolib.DawnRedwoodSaplingRenderer;
import net.seentro.prehistoriccraft.common.screen.acidCleaningChamber.AcidCleaningChamberScreen;
import net.seentro.prehistoriccraft.common.screen.fossilAnalysisTable.FossilAnalysisTableScreen;
import net.seentro.prehistoriccraft.common.screen.tissueExtractionChamber.TissueExtractionChamberScreen;
import net.seentro.prehistoriccraft.core.json.tissueExtractionChamber.TimePeriodTissueLoader;
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

        NeoForge.EVENT_BUS.register(this);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> new FluidBottleWrapper(stack),
                PrehistoricItems.BOTTLE_OF_BLICE.get());
    }

    private void itemColorRegistrationEvent(RegisterColorHandlersEvent.Item itemEvent) {
        itemEvent.register(
                (itemStack, color) -> {
                    BlockState blockstate = ((BlockItem)itemStack.getItem()).getBlock().defaultBlockState();
                    return itemEvent.getBlockColors().getColor(blockstate, null, null, color);
                },
                PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get()
        );
    }

    private void blockColorRegistrationEvent(RegisterColorHandlersEvent.Block blockEvent) {
        // DAWN REDWOOD
        blockEvent.register((state, tintGetter, pos, color) -> tintGetter != null && pos != null
                        ? getCustomFoliageColor(pos)
                        : FoliageColor.getDefaultColor(),
                PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get()
        );

        // VANILLA
        blockEvent.register((state, tintGetter, pos, color) -> tintGetter != null && pos != null
                        ? BiomeColors.getAverageFoliageColor(tintGetter, pos)
                        : FoliageColor.getDefaultColor(),
                PrehistoricBlocks.DAWN_REDWOOD_SAPLING.get()
        );
    }

    //Returns decimal color values
    private int getCustomFoliageColor(BlockPos pos) {
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
    public void onServerStarting(ServerStartingEvent event) {
    }

    @SubscribeEvent
    public void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new TimePeriodTissueLoader());
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            BlockEntityRenderers.register(PrehistoricBlockEntityTypes.TISSUE_EXTRACTION_CHAMBER_BLOCK_ENTITY.get(), TissueExtractionChamberRenderer::new);
            BlockEntityRenderers.register(PrehistoricBlockEntityTypes.ACID_CLEANING_CHAMBER_BLOCK_ENTITY.get(), AcidCleaningChamberRenderer::new);
            BlockEntityRenderers.register(PrehistoricBlockEntityTypes.DAWN_REDWOOD_SAPLING_BLOCK_ENTITY.get(), DawnRedwoodSaplingRenderer::new);

            event.enqueueWork(() -> {
                Sheets.addWoodType(PrehistoricWoodTypes.DAWN_REDWOOD);
            });
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(PrehistoricMenuTypes.FOSSIL_ANALYSIS_TABLE_MENU.get(), FossilAnalysisTableScreen::new);
            event.register(PrehistoricMenuTypes.TISSUE_EXTRACTION_CHAMBER_MENU.get(), TissueExtractionChamberScreen::new);
            event.register(PrehistoricMenuTypes.ACID_CLEANING_CHAMBER_MENU.get(), AcidCleaningChamberScreen::new);
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
}
