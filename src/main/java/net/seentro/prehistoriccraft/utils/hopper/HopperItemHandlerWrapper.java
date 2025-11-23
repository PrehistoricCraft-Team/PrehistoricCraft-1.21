package net.seentro.prehistoriccraft.utils.hopper;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class HopperItemHandlerWrapper implements IItemHandler {

    private final ItemStackHandler base;
    private final HopperRules rules;
    private final net.minecraft.core.Direction logicalSide;

    public HopperItemHandlerWrapper(ItemStackHandler base,
                                    HopperRules rules,
                                    net.minecraft.core.Direction logicalSide) {
        this.base = base;
        this.rules = rules;
        this.logicalSide = logicalSide;
    }

    @Nullable
    private HopperRules.SideRule rule() {
        return rules.get(logicalSide);
    }

    @Override
    public int getSlots() {
        return base.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return base.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        HopperRules.SideRule r = rule();
        if (r == null) return stack;

        if (!r.canInsertSlot.test(slot)) return stack;
        if (!r.canInsertItem.test(stack)) return stack;

        return base.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        HopperRules.SideRule r = rule();
        if (r == null) return ItemStack.EMPTY;

        if (!r.canExtractSlot.test(slot)) return ItemStack.EMPTY;

        return base.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return base.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        HopperRules.SideRule r = rule();
        if (r == null) return false;

        return r.canInsertSlot.test(slot) && r.canInsertItem.test(stack) && base.isItemValid(slot, stack);
    }
}
