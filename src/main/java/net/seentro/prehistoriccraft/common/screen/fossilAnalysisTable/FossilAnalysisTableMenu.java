package net.seentro.prehistoriccraft.common.screen.fossilAnalysisTable;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.seentro.prehistoriccraft.common.block.fossilAnalysisTable.FossilAnalysisTableBlockEntity;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricMenuTypes;
import org.jetbrains.annotations.NotNull;

public class FossilAnalysisTableMenu extends AbstractContainerMenu {
    public final FossilAnalysisTableBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public FossilAnalysisTableMenu(int containerId, Inventory inv, FriendlyByteBuf byteBuf) {
        this(containerId, inv, inv.player.level().getBlockEntity(byteBuf.readBlockPos()), new SimpleContainerData(2));
    }

    public FossilAnalysisTableMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
        super(PrehistoricMenuTypes.FOSSIL_ANALYSIS_TABLE_MENU.get(), containerId);
        this.blockEntity = (FossilAnalysisTableBlockEntity) blockEntity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);

        IItemHandler handler = this.blockEntity.itemHandler;

        /* LEFT 3x3 GRID */
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                this.addSlot(new SlotItemHandler(handler, col + row * 3, 24 + col * 18, 34 + row * 18));
            }
        }

        this.addSlot(new SlotItemHandler(handler, 9, 82, 25));

        /* RIGHT 3x3 GRID */
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                this.addSlot(new SlotItemHandler(handler, 10 + row * 3 + col, 104 + col * 18, 34 + row * 18));
            }
        }
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        int arrowPixelSize = 17;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    //CREDIT FOR THIS PART GOES TO: diesieben07
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 19;
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the player inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get());
    }


    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 10 + column * 18, 107 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 10 + i * 18, 165));
        }
    }
}
