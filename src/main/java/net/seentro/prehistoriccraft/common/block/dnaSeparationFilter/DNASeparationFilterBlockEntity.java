package net.seentro.prehistoriccraft.common.block.dnaSeparationFilter;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.screen.dnaSeparationFilter.DNASeparationFilterMenu;
import net.seentro.prehistoriccraft.data.FossilSpeciesLoader;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;
import net.seentro.prehistoriccraft.registry.PrehistoricDataComponents;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;
import net.seentro.prehistoriccraft.registry.PrehistoricTags;
import net.seentro.prehistoriccraft.utils.FossilSpeciesData;
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
import java.util.Random;

public class DNASeparationFilterBlockEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {

    public static final int SLOT_PETRI = 0;
    public static final int SLOT_TISSUE_1 = 1;
    public static final int SLOT_TISSUE_2 = 2;
    public static final int SLOT_TISSUE_3 = 3;
    public static final int SLOT_TISSUE_4 = 4;
    public static final int SLOT_TISSUE_5 = 5;
    public static final int SLOT_TISSUE_6 = 6;
    public static final int SLOT_CHARCOAL = 7;
    public static final int SLOT_NANO = 8;
    public static final int SLOT_OUTPUT_1 = 9;
    public static final int SLOT_OUTPUT_2 = 10;
    public static final int SLOT_OUTPUT_3 = 11;
    public static final int SLOT_OUTPUT_4 = 12;
    public static final int SLOT_OUTPUT_5 = 13;
    public static final int SLOT_OUTPUT_6 = 14;
    public static final int SLOT_FLUID_IO = 15;

    private static final int INVENTORY_SIZE = 16;
    private static final int WATER_PER_PROCESS = 100;
    private static final int TANK_CAPACITY = 4000;
    private static final int PROCESS_TIME = 160;
    private static final double CONTAMINATION_CHANCE = 0.20;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = PROCESS_TIME;
    protected int working = 0;

    private boolean doorsOpen = false;
    private boolean workingPlaying = false;
    private boolean wasViewed = false;

    private final RawAnimation WORKING = RawAnimation.begin().thenPlay("working");
    private final RawAnimation OPEN_DOORS = RawAnimation.begin().thenPlay("open");
    private final RawAnimation CLOSE_DOORS = RawAnimation.begin().thenPlay("close");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public final ItemStackHandler itemHandler = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (slot >= SLOT_TISSUE_1 && slot <= SLOT_TISSUE_6) {
                return stack.is(PrehistoricTags.Items.TISSUES);
            }
            if (slot >= SLOT_OUTPUT_1 && slot <= SLOT_OUTPUT_6) {
                return true;
            }
            return switch (slot) {
                case SLOT_PETRI -> stack.is(PrehistoricItems.PETRI_DISH.get());
                case SLOT_CHARCOAL -> stack.is(Items.CHARCOAL);
                case SLOT_NANO -> stack.is(PrehistoricItems.NANOPOD.get());
                case SLOT_FLUID_IO -> stack.getCapability(Capabilities.FluidHandler.ITEM, null) != null;
                default -> false;
            };
        }

        @Override
        public int getSlotLimit(int slot) {
            return slot == SLOT_NANO ? 1 : super.getSlotLimit(slot);
        }

        @Override
        public void deserializeNBT(HolderLookup.Provider registries, CompoundTag nbt) {
            super.deserializeNBT(registries, nbt);
            if (this.getSlots() != INVENTORY_SIZE) {
                var old = this.stacks;
                var resized = net.minecraft.core.NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
                for (int i = 0; i < Math.min(old.size(), INVENTORY_SIZE); i++) {
                    resized.set(i, old.get(i));
                }
                this.stacks = resized;
            }
        }
    };

    private final FluidTank FLUID_TANK = new FluidTank(TANK_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.WATER;
        }
    };

    private static final Random random = new Random();

    private final HopperRules hopperRules = new HopperRules();
    private final EnumMap<Direction, IItemHandler> hopperHandlers = new EnumMap<>(Direction.class);

    public DNASeparationFilterBlockEntity(BlockPos pos, BlockState state) {
        super(PrehistoricBlockEntityTypes.DNA_SEPARATION_FILTER_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> DNASeparationFilterBlockEntity.this.progress;
                    case 1 -> DNASeparationFilterBlockEntity.this.maxProgress;
                    case 2 -> DNASeparationFilterBlockEntity.this.working;
                    case 3 -> DNASeparationFilterBlockEntity.this.getFluidAmount();
                    case 4 -> DNASeparationFilterBlockEntity.this.getFluidCapacity();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> DNASeparationFilterBlockEntity.this.progress = value;
                    case 1 -> DNASeparationFilterBlockEntity.this.maxProgress = value;
                    case 2 -> DNASeparationFilterBlockEntity.this.working = value;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };

        hopperRules.set(Direction.UP, new HopperRules.SideRule(
                slot -> isTissueSlot(slot),
                stack -> stack.is(PrehistoricTags.Items.TISSUES),
                slot -> false
        ));

        hopperRules.set(Direction.DOWN, new HopperRules.SideRule(
                slot -> false,
                stack -> false,
                DNASeparationFilterBlockEntity::isOutputSlot
        ));

        hopperRules.set(Direction.WEST, new HopperRules.SideRule(
            slot -> slot == SLOT_PETRI,
            stack -> stack.is(PrehistoricItems.PETRI_DISH.get()),
            slot -> false
        ));

        hopperRules.set(Direction.EAST, new HopperRules.SideRule(
            slot -> slot == SLOT_CHARCOAL,
            stack -> stack.is(Items.CHARCOAL),
            slot -> false
        ));

        hopperRules.set(Direction.SOUTH, new HopperRules.SideRule(
            slot -> slot == SLOT_NANO,
            stack -> stack.is(PrehistoricItems.NANOPOD.get()),
            slot -> false
        ));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.putInt("maxProgress", maxProgress);
        tag.putInt("working", working);
        FLUID_TANK.writeToNBT(registries, tag);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        maxProgress = tag.getInt("maxProgress");
        working = tag.getInt("working");
        FLUID_TANK.readFromNBT(registries, tag);
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
        return Component.translatable("block.prehistoriccraft.dna_separation_filter");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, net.minecraft.world.entity.player.Inventory inv, Player player) {
        return new DNASeparationFilterMenu(containerId, inv, this, this.data);
    }

    public int getFluidAmount() {
        return FLUID_TANK.getFluidAmount();
    }

    public int getFluidCapacity() {
        return FLUID_TANK.getCapacity();
    }

    private void handleWaterIO() {
        ItemStack io = itemHandler.getStackInSlot(SLOT_FLUID_IO);
        if (io.isEmpty()) return;
        var cap = io.getCapability(Capabilities.FluidHandler.ITEM, null);
        if (cap == null) return;
        FluidActionResult res = FluidUtil.tryEmptyContainer(io, FLUID_TANK, Integer.MAX_VALUE, null, true);
        if (res.isSuccess() && !res.result.isEmpty()) {
            itemHandler.setStackInSlot(SLOT_FLUID_IO, res.result);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> PlayState.CONTINUE)
                .triggerableAnim("open_doors", OPEN_DOORS)
                .triggerableAnim("close_doors", CLOSE_DOORS)
                .triggerableAnim("working", WORKING)
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtil.getCurrentTick();
    }

    private void handleOpenCloseAnimations(boolean viewing) {
        if (working == 1) {
            if (!workingPlaying) {
                this.triggerAnim("controller", "working");
                workingPlaying = true;
            }
            wasViewed = viewing;
            return;
        }
        if (viewing && !wasViewed && !doorsOpen) {
            this.triggerAnim("controller", "open_doors");
            doorsOpen = true;
        } else if (!viewing && wasViewed && doorsOpen) {
            this.triggerAnim("controller", "close_doors");
            doorsOpen = false;
        }
        wasViewed = viewing;
        if (workingPlaying) {
            this.stopTriggeredAnim("controller", "working");
            workingPlaying = false;
        }
    }

    private void stopWorkingAnimIfNeeded() {
        if (workingPlaying) {
            this.stopTriggeredAnim("controller", "working");
            workingPlaying = false;
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        handleWaterIO();
        if (!hasAllInputs()) {
            stopWorkingAnimIfNeeded();
            working = 0;
            progress = 0;
            setChanged(level, pos, state);
            return;
        }
        working = 1;
        handleOpenCloseAnimations(isBeingViewed(level));
        progress++;
        setChanged(level, pos, state);
        if (progress >= maxProgress) {
            doProcess();
            progress = 0;
        }
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
    }

    private static final int[] TISSUE_SLOTS = {
            SLOT_TISSUE_1, SLOT_TISSUE_2, SLOT_TISSUE_3,
            SLOT_TISSUE_4, SLOT_TISSUE_5, SLOT_TISSUE_6
    };
    private static final int[] OUTPUT_SLOTS = {
            SLOT_OUTPUT_1, SLOT_OUTPUT_2, SLOT_OUTPUT_3,
            SLOT_OUTPUT_4, SLOT_OUTPUT_5, SLOT_OUTPUT_6
    };

    private Integer firstValidTissueSlot() {
        for (int s : TISSUE_SLOTS) {
            ItemStack st = itemHandler.getStackInSlot(s);
            if (!st.isEmpty() && st.is(PrehistoricTags.Items.TISSUES)) return s;
        }
        return null;
    }

    private Integer findOutputSlotFor(ItemStack stack) {
        for (int s : OUTPUT_SLOTS) {
            ItemStack cur = itemHandler.getStackInSlot(s);
            if (!cur.isEmpty() && ItemStack.isSameItemSameComponents(cur, stack) && cur.getCount() < cur.getMaxStackSize()) {
                return s;
            }
        }
        for (int s : OUTPUT_SLOTS) {
            if (itemHandler.getStackInSlot(s).isEmpty()) return s;
        }
        return null;
    }

    private boolean hasAllInputs() {
        ItemStack petri = itemHandler.getStackInSlot(SLOT_PETRI);
        ItemStack charcoal = itemHandler.getStackInSlot(SLOT_CHARCOAL);
        ItemStack nano = itemHandler.getStackInSlot(SLOT_NANO);
        Integer tissueSlot = firstValidTissueSlot();
        if (petri.isEmpty() || !petri.is(PrehistoricItems.PETRI_DISH.get())) return false;
        if (tissueSlot == null) return false;
        if (charcoal.isEmpty() || !charcoal.is(Items.CHARCOAL)) return false;
        if (nano.isEmpty() || !nano.is(PrehistoricItems.NANOPOD.get())) return false;
        if (getFluidAmount() < WATER_PER_PROCESS) return false;
        ItemStack preview = makeOutput(itemHandler.getStackInSlot(tissueSlot));
        Integer outSlot = findOutputSlotFor(preview);
        return outSlot != null;
    }

    private void doProcess() {
        Integer tissueSlotCheck = firstValidTissueSlot();
        if (tissueSlotCheck == null) {
            working = 0;
            return;
        }
        itemHandler.extractItem(SLOT_PETRI, 1, false);
        Integer tissueSlot = firstValidTissueSlot();
        ItemStack consumedTissue = tissueSlot != null ? itemHandler.extractItem(tissueSlot, 1, false) : ItemStack.EMPTY;
        itemHandler.extractItem(SLOT_CHARCOAL, 1, false);
        FLUID_TANK.drain(WATER_PER_PROCESS, IFluidHandler.FluidAction.EXECUTE);
        damageNano();
        consumedTissue.set(PrehistoricDataComponents.FOSSIL_SPECIES, "antarctilamna");
        ItemStack out = makeOutput(consumedTissue.isEmpty() ? new ItemStack(Items.AIR) : consumedTissue);
        Integer outSlot = findOutputSlotFor(out);
        if (outSlot != null) {
            ItemStack remainder = itemHandler.insertItem(outSlot, out, false);
            if (!remainder.isEmpty()) {
                Integer fallback = findOutputSlotFor(remainder);
                if (fallback != null) {
                    itemHandler.insertItem(fallback, remainder, false);
                }
            }
        }
        if (!hasAllInputs()) {
            working = 0;
        }
    }

    private void damageNano() {
        ItemStack nano = itemHandler.getStackInSlot(SLOT_NANO);
        if (nano.isEmpty() || !nano.isDamageableItem()) return;
        int newDamage = nano.getDamageValue() + 1;
        int maxDamage = nano.getMaxDamage();
        if (newDamage >= maxDamage) {
            level.playSound(null, worldPosition, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            itemHandler.setStackInSlot(SLOT_NANO, ItemStack.EMPTY);
        } else {
            nano.setDamageValue(newDamage);
            itemHandler.setStackInSlot(SLOT_NANO, nano);
        }
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    private ItemStack makeOutput(ItemStack tissue) {
        ItemStack out = new ItemStack(PrehistoricItems.DNA_IN_A_PETRI_DISH.get());
        int quality = calcQuality(tissue);
        boolean direct = tissue.is(PrehistoricItems.BLOOD_CELL);
        if (direct) quality = 100;
        boolean contaminated = random.nextDouble() < CONTAMINATION_CHANCE;
        String species = tissue.getOrDefault(PrehistoricDataComponents.FOSSIL_SPECIES.get(), "unknown");
        out.set(PrehistoricDataComponents.DNA_QUALITY.get(), quality);
        out.set(PrehistoricDataComponents.DNA_CONTAMINATED.get(), contaminated);
        out.set(PrehistoricDataComponents.FOSSIL_SPECIES.get(), species);
        return out;
    }

    public static class Range {
        public int min;
        public int max;

        public Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    private int calcQuality(ItemStack tissue) {
        Range r = getQualityRangeFromSpeciesJson(tissue);
        if (r.max < r.min) r.max = r.min;
        return r.min + random.nextInt(r.max - r.min + 1);
    }

    private Range getQualityRangeFromSpeciesJson(ItemStack stack) {
        String speciesKey = stack.get(PrehistoricDataComponents.FOSSIL_SPECIES.get());
        if (speciesKey == null) {
            return new Range(1, 20);
        }
        ResourceLocation id;
        if (speciesKey.contains(":")) {
            id = ResourceLocation.tryParse(speciesKey);
        } else {
            id = ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, speciesKey);
        }
        if (id == null) {
            PrehistoricCraft.LOGGER.error("Invalid ResourceLocation for FOSSIL_SPECIES: {}", speciesKey);
            return new Range(1, 20);
        }
        FossilSpeciesData data = FossilSpeciesLoader.INSTANCE.get(id);
        if (data == null || data.chances == null) {
            return new Range(1, 20);
        }
        if (stack.is(PrehistoricTags.Items.TISSUES) && data.chances.tissue != null) {
            return new Range(data.chances.tissue.minProb, data.chances.tissue.maxProb);
        }
        if (stack.is(PrehistoricTags.Items.AMBER) && data.chances.amber != null) {
            return new Range(data.chances.amber.minProb, data.chances.amber.maxProb);
        }
        return new Range(1, 20);
    }

    public boolean tryInsertWaterFromBucket(Player player, net.minecraft.world.InteractionHand hand) {
        if (level == null || level.isClientSide()) return false;
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.is(Items.WATER_BUCKET)) return false;
        if (FLUID_TANK.getFluidAmount() > TANK_CAPACITY - 1000) return false;
        FLUID_TANK.fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
        if (!player.getAbilities().instabuild) {
            player.setItemInHand(hand, new ItemStack(Items.BUCKET));
        }
        level.playSound(null, worldPosition, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        return true;
    }

    public void drop() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    private boolean isBeingViewed(Level level) {
        for (Player player : level.players()) {
            if (player.containerMenu instanceof DNASeparationFilterMenu menu && menu.getBlockEntity() == this) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTissueSlot(int slot) {
        return slot >= SLOT_TISSUE_1 && slot <= SLOT_TISSUE_6;
    }

    private static boolean isOutputSlot(int slot) {
        return slot >= SLOT_OUTPUT_1 && slot <= SLOT_OUTPUT_6;
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
