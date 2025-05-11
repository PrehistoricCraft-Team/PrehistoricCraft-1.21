package net.seentro.prehistoriccraft;

import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.neoforge.client.RenderTypeHelper;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.model.renderable.ITextureRenderTypeLookup;
import net.seentro.prehistoriccraft.common.screen.fossilAnalysisTable.FossilAnalysisTableMenu;
import net.seentro.prehistoriccraft.common.screen.fossilAnalysisTable.FossilAnalysisTableScreen;
import net.seentro.prehistoriccraft.registry.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

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

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(PrehistoricMenuTypes.FOSSIL_ANALYSIS_TABLE_MENU.get(), FossilAnalysisTableScreen::new);
        }
    }
}
