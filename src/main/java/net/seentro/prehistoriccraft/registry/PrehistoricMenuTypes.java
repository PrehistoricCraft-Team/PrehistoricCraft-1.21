package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.screen.dnaRecombinator.DNARecombinatorMenu;
import net.seentro.prehistoriccraft.common.screen.dnaSeparationFilter.DNASeparationFilterMenu;
import net.seentro.prehistoriccraft.common.screen.acidCleaningChamber.AcidCleaningChamberMenu;
import net.seentro.prehistoriccraft.common.screen.fossilAnalysisTable.FossilAnalysisTableMenu;
import net.seentro.prehistoriccraft.common.screen.tissueExtractionChamber.TissueExtractionChamberMenu;

public class PrehistoricMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(BuiltInRegistries.MENU, PrehistoricCraft.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<FossilAnalysisTableMenu>> FOSSIL_ANALYSIS_TABLE_MENU = registerMenuType("fossil_analysis_table_menu", FossilAnalysisTableMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<TissueExtractionChamberMenu>> TISSUE_EXTRACTION_CHAMBER_MENU = registerMenuType("tissue_extraction_chamber_menu", TissueExtractionChamberMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<AcidCleaningChamberMenu>> ACID_CLEANING_CHAMBER_MENU = registerMenuType("acid_cleaning_chamber_menu", AcidCleaningChamberMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<DNASeparationFilterMenu>> DNA_SEPARATION_FILTER_MENU = registerMenuType("dna_filter_menu", DNASeparationFilterMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<DNARecombinatorMenu>> DNA_RECOMBINATOR_MENU = registerMenuType("dna_recombinator_menu", DNARecombinatorMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENU_TYPES.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
