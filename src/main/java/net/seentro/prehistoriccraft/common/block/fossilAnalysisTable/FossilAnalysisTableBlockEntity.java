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
import net.seentro.prehistoriccraft.common.screen.tissueExtractionChamber.TissueExtractionChamberMenu;
import net.seentro.prehistoriccraft.core.systems.WeightedRandom;
import net.seentro.prehistoriccraft.registry.*;
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
            return slot == 9 ? stack.getItem() == PrehistoricItems.MAGNIFYING_GLASS.get() : super.isItemValid(slot, stack);
        }
    };

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 120;

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

    /* INVENTORY & PROCESSING */

    public void drop() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, container);
    }

    private @Nullable ItemStack qualityFossil;
    private int validInputSlot = -1;

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!tryInitializeRecipe()) {
            reset();
            return;
        }

        if (hasRecipe()) {
            progress++;
            setChanged(level, pos, state);

            if (progress >= maxProgress) {
                craft();
                reset();
            }
        } else {
            reset();
        }
    }

    private boolean tryInitializeRecipe() {
        for (int i = 0; i < 9; i++) {
            ItemStack input = itemHandler.getStackInSlot(i);
            if (input.is(PrehistoricTags.Items.FOSSILS) && !input.has(PrehistoricDataComponents.FOSSIL_QUALITY)) {
                ItemStack inputCopy = input.copy();
                inputCopy.setCount(1);
                ItemStack candidate = assignRandomQuality(inputCopy);

                if (canOutput(candidate)) {
                    validInputSlot = i;
                    qualityFossil = candidate;
                    return true;
                }
            }
        }
        return false;
    }

    private ItemStack assignRandomQuality(ItemStack fossil) {
        WeightedRandom<String> qualities = new WeightedRandom<>();

        qualities.addItem("damaged", 10);
        qualities.addItem("incomplete", 15);
        qualities.addItem("fragmentary", 30);
        qualities.addItem("decent", 35);
        qualities.addItem("rich", 10);

        String selectedQuality = qualities.getRandomItem();

        fossil.set(PrehistoricDataComponents.FOSSIL_QUALITY, selectedQuality);

        return fossil;
    }

    private boolean hasRecipe() {
        if (validInputSlot < 0 || qualityFossil == null) return false;

        ItemStack input = itemHandler.getStackInSlot(validInputSlot);
        if (input.isEmpty() || !input.is(PrehistoricTags.Items.FOSSILS) || input.has(PrehistoricDataComponents.FOSSIL_QUALITY)) return false;

        ItemStack magnifier = itemHandler.getStackInSlot(9);
        if (!magnifier.is(PrehistoricItems.MAGNIFYING_GLASS)) return false;
        if (magnifier.getMaxDamage() - magnifier.getDamageValue() < 1) return false;

        return canOutput(qualityFossil);
    }

    private boolean canOutput(ItemStack output) {
        for (int i = 10; i < 19; i++) {
            ItemStack outputCopy = itemHandler.insertItem(i, output.copy(), true);
            if (outputCopy.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void craft() {
        itemHandler.extractItem(validInputSlot, 1, false);
        ItemStack magnifier = itemHandler.getStackInSlot(9);

        if (magnifier.isDamageableItem()) {
            magnifier.setDamageValue(magnifier.getDamageValue() + 1);

            if (magnifier.getDamageValue() >= magnifier.getMaxDamage()) {
                itemHandler.setStackInSlot(9, ItemStack.EMPTY);
            }
        }

        for (int i = 10; i < 19; i++) {
            ItemStack leftover = itemHandler.insertItem(i, qualityFossil.copy(), false);
            if (leftover.isEmpty()) {
                break;
            }
        }
    }

    private void reset() {
        progress = 0;
        qualityFossil = null;
        validInputSlot = -1;
    }
}
