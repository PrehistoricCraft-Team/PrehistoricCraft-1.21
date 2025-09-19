package net.seentro.prehistoriccraft.core;

import net.minecraft.util.StringRepresentable;

public enum InvisibleProperty implements StringRepresentable {
    VISIBLE,
    INVISIBLE;

    @Override
    public String getSerializedName() {
        return this == VISIBLE ? "visible" : "invisible";
    }
}
