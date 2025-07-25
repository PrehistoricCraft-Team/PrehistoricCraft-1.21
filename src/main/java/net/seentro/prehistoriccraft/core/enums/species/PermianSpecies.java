package net.seentro.prehistoriccraft.core.enums.species;

import net.minecraft.util.StringRepresentable;

public enum PermianSpecies implements StringRepresentable {
    PERMIAN("permian"),
    PERM("perm");

    private String species;

    PermianSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getSerializedName() {
        return this.species;
    }
}
