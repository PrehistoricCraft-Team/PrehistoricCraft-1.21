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
import net.seentro.prehistoriccraft.common.screen.MachineMenu;
import net.seentro.prehistoriccraft.common.screen.slotItemHandlers.OutputSlotItemHandler;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricMenuTypes;
import org.jetbrains.annotations.NotNull;

public class FossilAnalysisTableMenu extends MachineMenu<FossilAnalysisTableBlockEntity> {
    public final FossilAnalysisTableBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public FossilAnalysisTableMenu(int containerId, Inventory inv, FriendlyByteBuf byteBuf) {
        this(containerId, inv, inv.player.level().getBlockEntity(byteBuf.readBlockPos()), new SimpleContainerData(2));
    }

    public FossilAnalysisTableMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
        super(PrehistoricMenuTypes.FOSSIL_ANALYSIS_TABLE_MENU.get(), containerId, (FossilAnalysisTableBlockEntity) blockEntity, data, 19);
        this.blockEntity = (FossilAnalysisTableBlockEntity) blockEntity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);

        IItemHandler handler = this.blockEntity.itemHandler;

        setPosValues(10, 107, 10, 165);

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
                this.addSlot(new OutputSlotItemHandler(handler, 10 + row * 3 + col, 104 + col * 18, 34 + row * 18));
            }
        }
    }

    public int getScaledArrowProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        int arrowPixelSize = 17;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }


    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get());
    }
}
