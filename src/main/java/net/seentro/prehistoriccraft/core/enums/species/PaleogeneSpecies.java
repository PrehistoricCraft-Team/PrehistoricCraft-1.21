package net.seentro.prehistoriccraft.core.enums.species;

import net.minecraft.util.StringRepresentable;

public enum PaleogeneSpecies implements StringRepresentable {
    PALEOGENE("paleogene"),
    PALE("pale");

    private String species;

    PaleogeneSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getSerializedName() {
        return this.species;
    }
}
