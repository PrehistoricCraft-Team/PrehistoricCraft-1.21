package net.seentro.prehistoriccraft.core.enums.species;

import net.minecraft.util.StringRepresentable;

public enum OrdovicianSpecies implements StringRepresentable {
    ORDOVICIAN("ordovician"),
    ORDO("ordo");

    private String species;

    OrdovicianSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getSerializedName() {
        return this.species;
    }
}
