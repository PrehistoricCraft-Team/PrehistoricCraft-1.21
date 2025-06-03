package net.seentro.prehistoriccraft.common.block.tissueExtractionChamber;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.syncher.EntityDataSerializers;
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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.screen.tissueExtractionChamber.TissueExtractionChamberMenu;
import net.seentro.prehistoriccraft.core.enums.TissueExtractionChamberStates;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.seentro.prehistoriccraft.core.enums.TissueExtractionChamberStates.*;

public class TissueExtractionChamberBlockEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public final ItemStackHandler itemHandler = new ItemStackHandler(21) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return super.isItemValid(slot, stack);
        }
    };

    //Bottle slot is slot 0
    //Input starts at 0, ends at 5
    //Output starts at 5, ends at 21

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 120;

    public TissueExtractionChamberBlockEntity(BlockPos pos, BlockState blockState) {
        super(PrehistoricBlockEntityTypes.TISSUE_EXTRACTION_CHAMBER_BLOCK_ENTITY.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> TissueExtractionChamberBlockEntity.this.progress;
                    case 1 -> TissueExtractionChamberBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> TissueExtractionChamberBlockEntity.this.progress = value;
                    case 1 -> TissueExtractionChamberBlockEntity.this.maxProgress = value;
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
        return Component.translatable("block.prehistoriccraft.tissue_extraction_chamber");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new TissueExtractionChamberMenu(containerId, playerInventory, this, this.data);
    }

    /* GECKOLIB */

    private final RawAnimation START_TO_WORK = RawAnimation.begin().then("work_start", Animation.LoopType.PLAY_ONCE).thenLoop("working");
    private final RawAnimation STOP_WORKING = RawAnimation.begin().then("work_end", Animation.LoopType.PLAY_ONCE);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::progressPredicate));
    }

    private <T extends GeoAnimatable> PlayState progressPredicate(AnimationState<T> state) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtil.getCurrentTick();
    }

    /* INVENTORY & PROCESSING */

    public void drop() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, container);
    }

    private int validInputSlot = -1;

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!tryInitializeRecipe()) {
            fullReset(level, pos, state);
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
        for (int i = 1; i < 5; i++) {
            ItemStack input = itemHandler.getStackInSlot(i);
            if (input.is(Items.STICK)) {
                validInputSlot = i;
                return true;
            }
        }
        return false;
    }

    private boolean hasRecipe() {
        ItemStack input = itemHandler.getStackInSlot(validInputSlot);

        if (validInputSlot < 0) return false;
        if (input.isEmpty() || !input.is(Items.STICK)) return false;

        //Todo: Check if it has the required fluid

        return canOutput(new ItemStack(Items.COBBLESTONE));
    }

    private boolean canOutput(ItemStack output) {
        for (int i = 5; i < 21; i++) {
            ItemStack outputCopy = itemHandler.insertItem(i, output.copy(), true);
            if (outputCopy.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void craft() {
        itemHandler.extractItem(validInputSlot, 1, false);
        ItemStack toOutput = new ItemStack(Items.COBBLESTONE);

        for (int i = 5; i < 21; i++) {
            ItemStack leftover = itemHandler.insertItem(i, toOutput.copy(), false);
            if (leftover.isEmpty()) {
                break;
            }
        }
    }

    private void reset() {
        progress = 0;
        validInputSlot = -1;
    }

    private void fullReset(Level level, BlockPos pos, BlockState state) {
        progress = 0;
        validInputSlot = -1;
        setChanged(level, pos, state);
    }
}
