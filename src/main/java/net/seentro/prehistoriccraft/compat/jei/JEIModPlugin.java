package net.seentro.prehistoriccraft.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.compat.jei.guides.dnaSeperationFilter.DNASeparationFilterGuideCategory;
import net.seentro.prehistoriccraft.compat.jei.guides.dnaSeperationFilter.DNASeperationFilterGuideRecipe;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.compat.jei.guides.tissueExtractionChamber.TissueExtractionChamberGuideRecipe;
import net.seentro.prehistoriccraft.compat.jei.guides.tissueExtractionChamber.TissueExtractionChamberGuideCategory;

import java.util.List;

@JeiPlugin
public class JEIModPlugin implements IModPlugin {

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(
            PrehistoricCraft.MODID, "jei_plugin"
    );

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new DNASeparationFilterGuideCategory(registration.getJeiHelpers().getGuiHelper()),
                new TissueExtractionChamberGuideCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(
                TissueExtractionChamberGuideCategory.RECIPE_TYPE,
                List.of(TissueExtractionChamberGuideRecipe.INSTANCE)
        );

        registration.addRecipes(
                DNASeparationFilterGuideCategory.RECIPE_TYPE,
                List.of(DNASeperationFilterGuideRecipe.INSTANCE)
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get()),
                TissueExtractionChamberGuideCategory.RECIPE_TYPE
        );

        registration.addRecipeCatalyst(
                new ItemStack(PrehistoricBlocks.DNA_SEPARATION_FILTER.get()),
                DNASeparationFilterGuideCategory.RECIPE_TYPE
        );
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JEIIntegration.setRuntime(jeiRuntime);
    }
}
