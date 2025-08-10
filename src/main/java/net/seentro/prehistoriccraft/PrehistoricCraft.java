package net.seentro.prehistoriccraft;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.geckolib.TissueExtractionChamberRenderer;
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

@Mod(PrehistoricCraft.MODID)
public class PrehistoricCraft {
    public static final String MODID = "prehistoriccraft";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PrehistoricCraft(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        PrehistoricBlocks.register(modEventBus);
        PrehistoricItems.register(modEventBus);
        PrehistoricCreativeTabs.register(modEventBus);
        PrehistoricDataComponents.register(modEventBus);
        PrehistoricBlockEntityTypes.register(modEventBus);
        PrehistoricMenuTypes.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
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
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(PrehistoricMenuTypes.FOSSIL_ANALYSIS_TABLE_MENU.get(), FossilAnalysisTableScreen::new);
            event.register(PrehistoricMenuTypes.TISSUE_EXTRACTION_CHAMBER_MENU.get(), TissueExtractionChamberScreen::new);
        }
    }
}
