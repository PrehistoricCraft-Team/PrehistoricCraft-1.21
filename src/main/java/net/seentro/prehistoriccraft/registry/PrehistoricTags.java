package net.seentro.prehistoriccraft.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.seentro.prehistoriccraft.PrehistoricCraft;

public class PrehistoricTags {
    public static class Blocks {
        public static final TagKey<Block> PLASTERED_FOSSIL =
                BlockTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "plastered_fossils"));
        public static final TagKey<Block> LOAM =
                BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", "loam"));
        public static final TagKey<Block> SILT =
                BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", "silt"));
        public static final TagKey<Block> CLAY =
                BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", "clay"));


    }

    public static class Items {
        public static final TagKey<Item> FOSSILS =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "fossils"));
        public static final TagKey<Item> FOSSIL_SAMPLES =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "fossil_samples"));
        public static final TagKey<Item> AMBER =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "amber"));
        public static final TagKey<Item> JAR =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","jar"));
        public static final TagKey<Item> SYRINGE =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "syringe"));
        public static final TagKey<Item> VIAL =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","vial"));
        public static final TagKey<Item> TISSUES =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID,"tissues"));
        public static final TagKey<Item> CLAY_BALL =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","clay_ball"));
        public static final TagKey<Item> SULFUR =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","sulfur"));

        // BLOCKS

        public static final TagKey<Item> LOAM =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","loam"));
        public static final TagKey<Item> SILT =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","silt"));
        public static final TagKey<Item> CLAY =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "clay"));



    }
}

