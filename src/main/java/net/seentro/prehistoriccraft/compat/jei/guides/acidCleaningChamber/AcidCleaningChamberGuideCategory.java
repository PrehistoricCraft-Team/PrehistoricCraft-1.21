package net.seentro.prehistoriccraft.compat.jei.guides.acidCleaningChamber;

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
import org.jetbrains.annotations.NotNull;

public class AcidCleaningChamberGuideCategory implements IRecipeCategory<AcidCleaningChamberGuideRecipe> {

    public static final RecipeType<AcidCleaningChamberGuideRecipe> RECIPE_TYPE =
            RecipeType.create(PrehistoricCraft.MODID, "acid_cleaning_table_guide", AcidCleaningChamberGuideRecipe.class);

    private static final ResourceLocation GUIDE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/gui/jei/dna_separation_filter_guide.png");

    private final IDrawable icon;

    public AcidCleaningChamberGuideCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(PrehistoricBlocks.ACID_CLEANING_CHAMBER.get()));
    }

    @Override
    public @NotNull RecipeType<AcidCleaningChamberGuideRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("gui.prehistoriccraft.acid_cleaning_chamber.guide");
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AcidCleaningChamberGuideRecipe recipe, IFocusGroup focuses) {
    }

    @Override
    public int getWidth() {
        return 150;
    }

    @Override
    public int getHeight() {
        return 80;
    }

    @Override
    public void draw(AcidCleaningChamberGuideRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(
                GUIDE_TEXTURE,
                0, 0,
                0, 0,
                150, 80,
                150, 80
        );
    }
}
