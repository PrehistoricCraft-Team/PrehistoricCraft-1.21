package net.seentro.prehistoriccraft.utils.jei;

import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.IRecipesGui;
import net.seentro.prehistoriccraft.utils.jei.guides.DNASeparationFilterGuideCategory;

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

        gui.showTypes(List.of(DNASeparationFilterGuideCategory.RECIPE_TYPE));
    }
}
