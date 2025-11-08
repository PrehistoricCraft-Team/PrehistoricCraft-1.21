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
                BlockTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "loam"));
        public static final TagKey<Block> SILT =
                BlockTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "silt"));
        public static final TagKey<Block> CLAY =
                BlockTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "clay"));


    }

    public static class Items {
        public static final TagKey<Item> FOSSILS =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "fossils"));
        public static final TagKey<Item> FOSSIL_SAMPLES =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "fossil_samples"));
        public static final TagKey<Item> AMBER =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "amber"));
        public static final TagKey<Item> JAR =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID,"jar"));
        public static final TagKey<Item> SYRINGE =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "syringe"));
        public static final TagKey<Item> VIAL =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID,"vial"));



    }
}

