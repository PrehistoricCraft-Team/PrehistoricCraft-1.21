package net.seentro.prehistoriccraft.core.enums.species;

import net.minecraft.util.StringRepresentable;

public enum CretaceousSpecies implements StringRepresentable {
    CRETACEOUS("cretaceous"),
    CRETA("creta");

    private String species;

    CretaceousSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getSerializedName() {
        return this.species;
    }
}
