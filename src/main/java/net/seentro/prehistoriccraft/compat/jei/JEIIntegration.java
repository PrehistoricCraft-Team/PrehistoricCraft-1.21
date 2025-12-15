package net.seentro.prehistoriccraft.compat.jei;

import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.IRecipesGui;
import net.seentro.prehistoriccraft.compat.jei.guides.acidCleaningChamber.AcidCleaningChamberGuideCategory;
import net.seentro.prehistoriccraft.compat.jei.guides.dnaRecombinator.DNARecombinatorGuideCategory;
import net.seentro.prehistoriccraft.compat.jei.guides.dnaSeperationFilter.DNASeparationFilterGuideCategory;
import net.seentro.prehistoriccraft.compat.jei.guides.tissueExtractionChamber.TissueExtractionChamberGuideCategory;

import java.util.List;

public class JEIIntegration {

    private static IJeiRuntime runtime;

    public static void setRuntime(IJeiRuntime r) {
        runtime = r;
    }

    public static void showAcidCleaningChamberGuide() {
        if (runtime == null) {
            return;
        }

        IRecipesGui gui = runtime.getRecipesGui();

        gui.showTypes(List.of(AcidCleaningChamberGuideCategory.RECIPE_TYPE));
    }

    public static void showTissueExtractionChamberGuide() {
        if (runtime == null) {
            return;
        }

        IRecipesGui gui = runtime.getRecipesGui();

        gui.showTypes(List.of(TissueExtractionChamberGuideCategory.RECIPE_TYPE));
    }

    public static void showDnaSeparatorGuide() {
        if (runtime == null) {
            return;
        }

        IRecipesGui gui = runtime.getRecipesGui();

        gui.showTypes(List.of(DNASeparationFilterGuideCategory.RECIPE_TYPE));
    }

    public static void showDnaRecombinatorGuide() {
        if (runtime == null) {
            return;
        }

        IRecipesGui gui = runtime.getRecipesGui();

        gui.showTypes(List.of(DNARecombinatorGuideCategory.RECIPE_TYPE));
    }
}
