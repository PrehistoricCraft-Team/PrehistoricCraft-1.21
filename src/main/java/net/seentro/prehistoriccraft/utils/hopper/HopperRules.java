package net.seentro.prehistoriccraft.utils.hopper;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class HopperRules {

    public static class SideRule {
        public final IntPredicate canInsertSlot;
        public final Predicate<ItemStack> canInsertItem;
        public final IntPredicate canExtractSlot;

        public SideRule(IntPredicate canInsertSlot,
                        Predicate<ItemStack> canInsertItem,
                        IntPredicate canExtractSlot) {
            this.canInsertSlot = canInsertSlot;
            this.canInsertItem = canInsertItem;
            this.canExtractSlot = canExtractSlot;
        }

        public static SideRule noAccess() {
            return new SideRule(s -> false, s -> false, s -> false);
        }
    }

    private final EnumMap<Direction, SideRule> rules = new EnumMap<>(Direction.class);

    public void set(Direction side, SideRule rule) {
        rules.put(side, rule);
    }

    @Nullable
    public SideRule get(Direction side) {
        return rules.get(side);
    }
}
