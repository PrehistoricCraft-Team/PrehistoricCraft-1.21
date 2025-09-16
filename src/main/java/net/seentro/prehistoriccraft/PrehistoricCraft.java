package net.seentro.prehistoriccraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.seentro.prehistoriccraft.common.block.acidCleaningChamber.geckolib.AcidCleaningChamberRenderer;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.geckolib.TissueExtractionChamberRenderer;
import net.seentro.prehistoriccraft.common.entity.PrehistoricBoatRenderer;
import net.seentro.prehistoriccraft.common.screen.acidCleaningChamber.AcidCleaningChamberScreen;
import net.seentro.prehistoriccraft.common.screen.fossilAnalysisTable.FossilAnalysisTableScreen;
import net.seentro.prehistoriccraft.common.screen.tissueExtractionChamber.TissueExtractionChamberScreen;
import net.seentro.prehistoriccraft.core.json.tissueExtractionChamber.TimePeriodTissueLoader;
import net.seentro.prehistoriccraft.registry.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import javax.swing.text.html.parser.Entity;

@Mod(PrehistoricCraft.MODID)
public class PrehistoricCraft {
    public static final String MODID = "prehistoriccraft";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PrehistoricCraft(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::blockColorRegistrationEvent);
        modEventBus.addListener(this::itemColorRegistrationEvent);

        PrehistoricBlocks.register(modEventBus);
        PrehistoricItems.register(modEventBus);
        PrehistoricCreativeTabs.register(modEventBus);
        PrehistoricDataComponents.register(modEventBus);
        PrehistoricBlockEntityTypes.register(modEventBus);
        PrehistoricMenuTypes.register(modEventBus);
        //PrehistoricEntityTypes.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
    }

    private void itemColorRegistrationEvent(RegisterColorHandlersEvent.Item itemEvent) {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();

        itemEvent.register(
                (itemStack, color) -> {
                    BlockState blockstate = ((BlockItem)itemStack.getItem()).getBlock().defaultBlockState();
                    return blockColors.getColor(blockstate, null, null, color);
                },
                PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get()
        );
    }

    private void blockColorRegistrationEvent(RegisterColorHandlersEvent.Block blockEvent) {
        blockEvent.register((state, tintGetter, pos, color) -> tintGetter != null && pos != null
                        ? BiomeColors.getAverageFoliageColor(tintGetter, pos)
                        : FoliageColor.getDefaultColor(),
                PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get()
        );
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
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
    }
}
