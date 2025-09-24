package net.seentro.prehistoriccraft.common.screen.slotItemHandlers;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SpecificItemSlotItemHandler extends SlotItemHandler {
    private Map<Integer, ItemStack> STACK_MAP = new HashMap<>();
    public SpecificItemSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition, Map<Integer, ItemStack>... maps) {
        super(itemHandler, index, xPosition, yPosition);

        Arrays.stream(maps).forEach(map -> STACK_MAP.putAll(map));
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        int index = getSlotIndex();
        if (STACK_MAP.containsKey(index)) {
            return STACK_MAP.get(index) == stack;
        }

        return super.mayPlace(stack);
    }
}
