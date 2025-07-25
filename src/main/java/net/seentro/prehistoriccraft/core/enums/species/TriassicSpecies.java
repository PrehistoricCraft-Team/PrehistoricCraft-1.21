package net.seentro.prehistoriccraft.core.enums.species;

import net.minecraft.util.StringRepresentable;

public enum TriassicSpecies implements StringRepresentable {
    TRIASSIC("triassic"),
    TRIA("tria");

    private String species;

    TriassicSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getSerializedName() {
        return this.species;
    }
}
