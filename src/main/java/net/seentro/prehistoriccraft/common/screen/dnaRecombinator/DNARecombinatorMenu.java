package net.seentro.prehistoriccraft.common.screen.dnaRecombinator;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.seentro.prehistoriccraft.common.block.dnaRecombinator.DNARecombinatorBlockEntity;
import net.seentro.prehistoriccraft.common.screen.MachineMenu;
import net.seentro.prehistoriccraft.common.screen.slotItemHandlers.OutputSlotItemHandler;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricMenuTypes;

public class DNARecombinatorMenu extends MachineMenu<DNARecombinatorBlockEntity> {
    private final Level level;

    public DNARecombinatorMenu(int containerId, Inventory inv, FriendlyByteBuf byteBuf) {
        this(containerId, inv, inv.player.level().getBlockEntity(byteBuf.readBlockPos()), new SimpleContainerData(3));
    }

    public DNARecombinatorMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
        super(PrehistoricMenuTypes.DNA_RECOMBINATOR_MENU.get(), containerId, (DNARecombinatorBlockEntity) blockEntity, data, 10);
        this.level = inv.player.level();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);

        IItemHandler handler = this.blockEntity.itemHandler;

        setPosValues(12, 113, 12, 171);

        /* PETRI DISHES */

        // OUTPUT PETRI DISH
        this.addSlot(new SlotItemHandler(handler, 0, 84, 50));

        this.addSlot(new SlotItemHandler(handler, 1, 28, 12));
        this.addSlot(new SlotItemHandler(handler, 2, 140, 12));

        /* MAGMA */
        this.addSlot(new SlotItemHandler(handler, 3, 84, 81));

        /* LEFT ROW */
        for (int row = 0; row < 3; row++) {
            this.addSlot(new SlotItemHandler(handler, 4 + row, 28, 43 + row * 19));
        }

        /* RIGHT ROW */
        for (int row = 0; row < 3; row++) {
            this.addSlot(new SlotItemHandler(handler, 7 + row, 140, 43 + row * 19));
        }
    }


    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, PrehistoricBlocks.DNA_RECOMBINATOR.get());
    }

    @Override
    public void removed(Player player) {
        if (data.get(2) == 0)
            blockEntity.triggerAnim("controller", "close_doors");

        super.removed(player);
    }
}
