package net.seentro.prehistoriccraft.compat.jei.guides.tissueExtractionChamber;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;

public class TissueExtractionChamberGuideCategory implements IRecipeCategory<TissueExtractionChamberGuideRecipe> {

    public static final RecipeType<TissueExtractionChamberGuideRecipe> RECIPE_TYPE =
            RecipeType.create(PrehistoricCraft.MODID, "tissue_extraction_chamber_guide", TissueExtractionChamberGuideRecipe.class);

    private static final ResourceLocation GUIDE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/gui/jei/dna_separation_filter_guide.png");

    private final IDrawable background;
    private final IDrawable icon;

    public TissueExtractionChamberGuideCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(150, 80);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get()));
    }

    @Override
    public RecipeType<TissueExtractionChamberGuideRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.prehistoriccraft.tissue_extraction_chamber.guide");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TissueExtractionChamberGuideRecipe recipe, IFocusGroup focuses) {
    }

    @Override
    public void draw(TissueExtractionChamberGuideRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(
                GUIDE_TEXTURE,
                0, 0,
                0, 0,
                150, 80,
                150, 80
        );
    }
}
