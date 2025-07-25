package net.seentro.prehistoriccraft.core.enums.species;

import net.minecraft.util.StringRepresentable;

public enum SilurianSpecies implements StringRepresentable {
    SILURIAN("silurian"),
    SIL("sil");

    private String species;

    SilurianSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getSerializedName() {
        return this.species;
    }
}
