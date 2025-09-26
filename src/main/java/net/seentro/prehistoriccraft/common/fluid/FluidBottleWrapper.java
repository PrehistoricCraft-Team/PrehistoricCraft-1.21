package net.seentro.prehistoriccraft.common.fluid;

import net.minecraft.world.item.*;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.seentro.prehistoriccraft.common.item.FilledBottleItem;
import net.seentro.prehistoriccraft.registry.PrehistoricFluidTypes;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;

public class FluidBottleWrapper implements IFluidHandlerItem {
    protected ItemStack container;

    public FluidBottleWrapper(ItemStack container) {
        this.container = container;
    }

    public FluidStack getFluid() {
        Item item = container.getItem();
        if (item instanceof FilledBottleItem) {
            return new FluidStack(((FilledBottleItem) item).content, 250);
        } else {
            return FluidStack.EMPTY;
        }
    }

    protected void setFluid(FluidStack fluidStack) {
        if (fluidStack.isEmpty())
            container = new ItemStack(Items.GLASS_BOTTLE);
        else
            container = getFilledBottle(fluidStack);
    }

    public static ItemStack getFilledBottle(FluidStack fluidStack) {
        if (fluidStack.is(PrehistoricFluidTypes.BLICE_FLUID_TYPE.get())) {
            return new ItemStack(PrehistoricItems.BOTTLE_OF_BLICE.get());
        }
        return ItemStack.EMPTY;
    }

    public boolean canFillFluidType(FluidStack fluid) {
        if (fluid.is(PrehistoricFluidTypes.BLICE_FLUID_TYPE.get())) {
            return true;
        }
        return getFilledBottle(fluid) != ItemStack.EMPTY;
    }

    @Override
    public ItemStack getContainer() {
        return container;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        return 250;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return true;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (container.getCount() != 1 || resource.getAmount() < 250 || !getFluid().isEmpty() || !canFillFluidType(resource)) {
            return 0;
        }

        if (action.execute()) {
            setFluid(resource);
        }

        return 250;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (container.getCount() != 1 || resource.getAmount() < 250) {
            return FluidStack.EMPTY;
        }

        FluidStack fluidStack = getFluid();
        if (!fluidStack.isEmpty() && FluidStack.isSameFluidSameComponents(fluidStack, resource)) {
            if (action.execute()) {
                setFluid(FluidStack.EMPTY);
            }
            return fluidStack;
        }

        return FluidStack.EMPTY;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (container.getCount() != 1 || maxDrain < 250) {
            return FluidStack.EMPTY;
        }

        FluidStack fluidStack = getFluid();
        if (!fluidStack.isEmpty()) {
            if (action.execute()) {
                setFluid(FluidStack.EMPTY);
            }
            return fluidStack;
        }

        return FluidStack.EMPTY;
    }
}
