package net.seentro.prehistoriccraft.common.block.dnaRecombinator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.dnaSeparationFilter.DNASeparationFilterBlock;
import net.seentro.prehistoriccraft.common.screen.dnaRecombinator.DNARecombinatorMenu;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;
import net.seentro.prehistoriccraft.registry.PrehistoricDataComponents;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;
import net.seentro.prehistoriccraft.utils.hopper.HopperItemHandlerWrapper;
import net.seentro.prehistoriccraft.utils.hopper.HopperRules;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

import java.util.EnumMap;

public class DNARecombinatorBlockEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public final ItemStackHandler itemHandler = new ItemStackHandler(10) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot != 0;
        }
    };

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 400;
    protected int working;

    //Petri Dish output slot is 0
    //Petri Dish input slots is 1,2
    //Magma slot is 3
    //Left ice slots are 4,5,6
    //Right ice slots are 7,8,9

    private final HopperRules hopperRules = new HopperRules();
    private final EnumMap<Direction, IItemHandler> hopperHandlers = new EnumMap<>(Direction.class);

    public DNARecombinatorBlockEntity(BlockPos pos, BlockState blockState) {
        super(PrehistoricBlockEntityTypes.DNA_RECOMBINATOR_BLOCK_ENTITY.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> DNARecombinatorBlockEntity.this.progress;
                    case 1 -> DNARecombinatorBlockEntity.this.maxProgress;
                    case 2 -> DNARecombinatorBlockEntity.this.working;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> DNARecombinatorBlockEntity.this.progress = value;
                    case 1 -> DNARecombinatorBlockEntity.this.maxProgress = value;
                    case 2 -> DNARecombinatorBlockEntity.this.working = value;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        hopperRules.set(Direction.WEST, new HopperRules.SideRule(
                slot -> slot == 3,
                stack -> stack.is(Items.MAGMA_CREAM),
                slot -> false
        ));

        hopperRules.set(Direction.EAST, new HopperRules.SideRule(
                DNARecombinatorBlockEntity::isIceBlockSlot,
                stack -> stack.is(Items.ICE),
                slot -> false
        ));

        hopperRules.set(Direction.SOUTH, new HopperRules.SideRule(
                slot -> slot == 1 || slot == 2,
                stack -> stack.is(PrehistoricItems.DNA_IN_A_PETRI_DISH.get()),
                slot -> false
        ));

        hopperRules.set(Direction.DOWN, new HopperRules.SideRule(
                slot -> false,
                stack -> false,
                slot -> slot == 0
        ));
    }

    /* SAVING */

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.putInt("maxProgress", maxProgress);
        tag.putInt("working", working);

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        maxProgress = tag.getInt("maxProgress");
        working = tag.getInt("working");

        super.loadAdditional(tag, registries);
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
        return Component.translatable("block.prehistoriccraft.dna_recombinator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new DNARecombinatorMenu(containerId, playerInventory, this, this.data);
    }

    /* GECKOLIB */

    private final RawAnimation WORKING = RawAnimation.begin().thenPlay("working");
    private final RawAnimation OPEN_DOORS = RawAnimation.begin().thenPlay("open");
    private final RawAnimation CLOSE_DOORS = RawAnimation.begin().thenPlay("close");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> PlayState.STOP)
                .triggerableAnim("open_doors", OPEN_DOORS).triggerableAnim("close_doors", CLOSE_DOORS).triggerableAnim("working", WORKING));
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

    private @Nullable ItemStack result;

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!hasRecipe()) {
            this.stopTriggeredAnim("controller", "working");
            working = 0;
            progress = 0;
            result = null;
            setChanged(level, pos, state);
            return;
        }

        working = 1;
        this.triggerAnim("controller", "working");
        progress++;
        setChanged(level, pos, state);

        if (progress >= maxProgress) {
            craft();
            progress = 0;
            working = 0;
            result = null;
        }
    }

    private void craft() {
        if (result == null) return;

        int leftSideIceSlot = getSlotForIceLeftSide();
        int rightSideIceSlot = getSlotForIceRightSide();

        itemHandler.extractItem(leftSideIceSlot, 1, false);
        itemHandler.extractItem(rightSideIceSlot, 1, false);
        itemHandler.extractItem(3, 1, false);

        itemHandler.extractItem(1, 1, false);
        itemHandler.extractItem(2, 1, false);

        int outputStackSize = itemHandler.getStackInSlot(0).getCount();
        result.setCount(outputStackSize + 1);
        itemHandler.setStackInSlot(0, result);
    }

    private boolean hasRecipe() {
        ItemStack leftPetriDish = itemHandler.getStackInSlot(1);
        ItemStack rightPetriDish = itemHandler.getStackInSlot(2);

        if (!(leftPetriDish.is(PrehistoricItems.DNA_IN_A_PETRI_DISH) && leftPetriDish.has(PrehistoricDataComponents.DNA_QUALITY))) return false;
        if (!(rightPetriDish.is(PrehistoricItems.DNA_IN_A_PETRI_DISH) && rightPetriDish.has(PrehistoricDataComponents.DNA_QUALITY))) return false;

        String leftPetriDishSpecies = leftPetriDish.getOrDefault(PrehistoricDataComponents.FOSSIL_SPECIES, "null");
        String rightPetriDishSpecies = rightPetriDish.getOrDefault(PrehistoricDataComponents.FOSSIL_SPECIES, "null");

        if (leftPetriDishSpecies.equals("null") || rightPetriDishSpecies.equals("null")) return false;
        if (!leftPetriDishSpecies.equals(rightPetriDishSpecies)) return false;

        if (!(itemHandler.getStackInSlot(3).is(Items.MAGMA_CREAM))) return false;
        if (!(hasIceInLeftSide() && hasIceInRightSide())) return false;

        if (result == null)
            result = simulateOutput();

        return canOutput(result);
    }

    private boolean canOutput(ItemStack result) {
        ItemStack outputStack = itemHandler.getStackInSlot(0);
        String resultSpecies = result.getOrDefault(PrehistoricDataComponents.FOSSIL_SPECIES, "null");
        int resultQuality = result.getOrDefault(PrehistoricDataComponents.DNA_QUALITY, 0);

        String outputStackSpecies = outputStack.getOrDefault(PrehistoricDataComponents.FOSSIL_SPECIES, "null");
        int outputStackQuality = outputStack.getOrDefault(PrehistoricDataComponents.DNA_QUALITY, 0);

        int outputStackSize = outputStack.getCount();

        if (outputStack.isEmpty()) return true;

        if (!outputStack.is(PrehistoricItems.DNA_IN_A_PETRI_DISH) || !outputStackSpecies.equals(resultSpecies) || outputStackQuality != resultQuality)
            return false;

        if (outputStackSize >= result.getMaxStackSize()) return false;

        return true;
    }

    private ItemStack simulateOutput() {
        int leftPetriDishQuality = itemHandler.getStackInSlot(1).getOrDefault(PrehistoricDataComponents.DNA_QUALITY.get(), 0);
        int rightPetriDishQuality = itemHandler.getStackInSlot(2).getOrDefault(PrehistoricDataComponents.DNA_QUALITY.get(), 0);
        int quality = Math.min(leftPetriDishQuality + rightPetriDishQuality, 100);

        String species = itemHandler.getStackInSlot(1).getOrDefault(PrehistoricDataComponents.FOSSIL_SPECIES, "null");
        if (species.equals("null")) {
            PrehistoricCraft.LOGGER.error("Species for the DNA Recombinator was null!");
            return ItemStack.EMPTY;
        }

        ItemStack result = new ItemStack(PrehistoricItems.DNA_IN_A_PETRI_DISH.get());
        result.set(PrehistoricDataComponents.DNA_QUALITY, quality);
        result.set(PrehistoricDataComponents.FOSSIL_SPECIES, species);

        return result;
    }

    private boolean hasIceInLeftSide() {
        for (int i = 4; i < 7; i++) {
            if (itemHandler.getStackInSlot(i).is(Items.ICE)) return true;
        }

        return false;
    }

    private boolean hasIceInRightSide() {
        for (int i = 7; i < 10; i++) {
            if (itemHandler.getStackInSlot(i).is(Items.ICE)) return true;
        }

        return false;
    }

    private int getSlotForIceLeftSide() {
        for (int i = 4; i < 7; i++) {
            if (itemHandler.getStackInSlot(i).is(Items.ICE)) return i;
        }

        return 0;
    }

    private int getSlotForIceRightSide() {
        for (int i = 7; i < 10; i++) {
            if (itemHandler.getStackInSlot(i).is(Items.ICE)) return i;
        }

        return 0;
    }

    /* HOPPER */

    private static boolean isIceBlockSlot(int slot) {
        return slot >= 4 && slot <= 9;
    }

    private IItemHandler getLogicalSideHandler(Direction logicalSide) {
        return hopperHandlers.computeIfAbsent(
                logicalSide,
                side -> new HopperItemHandlerWrapper(itemHandler, hopperRules, side)
        );
    }

    public @Nullable IItemHandler getHopperItemHandler(@Nullable Direction worldSide) {
        if (worldSide == null) return itemHandler;

        Direction facing = this.getBlockState().getValue(DNASeparationFilterBlock.FACING);
        Direction logicalSide;

        if (worldSide == Direction.UP || worldSide == Direction.DOWN) {
            logicalSide = worldSide;
        } else {
            if (worldSide == facing.getCounterClockWise()) {
                logicalSide = Direction.WEST;
            } else if (worldSide == facing.getClockWise()) {
                logicalSide = Direction.EAST;
            } else if (worldSide == facing.getOpposite()) {
                logicalSide = Direction.SOUTH;
            } else {
                logicalSide = null;
            }
        }

        if (logicalSide == null) return null;

        return getLogicalSideHandler(logicalSide);
    }
}
