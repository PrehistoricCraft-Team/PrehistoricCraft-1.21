package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PrehistoricRecipeProvider extends RecipeProvider {
    public PrehistoricRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricItems.PLASTER_WRAP.get(),8)
                .pattern("SPS")
                .pattern("PWP")
                .pattern("SPS")
                .define('S', PrehistoricItems.PLASTER_POWDER)
                .define('P', Items.PAPER)
                .define('W', Items.WATER_BUCKET)
                .unlockedBy("has_paper", has(Items.PAPER))
                .unlockedBy("has_water_bucket", has(Items.WATER_BUCKET))
                .unlockedBy("has_plaster_powder", has(PrehistoricItems.PLASTER_POWDER))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricItems.REINFORCED_OBSIDIAN_PLATE.get(),1)
                .pattern("III")
                .pattern("COC")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('C', Items.COPPER_INGOT)
                .define('O', PrehistoricItems.OBSIDIAN_PLATE)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .unlockedBy("has_obsidian_plate", has(PrehistoricItems.OBSIDIAN_PLATE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricItems.OBSIDIAN_PLATE.get(),2)
                .pattern("OOO")
                .pattern(" L ")
                .pattern("OOO")
                .define('O', Items.OBSIDIAN)
                .define('L', Items.LAVA_BUCKET)
                .unlockedBy("has_obsidian", has(Items.OBSIDIAN))
                .unlockedBy("has_lava", has(Items.LAVA_BUCKET))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricItems.BLOB_OF_BLICE.get(),4)
                .pattern("BPB")
                .pattern("PWP")
                .pattern("BPB")
                .define('P', Blocks.PACKED_ICE)
                .define('B', Items.BLAZE_POWDER)
                .define('W', Items.WATER_BUCKET)
                .unlockedBy("has_packed_ice", has(Blocks.PACKED_ICE))
                .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER))
                .unlockedBy("has_water_buucket", has(Items.WATER_BUCKET))
                .save(recipeOutput, "prehistoriccraft:blice_from_bucket");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricItems.GOLD_PIPE.get(),1)
                .pattern("NGN")
                .pattern(" G ")
                .pattern("NGN")
                .define('G', Items.GOLD_INGOT)
                .define('N', Items.GOLD_NUGGET)
                .unlockedBy("has_gold", has(Items.GOLD_INGOT))
                .unlockedBy("has_gold_nugget", has(Items.GOLD_NUGGET))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricItems.VIAL.get(),1)
                .pattern("   ")
                .pattern(" G ")
                .pattern(" G ")
                .define('G', Items.GLASS_PANE)
                .unlockedBy("has_glass", has(Blocks.GLASS_PANE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricItems.PETRI_DISH.get(),2)
                .pattern("   ")
                .pattern("   ")
                .pattern("GGG")
                .define('G', Items.GLASS_PANE)
                .unlockedBy("has_glass", has(Blocks.GLASS_PANE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricItems.SYRINGE.get(),1)
                .pattern(" I ")
                .pattern(" G ")
                .pattern("III")
                .define('G', Items.GLASS_PANE)
                .define('I', Items.IRON_NUGGET)
                .unlockedBy("has_glass", has(Blocks.GLASS_PANE))
                .unlockedBy("has_glass", has(Items.IRON_NUGGET))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricItems.JAR.get(),3)
                .pattern("WWW")
                .pattern("G G")
                .pattern("GGG")
                .define('G', Items.GLASS_PANE)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_glass", has(Blocks.GLASS_PANE))
                .unlockedBy("has_wooden_slabs", has(ItemTags.WOODEN_SLABS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricItems.NANOPOD.get(),1)
                .pattern("III")
                .pattern("AEA")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('A', Items.AMETHYST_SHARD)
                .define('E', Items.ECHO_SHARD)
                .define('R', Items.REDSTONE)
                .unlockedBy("has_glass", has(Blocks.GLASS_PANE))
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, PrehistoricItems.BLOB_OF_BLICE, 1)
                .requires(Items.BLAZE_POWDER)
                .requires(Blocks.PACKED_ICE)
                .requires(Items.POTION)
                .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER))
                .unlockedBy("has_packed_ice", has(Blocks.PACKED_ICE))
                .unlockedBy("has_potion", has(Items.POTION))
                .save(recipeOutput, "prehistoriccraft:blice_from_bottle");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, PrehistoricItems.BOTTLE_OF_BLICE, 1)
                .requires(PrehistoricItems.BLOB_OF_BLICE)
                .requires(PrehistoricItems.BLOB_OF_BLICE)
                .requires(Items.POTION)
                .unlockedBy("has_blob_of_blice", has(PrehistoricItems.BLOB_OF_BLICE))
                .unlockedBy("has_potion", has(Items.POTION))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get())
                .pattern("BPR")
                .pattern("WWW")
                .pattern("F F")
                .define('B', Items.BOOK)
                .define('P', Items.IRON_PICKAXE)
                .define('R', Blocks.FLOWER_POT)
                .define('W', Blocks.WHITE_CARPET)
                .define('F', ItemTags.FENCES)
                .unlockedBy("has_book", has(Items.BOOK))
                .unlockedBy("has_iron_pickaxe", has(Items.IRON_PICKAXE))
                .unlockedBy("has_white_carpet", has(Blocks.WHITE_CARPET))
                .unlockedBy("has_fence", has(ItemTags.FENCES))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get())
                .pattern("PRP")
                .pattern(" G ")
                .pattern("RIR")
                .define('P', PrehistoricItems.GOLD_PIPE)
                .define('G', Blocks.GLASS)
                .define('I', Items.GOLD_INGOT)
                .define('R', PrehistoricItems.REINFORCED_OBSIDIAN_PLATE)
                .unlockedBy("has_gold", has(Items.GOLD_INGOT))
                .unlockedBy("has_gold_pipe", has(PrehistoricItems.GOLD_PIPE))
                .unlockedBy("has_reinforced_obsidian_plate", has(PrehistoricItems.REINFORCED_OBSIDIAN_PLATE))
                .unlockedBy("has_glass", has(Blocks.GLASS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PrehistoricBlocks.ACID_CLEANING_CHAMBER.get())
                .pattern("RRR")
                .pattern("GPG")
                .pattern("RRR")
                .define('P', PrehistoricItems.GOLD_PIPE)
                .define('G', Blocks.GLASS)
                .define('R', PrehistoricItems.REINFORCED_OBSIDIAN_PLATE)
                .unlockedBy("has_gold_pipe", has(PrehistoricItems.GOLD_PIPE))
                .unlockedBy("has_reinforced_obsidian_plate", has(PrehistoricItems.REINFORCED_OBSIDIAN_PLATE))
                .unlockedBy("has_glass", has(Blocks.GLASS))
                .save(recipeOutput);

        oreSmelting(recipeOutput, List.of(PrehistoricItems.GYPSUM_POWDER.get()), RecipeCategory.MISC, PrehistoricItems.PLASTER_POWDER.get(), 0.35f, 85, "plaster_powder");
        oreBlasting(recipeOutput, List.of(PrehistoricItems.GYPSUM_POWDER.get()), RecipeCategory.MISC, PrehistoricItems.PLASTER_POWDER.get(), 0.35f, 35, "plaster_powder");



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
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, PrehistoricCraft.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));


        }
    }
}

