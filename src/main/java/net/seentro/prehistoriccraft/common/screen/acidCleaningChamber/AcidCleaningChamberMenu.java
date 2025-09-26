package net.seentro.prehistoriccraft.common.screen.acidCleaningChamber;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.seentro.prehistoriccraft.common.block.acidCleaningChamber.AcidCleaningChamberBlockEntity;
import net.seentro.prehistoriccraft.common.screen.MachineMenu;
import net.seentro.prehistoriccraft.common.screen.slotItemHandlers.OutputSlotItemHandler;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;
import net.seentro.prehistoriccraft.registry.PrehistoricMenuTypes;

public class AcidCleaningChamberMenu extends MachineMenu<AcidCleaningChamberBlockEntity> {
    private final Level level;

    public AcidCleaningChamberMenu(int containerId, Inventory inv, FriendlyByteBuf byteBuf) {
        this(containerId, inv, inv.player.level().getBlockEntity(byteBuf.readBlockPos()), new SimpleContainerData(3));
    }

    public AcidCleaningChamberMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
        super(PrehistoricMenuTypes.ACID_CLEANING_CHAMBER_MENU.get(), containerId, (AcidCleaningChamberBlockEntity) blockEntity, data, 13);
        this.level = inv.player.level();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);

        IItemHandler handler = this.blockEntity.itemHandler;

        setPosValues(12, 93, 12, 151);

        /* BOTTLE */
        this.addSlot(new SlotItemHandler(handler, 0, 143, 34));

        /* INPUT SLOTS */
        for (int col = 0; col < 2; col++) {
            for (int row = 0; row < 3; row++) {
                this.addSlot(new SlotItemHandler(handler, 1 + row * 2 + col, 19 + col * 19, 15 + row * 19));
            }
        }

        /* OUTPUT SLOTS */
        for (int col = 0; col < 2; col++) {
            for (int row = 0; row < 3; row++) {
                this.addSlot(new OutputSlotItemHandler(handler, 7 + row * 2 + col, 100 + col * 19, 15 + row * 19));
            }
        }
    }

    public int getScaledArrowProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        int progressBarLength = 32;

        return maxProgress != 0 && progress != 0 ? progress * progressBarLength / maxProgress : 0;
    }

    public int getScaledAcidFill() {
        int acid = getAcid();
        int maxAcid = getMaxAcid();
        int acidBarHeight = 57;

        if (maxAcid == 0) return 0;

        return acid * acidBarHeight / maxAcid;
    }

    public int getAcid() {
        return blockEntity.getFluidAmount();
    }
    public int getMaxAcid() {
        return blockEntity.getFluidCapacity();
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, PrehistoricBlocks.ACID_CLEANING_CHAMBER.get());
    }

    @Override
    public void removed(Player player) {
        if (data.get(2) == 0)
            blockEntity.triggerAnim("controller", "close_doors");

        super.removed(player);
    }
}
