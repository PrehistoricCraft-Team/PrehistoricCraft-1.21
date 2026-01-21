package net.seentro.prehistoriccraft.registry;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.worldgen.PrehistoricConfiguredFeatures;

import java.util.Optional;

public class PrehistoricTreeGrowers {
    public static final TreeGrower DAWN_REDWOOD = new TreeGrower(PrehistoricCraft.MODID + 
        ":dawn_redwood", Optional.of(PrehistoricConfiguredFeatures.DAWN_REDWOOD_BIG_KEY), Optional.of(PrehistoricConfiguredFeatures.DAWN_REDWOOD_KEY),
        Optional.empty());
}
