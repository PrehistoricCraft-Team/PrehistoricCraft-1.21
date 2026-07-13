package net.seentro.prehistoriccraft.core.multiblock;

import net.minecraft.util.StringRepresentable;

public enum QuadrupleInvisibleSegmentProperty implements StringRepresentable {
    INVISIBLE("invisible"),
    STEM("stem"),
    BASE("base"),
    MIDDLE("middle"),
    TOP("top");

    private final String name;

    QuadrupleInvisibleSegmentProperty(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
