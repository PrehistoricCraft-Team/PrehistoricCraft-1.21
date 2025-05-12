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

import java.util.ArrayList;
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
                    case 0:
                        FossilAnalysisTableBlockEntity.this.progress = value;
                    case 1:
                        FossilAnalysisTableBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    /* SAVING */

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
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, container);
    }

    /* INVENTORY & PROCESSING */

    private @Nullable List<ItemStack> qualityFossils;
    private int validInputSlot = -1;

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (qualityFossils == null) {
            initializeRecipe(level);
        }

        if (qualityFossils != null && hasRecipe()) {
            progress++;
            setChanged(level, pos, state);

            if (progress >= maxProgress) {
                craftOutputs();
                reset();
            }
        } else {
            reset();
        }
    }

    private void initializeRecipe(Level level) {
        for (int i = 0; i < 9; i++) {
            ItemStack in = itemHandler.getStackInSlot(i);
            if (in.is(PrehistoricTags.Items.FOSSILS) && !in.has(PrehistoricDataComponents.FOSSIL_QUALITY)) {
                validInputSlot = i;
                qualityFossils = generateQualityList(in, level);
                return;
            }
        }
    }

    private List<ItemStack> generateQualityList(ItemStack input, Level level) {
        List<ItemStack> list = new ArrayList<>(input.getCount());
        for (int j = 0; j < input.getCount(); j++) {
            ItemStack single = input.copy();
            single.setCount(1);
            list.add(assignRandomQuality(single, level));
        }
        return list;
    }

    private ItemStack assignRandomQuality(ItemStack fossil, Level level) {
        switch (level.random.nextInt(5)) {
            case 0 -> fossil.set(PrehistoricDataComponents.FOSSIL_QUALITY, "damaged");
            case 1 -> fossil.set(PrehistoricDataComponents.FOSSIL_QUALITY, "incomplete");
            case 2 -> fossil.set(PrehistoricDataComponents.FOSSIL_QUALITY, "fragmentary");
            case 3 -> fossil.set(PrehistoricDataComponents.FOSSIL_QUALITY, "decent");
            default -> fossil.set(PrehistoricDataComponents.FOSSIL_QUALITY, "rich");
        }
        return fossil;
    }

    private boolean hasRecipe() {
        if (validInputSlot < 0 || qualityFossils == null || qualityFossils.isEmpty()) return false;

        ItemStack input = itemHandler.getStackInSlot(validInputSlot);
        if (input.isEmpty() || !input.is(PrehistoricTags.Items.FOSSILS) || input.has(PrehistoricDataComponents.FOSSIL_QUALITY)) {
            return false;
        }

        List<ItemStack> sim = new ArrayList<>();
        for (int i = 10; i < 19; i++) {
            sim.add(itemHandler.getStackInSlot(i).copy());
        }

        for (ItemStack out : qualityFossils) {
            boolean placed = false;

            for (ItemStack slot : sim) {
                if (!slot.isEmpty() && ItemStack.isSameItemSameComponents(slot, out) && slot.getCount() < slot.getMaxStackSize()) {
                    slot.grow(1);
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                for (int k = 0; k < sim.size(); k++) {
                    if (sim.get(k).isEmpty()) {
                        sim.set(k, out.copy());
                        placed = true;
                        break;
                    }
                }
            }

            if (!placed) {
                return false;
            }
        }

        return true;
    }

    private void craftOutputs() {
        itemHandler.extractItem(validInputSlot, qualityFossils.size(), false);

        for (ItemStack out : qualityFossils) {
            boolean done = false;
            for (int i = 10; i < 19 && !done; i++) {
                ItemStack slot = itemHandler.getStackInSlot(i);
                if (!slot.isEmpty() && ItemStack.isSameItemSameComponents(slot, out) && slot.getCount() < slot.getMaxStackSize()) {
                    slot.grow(1);
                    done = true;
                }
            }
            if (!done) {
                for (int i = 10; i < 19; i++) {
                    if (itemHandler.getStackInSlot(i).isEmpty()) {
                        itemHandler.insertItem(i, out.copy(), false);
                        break;
                    }
                }
            }
        }
    }

    private void reset() {
        progress = 0;
        qualityFossils = null;
        validInputSlot = -1;
    }
}
