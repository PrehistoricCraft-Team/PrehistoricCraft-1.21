package net.seentro.prehistoriccraft.core.enums.species;

import net.minecraft.util.StringRepresentable;

public enum JurassicSpecies implements StringRepresentable {
    JURASSIC("jurassic"),
    JUR("jur");

    private String species;

    JurassicSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getSerializedName() {
        return this.species;
    }
}
