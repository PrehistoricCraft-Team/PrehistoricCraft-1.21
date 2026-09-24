package net.seentro.prehistoriccraft.registry;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.seentro.prehistoriccraft.core.multiblock.QuadrupleInvisibleSegmentProperty;

public class PrehistoricBlockStateProperties {
    // Variants
    public static final IntegerProperty VARIANT_DOUBLE = IntegerProperty.create("variant_double", 1, 2);
    public static final IntegerProperty VARIANT_TRIPLE = IntegerProperty.create("variant_triple", 1, 3);

    // Bools
    public static final BooleanProperty IS_STEM = BooleanProperty.create("is_stem");
    public static final BooleanProperty INVISIBLE = BooleanProperty.create("invisible");
    public static final BooleanProperty TWO_BY_TWO = BooleanProperty.create("two_by_two");

    // Enums
    public static final EnumProperty<QuadrupleInvisibleSegmentProperty> QUAD_INV_SEGMENT = EnumProperty.create("segment", QuadrupleInvisibleSegmentProperty.class);

    // Stages
    public static final IntegerProperty STAGES_TRIPLE = IntegerProperty.create("stages", 1, 3);

    // Integers
    public static final IntegerProperty MIDDLE_SEGMENT_2 = IntegerProperty.create("middle_segment_count", 1, 2);
}
