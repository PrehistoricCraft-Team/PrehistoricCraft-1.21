package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PrehistoricRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public PrehistoricRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        /* PRESET RECIPES */
        /*ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricBlocks.AMBER_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', Items.STICK)
                .unlockedBy("has_wood", has(ItemTags.LOGS)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STICK, 9)
                .requires(PrehistoricBlocks.AMBER_BLOCK)
                .unlockedBy("has_amber", has(PrehistoricBlocks.AMBER_BLOCK)).save(recipeOutput);

        If you have 2 recipes yielding the same result, you will need to differentiate the file names, like so
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STICK, 9)
                .requires(Blocks.OAK_LOG)
                .unlockedBy("has_amber", has(PrehistoricBlocks.AMBER_BLOCK))
                .save(recipeOutput, "prehistoriccraft:stick_from_oak_logs");

        List<ItemLike> UNHEATED_SYRINGES = List.of(); Demonstrational list, let's say only smelting and not blasting
        List<ItemLike> STONE = List.of(Blocks.STONE, Blocks.DEEPSLATE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE); Smelting and blasting

        oreSmelting(recipeOutput, UNHEATED_SYRINGES, RecipeCategory.MISC, Items.STICK, 0.25f, 100, "sticks");

        oreSmelting(recipeOutput, STONE, RecipeCategory.MISC, PrehistoricBlocks.AMBER_BLOCK, 0.25f, 500, "amber_block");
        oreBlasting(recipeOutput, STONE, RecipeCategory.MISC, PrehistoricBlocks.AMBER_BLOCK, 0.25f, 200, "amber_block");

        oreSmelting(recipeOutput, List.of(Items.APPLE), RecipeCategory.MISC, Items.ANVIL, 1f, 50, "anvil"); If you only need 1 item*/
    }

    /* HELPER METHODS */

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, PrehistoricCraft.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
