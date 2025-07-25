package net.seentro.prehistoriccraft.core.enums.species;

import net.minecraft.util.StringRepresentable;

public enum PrecambrianSpecies implements StringRepresentable {
    PRECAMBRIAN("precambrian"),
    PRECAM("precam");

    private String species;

    PrecambrianSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getSerializedName() {
        return this.species;
    }
}
