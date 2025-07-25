package net.seentro.prehistoriccraft.core.enums.species;

import net.minecraft.util.StringRepresentable;

public enum DevonianSpecies implements StringRepresentable {
    DEVONIAN("devonian"),
    DEV("dev");

    private String species;

    DevonianSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getSerializedName() {
        return this.species;
    }
}
