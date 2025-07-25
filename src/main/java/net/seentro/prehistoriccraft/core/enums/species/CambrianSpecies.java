package net.seentro.prehistoriccraft.core.enums.species;

import net.minecraft.util.StringRepresentable;

public enum CambrianSpecies implements StringRepresentable {
    CAMBRIAN("cambrian"),
    CAM("cam");

    private String species;

    CambrianSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getSerializedName() {
        return this.species;
    }
}
