package net.seentro.prehistoriccraft.core.enums.species;

import net.minecraft.util.StringRepresentable;

public enum CarboniferousSpecies implements StringRepresentable {
    CARBONIFEROUS("carboniferous"),
    CARBON("carbon");

    private String species;

    CarboniferousSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getSerializedName() {
        return this.species;
    }
}
