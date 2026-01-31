package net.seentro.prehistoriccraft.common.nature.neocalamites;

import net.seentro.prehistoriccraft.common.nature.plantStructures.PlantStructure;

public class NeocalamitesBlock extends PlantStructure {
    public NeocalamitesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HEIGHT, 4));
    }
}
