package net.seentro.prehistoriccraft.registry;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.worldgen.PrehistoricConfiguredFeatures;

import java.util.Optional;

public class PrehistoricTreeGrowers {
    public static final TreeGrower DAWN_REDWOOD = new TreeGrower(PrehistoricCraft.MODID + 
        ":dawn_redwood", Optional.empty(), Optional.of(PrehistoricConfiguredFeatures.DAWN_REDWOOD_TREE_KEY), 
        Optional.empty());
}
