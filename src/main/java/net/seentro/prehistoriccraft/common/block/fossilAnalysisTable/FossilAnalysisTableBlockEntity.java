package net.seentro.prehistoriccraft.common.block.fossilAnalysisTable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.screen.fossilAnalysisTable.FossilAnalysisTableMenu;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;
import net.seentro.prehistoriccraft.registry.PrehistoricDataComponents;
import net.seentro.prehistoriccraft.registry.PrehistoricTags;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FossilAnalysisTableBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(19) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot == 9 ? stack.getItem() == Items.STICK : super.isItemValid(slot, stack);
        }
    };

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 600;

    public FossilAnalysisTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(PrehistoricBlockEntityTypes.FOSSIL_ANALYSIS_TABLE_BLOCK_ENTITY.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FossilAnalysisTableBlockEntity.this.progress;
                    case 1 -> FossilAnalysisTableBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: FossilAnalysisTableBlockEntity.this.progress = value;
                    case 1: FossilAnalysisTableBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    /* INVENTORY & SAVING */

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.putInt("maxProgress", maxProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        maxProgress = tag.getInt("maxProgress");
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
       return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new FossilAnalysisTableMenu(containerId, playerInventory, this, this.data);
    }

    public void drop() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, container);
    }

    private ItemStack qualityFossil;

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (qualityFossil == null)
            qualityFossil = createRandomQualityFossil(level);

        if (hasRecipe(qualityFossil)) {
            increaseCraftingProgress();
            setChanged(level, blockPos, blockState);
            PrehistoricCraft.LOGGER.info("Has recipe");

            if (craftingFinished()) {
                PrehistoricCraft.LOGGER.info("Crafting finished");
                craftStack(qualityFossil);
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void craftStack(ItemStack output) {
        int firstAvailableValidInputSlot = -1;
        for (int i = 0; i < 9; i++) {
            if (itemHandler.getStackInSlot(i).is(PrehistoricTags.Items.FOSSILS) && !itemHandler.getStackInSlot(i).has(PrehistoricDataComponents.FOSSIL_QUALITY)) {
                firstAvailableValidInputSlot = i;
                break;
            }
        }

        if (firstAvailableValidInputSlot != -1) {
            int inputStackSize = itemHandler.getStackInSlot(firstAvailableValidInputSlot).getCount();
            if (canInsertAmountIntoOutputSlots(output, inputStackSize)) {
                int remaining = inputStackSize;
                itemHandler.extractItem(firstAvailableValidInputSlot, inputStackSize, false);

                for (int i = 10; i < 19 && remaining > 0; i++) {
                    ItemStack slotStack = itemHandler.getStackInSlot(i);

                    if (slotStack.isEmpty()) {
                        int toInsert = Math.min(output.getMaxStackSize(), remaining);
                        ItemStack newStack = output.copy();
                        newStack.setCount(toInsert);
                        itemHandler.insertItem(i, newStack, false);
                        remaining -= toInsert;
                    } else if (ItemStack.isSameItemSameComponents(slotStack, output)) {
                        int space = slotStack.getMaxStackSize() - slotStack.getCount();
                        if (space > 0) {
                            int toInsert = Math.min(space, remaining);
                            slotStack.grow(toInsert);
                            remaining -= toInsert;
                        }
                    }

                    PrehistoricCraft.LOGGER.info("Crafted {} x {}", inputStackSize, output);
                }

                qualityFossil = null;
            }
        }
    }

    private void resetProgress() {
        progress = 0;
        qualityFossil = null;
    }

    private boolean craftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private ItemStack createRandomQualityFossil(Level level) {
        ItemStack fossil = ItemStack.EMPTY;
        for (int i = 0; i < 9; i++) {
            if (itemHandler.getStackInSlot(i).is(PrehistoricTags.Items.FOSSILS)) {
                fossil = itemHandler.getStackInSlot(i).copy();
                break;
            }
        }

        if (!fossil.isEmpty())
            switch (level.random.nextInt(5)) {
                case 0 -> fossil.set(PrehistoricDataComponents.FOSSIL_QUALITY, "damaged");
                case 1 -> fossil.set(PrehistoricDataComponents.FOSSIL_QUALITY, "incomplete");
                case 2 -> fossil.set(PrehistoricDataComponents.FOSSIL_QUALITY, "fragmentary");
                case 3 -> fossil.set(PrehistoricDataComponents.FOSSIL_QUALITY, "decent");
                case 4 -> fossil.set(PrehistoricDataComponents.FOSSIL_QUALITY, "rich");
            }

        return fossil;
    }

    private boolean hasRecipe(ItemStack output) {
        for (int i = 0; i < 9; i++) {
            ItemStack input = itemHandler.getStackInSlot(i);
            if (input.is(PrehistoricTags.Items.FOSSILS) && !input.has(PrehistoricDataComponents.FOSSIL_QUALITY)) {
                if (canInsertAmountIntoOutputSlots(output, input.getCount()) && canInsertItemIntoOutputSlots(output)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canInsertItemIntoOutputSlots(ItemStack output) {
        for (int i = 10; i < 19; i++) {
            if (itemHandler.getStackInSlot(i).isEmpty()) return true;
            if (ItemStack.isSameItemSameComponents(itemHandler.getStackInSlot(i), output)) return true;
        }
        return false;
    }

    private boolean canInsertAmountIntoOutputSlots(ItemStack output, int count) {
        int totalSpace = 0;
        for (int i = 10; i < 19; i++) {
            ItemStack slotStack = itemHandler.getStackInSlot(i);
            if (slotStack.isEmpty()) {
                totalSpace += 64;
            } else if (ItemStack.isSameItemSameComponents(slotStack, output)) {
                totalSpace += slotStack.getMaxStackSize() - slotStack.getCount();
            }
            if (totalSpace >= count) return true;
        }
        return false;
    }
}
