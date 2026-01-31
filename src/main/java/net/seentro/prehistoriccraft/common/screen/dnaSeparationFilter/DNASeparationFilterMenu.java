package net.seentro.prehistoriccraft.common.screen.dnaSeparationFilter;

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
import net.seentro.prehistoriccraft.common.block.dnaSeparationFilter.DNASeparationFilterBlockEntity;
import net.seentro.prehistoriccraft.common.screen.MachineMenu;
import net.seentro.prehistoriccraft.common.screen.slotItemHandlers.OutputSlotItemHandler;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricMenuTypes;

public class DNASeparationFilterMenu extends MachineMenu<DNASeparationFilterBlockEntity> {
    private final Level level;

    public DNASeparationFilterMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(5));
    }

    public DNASeparationFilterMenu(int containerId, Inventory inv, BlockEntity be, ContainerData data) {
        super(PrehistoricMenuTypes.DNA_SEPARATION_FILTER_MENU.get(), containerId, (DNASeparationFilterBlockEntity) be, data, 16);
        this.level = inv.player.level();
        this.addDataSlots(data);

        IItemHandler handler = this.blockEntity.itemHandler;

        final int SPACING = 19;

        this.addSlot(new SlotItemHandler(handler, DNASeparationFilterBlockEntity.SLOT_PETRI,    149, 34));
        this.addSlot(new SlotItemHandler(handler, DNASeparationFilterBlockEntity.SLOT_CHARCOAL,  75, 34));
        this.addSlot(new SlotItemHandler(handler, DNASeparationFilterBlockEntity.SLOT_NANO,     112, 34));

        for (int i = 0; i < 6; i++) {
            int slotIndex = DNASeparationFilterBlockEntity.SLOT_TISSUE_1 + i; // 1..6
            this.addSlot(new SlotItemHandler(handler, slotIndex, 64 + (SPACING * i), 64));
        }

        for (int i = 0; i < 6; i++) {
            int slotIndex = DNASeparationFilterBlockEntity.SLOT_OUTPUT_1 + i; // 9..14
            this.addSlot(new OutputSlotItemHandler(handler, slotIndex, 64 + (SPACING * i), 103));
        }

        this.addSlot(new SlotItemHandler(handler, DNASeparationFilterBlockEntity.SLOT_FLUID_IO, 39, 103));

        this.setPosValues(40, 135, 40, 193);

        this.addPlayerInventory(inv);
        this.addPlayerHotbar(inv);
    }

    public int getScaledArrowProgress(int progressBarLength) {
        int progress = data.get(0);
        int maxProgress = data.get(1);

        return maxProgress != 0 && progress != 0 ? progress * progressBarLength / maxProgress : 0;
    }

    public int getScaledWaterFill(int maxWidth) {
        int water = data.get(3);
        int capacity = data.get(4);
        return capacity == 0 ? 0 : water * maxWidth / capacity;
    }

    public int getWater() { return data.get(3); }
    public int getMaxWater() { return data.get(4); }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, PrehistoricBlocks.DNA_SEPARATION_FILTER.get());
    }

    @Override
    public void removed(Player player) {
        if (data.get(2) == 0) {
            blockEntity.triggerAnim("controller", "close_doors");
        }
        super.removed(player);
    }
}
