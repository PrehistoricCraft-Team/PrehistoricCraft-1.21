package net.seentro.prehistoriccraft.common.block.tissueExtractionChamber;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.seentro.prehistoriccraft.common.screen.tissueExtractionChamber.TissueExtractionChamberMenu;
import net.seentro.prehistoriccraft.core.json.tissueExtractionChamber.TimePeriodTissueLoader;
import net.seentro.prehistoriccraft.core.json.tissueExtractionChamber.TissueEntry;
import net.seentro.prehistoriccraft.core.systems.WeightedRandom;
import net.seentro.prehistoriccraft.registry.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

import net.neoforged.neoforge.items.IItemHandler;
import net.seentro.prehistoriccraft.utils.hopper.HopperItemHandlerWrapper;
import net.seentro.prehistoriccraft.utils.hopper.HopperRules;

import java.util.*;

public class TissueExtractionChamberBlockEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final int SLOT_BOTTLE_IN = 0;
    public static final int SLOT_BOTTLE_OUT = 1;
    public static final int SLOT_INPUT_1 = 2;
    public static final int SLOT_INPUT_4 = 5;
    public static final int SLOT_OUTPUT_START = 6;   // 6..21
    public static final int SLOT_OUTPUT_END = 21;

    //Bottle slot is slot 0
    //Bottle output is slot 1
    //Input starts at 1, ends at 6
    //Output starts at 6, ends at 22
    public final ItemStackHandler itemHandler = new ItemStackHandler(22) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (slot == SLOT_BOTTLE_IN) return stack.is(PrehistoricItems.BOTTLE_OF_BLICE.get());

            if (slot == SLOT_BOTTLE_OUT) return stack.is(Items.GLASS_BOTTLE);

            // SLOTS 2..5: inputs -> Only fossil samples or amber
            if (slot >= SLOT_INPUT_1 && slot <= SLOT_INPUT_4) {
                if (stack.is(PrehistoricTags.Items.FOSSIL_SAMPLES)
                    || stack.is(PrehistoricTags.Items.AMBER)){
                        return true;
                    }
                return false;
            }

            return true;
        }
    };

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 250;
    private boolean working;

    private final FluidTank FLUID_TANK = createFluidTank();

    private FluidTank createFluidTank() {
        return new FluidTank(2750) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if (!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return stack.is(PrehistoricFluidTypes.BLICE_FLUID_TYPE.get());
            }
        };
    }

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

        //Hoppers
        hopperRules.set(Direction.UP, new HopperRules.SideRule(
                slot -> slot == SLOT_BOTTLE_IN,
                stack -> stack.is(PrehistoricItems.BOTTLE_OF_BLICE.get()),
                slot -> false
        ));

        hopperRules.set(Direction.NORTH, new HopperRules.SideRule(
                TissueExtractionChamberBlockEntity::isInputSlot,
                TissueExtractionChamberBlockEntity::isAdequateFossilItem,
                slot -> false
        ));

        hopperRules.set(Direction.DOWN, new HopperRules.SideRule(
                slot -> false,
                stack -> false,
                TissueExtractionChamberBlockEntity::isOutputSlot
        ));
    }

    /* SAVING */

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.putInt("maxProgress", maxProgress);
        tag.putBoolean("working", working);
        tag = FLUID_TANK.writeToNBT(registries, tag);

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        maxProgress = tag.getInt("maxProgress");
        working = tag.getBoolean("working");
        FLUID_TANK.readFromNBT(registries, tag);
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

    public int getFluidAmount() {
        return FLUID_TANK.getFluidAmount();
    }

    public int getFluidCapacity() {
        return FLUID_TANK.getCapacity();
    }

    /* GECKOLIB */

    // PROCESSING
    private final RawAnimation START_WORKING = RawAnimation.begin().thenPlay("work_start").thenPlay("working");
    private final RawAnimation STOP_WORKING = RawAnimation.begin().thenPlay("work_end");

    // DRAWERS
    private final RawAnimation OPEN_DRAWERS = RawAnimation.begin().thenPlay("opening");
    private final RawAnimation CLOSE_DRAWERS = RawAnimation.begin().thenPlay("closing");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "processingController", state -> PlayState.STOP)
                .triggerableAnim("start_working", START_WORKING).triggerableAnim("stop_working", STOP_WORKING));

        controllers.add(new AnimationController<>(this, "drawerController", state -> PlayState.STOP)
                .triggerableAnim("open_drawers", OPEN_DRAWERS).triggerableAnim("close_drawers", CLOSE_DRAWERS));
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
    private int validInputSlot = -1;
    private static final Random random = new Random();

    public void tick(Level level, BlockPos pos, BlockState state) {
        handleBlice();

        if (!tryInitializeRecipe()) {
            fullReset();
            setChanged(level, pos, state);
            return;
        }

        if (!hasRecipe()) {
            ItemStack input = validInputSlot >= 0 ? itemHandler.getStackInSlot(validInputSlot) : ItemStack.EMPTY;
            if (input.isEmpty() || (!(input.is(PrehistoricTags.Items.FOSSIL_SAMPLES) && input.has(PrehistoricDataComponents.FOSSIL_QUALITY))
                    && !input.is(PrehistoricTags.Items.AMBER))) {
                reset();
            }

            this.stopTriggeredAnim("processingController", "start_working");
            this.triggerAnim("processingController", "stop_working");
            working = false;
            setChanged(level, pos, state);

            return;
        }

        working = true;
        this.triggerAnim("processingController", "start_working");
        progress++;
        setChanged(level, pos, state);

        if (progress >= maxProgress) {
            craft();
            reset();
        }
    }

    private void handleBlice() {
        if (hasFluidInFluidSlot()) {
            transferItemFluidToMachine();
        }
    }

    private boolean hasFluidInFluidSlot() {
        return !itemHandler.getStackInSlot(0).isEmpty()
                && itemHandler.getStackInSlot(0).getCapability(Capabilities.FluidHandler.ITEM, null) != null
                && !itemHandler.getStackInSlot(0).getCapability(Capabilities.FluidHandler.ITEM, null).getFluidInTank(0).isEmpty();
    }

    private void transferItemFluidToMachine() {
        FluidActionResult testFluidResult = FluidUtil.tryEmptyContainer(itemHandler.getStackInSlot(0), FLUID_TANK, Integer.MAX_VALUE, null, false);

        if (canOutput(testFluidResult.result, 1)) {
            FluidActionResult fluidResult = FluidUtil.tryEmptyContainer(itemHandler.getStackInSlot(0), FLUID_TANK, Integer.MAX_VALUE, null, true);

            if (fluidResult.result != ItemStack.EMPTY) {
                itemHandler.extractItem(0, 1, false);
                itemHandler.insertItem(1, fluidResult.result, false);
            }
        }
    }

    private boolean tryInitializeRecipe() {
        for (int i = 2; i < 6; i++) {
            ItemStack input = itemHandler.getStackInSlot(i);
            if ((input.is(PrehistoricTags.Items.FOSSIL_SAMPLES) && input.has(PrehistoricDataComponents.FOSSIL_QUALITY))
                    || input.is(PrehistoricTags.Items.AMBER)) {
                if (validInputSlot == i) return true;

                validInputSlot = i;
                return true;
            }
        }
        return false;
    }

    private boolean hasRecipe() {
        if (validInputSlot < 0) return false;

        ItemStack input = itemHandler.getStackInSlot(validInputSlot);
        if (input.isEmpty() || (!(input.is(PrehistoricTags.Items.FOSSIL_SAMPLES) && input.has(PrehistoricDataComponents.FOSSIL_QUALITY))
                && !input.is(PrehistoricTags.Items.AMBER))) return false;

        if (getFluidAmount() <= 45) return false;

        if (result == null) {
            result = simulateOutput(input);
        }

        return canOutput(result);
    }

    private ItemStack simulateOutput(ItemStack input) {
        double wasteChance = input.is(PrehistoricTags.Items.FOSSIL_SAMPLES)
                ? getWasteChance(input.getOrDefault(PrehistoricDataComponents.FOSSIL_QUALITY, "decent"))
                : 0.5;

        if (random.nextDouble() >= wasteChance) {
            WeightedRandom<ItemLike> waste = new WeightedRandom<>();
            waste.addItem(Blocks.GRAVEL, 35);
            waste.addItem(Items.FLINT, 35);
            waste.addItem(Items.BONE, 20);
            waste.addItem(Blocks.BONE_BLOCK, 10);

            return new ItemStack(waste.getRandomItem());
        }

        String timePeriod = getTimePeriod(input);

        return getRandomTissue(timePeriod, random)
                .map(tissueEntry -> {
                    Item tissueType = switch (tissueEntry.tissueType()) {
                        case "animal" -> PrehistoricItems.ANIMAL_TISSUE.get();
                        case "plant" -> PrehistoricItems.PLANT_TISSUE.get();
                        case "fungus" -> PrehistoricItems.BLOOD_CELL.get();
                        default -> Items.AIR;
                    };
                    ItemStack toOutput = new ItemStack(tissueType);
                    toOutput.set(PrehistoricDataComponents.FOSSIL_SPECIES, tissueEntry.name());
                    return toOutput;
                })
                .orElse(ItemStack.EMPTY);
    }

    private boolean canOutput(ItemStack output) {
        return canOutput(output, 6, 22);
    }

    private boolean canOutput(ItemStack output, int slot) {
        ItemStack outputCopy = itemHandler.insertItem(slot, output.copy(), true);
        return outputCopy.isEmpty();
    }

    private boolean canOutput(ItemStack output, int minSlot, int maxSlot) {
        for (int i = minSlot; i < maxSlot; i++) {
            ItemStack outputCopy = itemHandler.insertItem(i, output.copy(), true);
            if (outputCopy.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void craft() {
        if (result == null) return;

        itemHandler.extractItem(validInputSlot, 1, false);
        FLUID_TANK.drain(45, IFluidHandler.FluidAction.EXECUTE);

        for (int i = 6; i < 22; i++) {
            ItemStack leftover = itemHandler.insertItem(i, result.copy(), false);
            if (leftover.isEmpty()) break;
        }
    }

    private double getWasteChance(String quality) {
        return switch (quality) {
            case "damaged" -> 0.10;
            case "incomplete" -> 0.15;
            case "fragmentary" -> 0.25;
            case "decent" -> 0.45;
            case "rich" -> 0.75;
            default -> throw new IllegalStateException("Unexpected value: " + quality);
        };
    }

    private String getTimePeriod(ItemStack fossil) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(fossil.getItem());

        String path = id.getPath();
        if (path.endsWith("_fossil_sample")) {
            return path.substring(0, path.length() - "_fossil_sample".length());
        }
        return path;
    }

    private void reset() {
        progress = 0;
        validInputSlot = -1;
        result = null;
    }

    private void fullReset() {
        progress = 0;
        validInputSlot = -1;
        result = null;

        if (working) {
            this.stopTriggeredAnim("processingController", "start_working");
            this.triggerAnim("processingController", "stop_working");
            working = false;
        }
    }

    public Optional<TissueEntry> getRandomTissue(String timePeriod, Random random) {
        List<TissueEntry> pool = TimePeriodTissueLoader.TISSUE_POOLS.getOrDefault(timePeriod.toLowerCase(), List.of());
        if (pool.isEmpty()) return Optional.empty();
        return Optional.of(pool.get(random.nextInt(pool.size())));
    }

    // --- Hopper ---

    private final HopperRules hopperRules = new HopperRules();
    private final EnumMap<Direction, IItemHandler> hopperHandlers = new EnumMap<>(Direction.class);

    private static boolean isInputSlot(int slot) {
        return slot >= SLOT_INPUT_1 && slot <= SLOT_INPUT_4;
    }

    private static boolean isOutputSlot(int slot) {
        return slot >= SLOT_OUTPUT_START && slot <= SLOT_OUTPUT_END;
    }

    private static boolean isAdequateFossilItem(ItemStack stack) {
        return (stack.is(PrehistoricTags.Items.FOSSIL_SAMPLES))
                || stack.is(PrehistoricTags.Items.AMBER);
    }

        private IItemHandler getLogicalSideHandler(Direction side) {
        return hopperHandlers.computeIfAbsent(
                side,
                s -> new HopperItemHandlerWrapper(itemHandler, hopperRules, s)
        );
    }

    public @Nullable IItemHandler getHopperItemHandler(@Nullable Direction worldSide) {
        if (worldSide == null) return itemHandler;

        return getLogicalSideHandler(worldSide);
    }

}
