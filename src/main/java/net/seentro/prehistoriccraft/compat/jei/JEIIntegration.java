package net.seentro.prehistoriccraft.compat.jei;

import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.IRecipesGui;
import net.seentro.prehistoriccraft.compat.jei.guides.tissueExtractionChamber.TissueExtractionChamberGuideCategory;

import java.util.List;

public class JEIIntegration {

    private static IJeiRuntime runtime;

    public static void setRuntime(IJeiRuntime r) {
        runtime = r;
    }

    public static void showDnaFilterGuide() {
        if (runtime == null) {
            return;
        }

        IRecipesGui gui = runtime.getRecipesGui();

        gui.showTypes(List.of(TissueExtractionChamberGuideCategory.RECIPE_TYPE));
    }
}
