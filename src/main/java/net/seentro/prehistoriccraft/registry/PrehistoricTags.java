package net.seentro.prehistoriccraft.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.seentro.prehistoriccraft.PrehistoricCraft;

public class PrehistoricTags {

    public static class Blocks {
        public static final TagKey<Block> PLASTERED_FOSSIL =
                BlockTags.create(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "plastered_fossils"));
    }

    public static class Items {
        //  For custom item tags later
    }
}

