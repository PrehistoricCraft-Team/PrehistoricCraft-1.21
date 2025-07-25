package net.seentro.prehistoriccraft.core.enums.species;

import net.minecraft.util.StringRepresentable;

public enum NeogeneSpecies implements StringRepresentable {
    NEOGENE("neogene"),
    NEOG("neog");

    private String species;

    NeogeneSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getSerializedName() {
        return this.species;
    }
}
