package net.seentro.prehistoriccraft.common.screen.tissueExtractionChamber;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.TissueExtractionChamberBlockEntity;
import net.seentro.prehistoriccraft.common.screen.MachineMenu;
import net.seentro.prehistoriccraft.common.screen.slotItemHandlers.OutputSlotItemHandler;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricMenuTypes;

public class TissueExtractionChamberMenu extends MachineMenu<TissueExtractionChamberBlockEntity> {
    public final TissueExtractionChamberBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public TissueExtractionChamberMenu(int containerId, Inventory inv, FriendlyByteBuf byteBuf) {
        this(containerId, inv, inv.player.level().getBlockEntity(byteBuf.readBlockPos()), new SimpleContainerData(2));
    }

    public TissueExtractionChamberMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
        super(PrehistoricMenuTypes.TISSUE_EXTRACTION_CHAMBER_MENU.get(), containerId, (TissueExtractionChamberBlockEntity) blockEntity, data, 22);
        this.blockEntity = (TissueExtractionChamberBlockEntity) blockEntity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);

        IItemHandler handler = this.blockEntity.itemHandler;

        setPosValues(12, 113, 12, 171);

        /* BOTTLE */
        this.addSlot(new SlotItemHandler(handler, 0, 12, 11));
        this.addSlot(new OutputSlotItemHandler(handler, 1, 12, 34));

        /* INPUT SLOTS */
        for (int col = 0; col < 4; col++) {
            this.addSlot(new SlotItemHandler(handler, 2 + col, 55 + col * 19, 24));
        }

        /* OUTPUT SLOTS */
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 2; row++) {
                this.addSlot(new OutputSlotItemHandler(handler, 6 + row * 8 + col, 17 + col * 19, 60 + row * 19));
            }
        }
    }

    public int getScaledArrowProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        int arrowPixelSize = 16;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    public int getScaledBliceFill() {
        int blice = getBlice();
        int maxBlice = getMaxBlice();
        int bliceBarLength = 65;

        return maxBlice != 0 ? blice * bliceBarLength / maxBlice : 0;
    }

    public int getBlice() {
        return blockEntity.getFluidAmount();
    }
    public int getMaxBlice() {
        return blockEntity.getFluidCapacity();
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get());
    }

    @Override
    public void removed(Player player) {
        blockEntity.triggerAnim("drawerController", "close_drawers");
        super.removed(player);
    }
}
