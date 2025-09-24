package net.seentro.prehistoriccraft.common.block.tissueExtractionChamber;

import net.minecraft.core.BlockPos;
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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.seentro.prehistoriccraft.common.screen.tissueExtractionChamber.TissueExtractionChamberMenu;
import net.seentro.prehistoriccraft.core.json.tissueExtractionChamber.TimePeriodTissueLoader;
import net.seentro.prehistoriccraft.core.json.tissueExtractionChamber.TissueEntry;
import net.seentro.prehistoriccraft.core.systems.WeightedRandom;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;
import net.seentro.prehistoriccraft.registry.PrehistoricDataComponents;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;
import net.seentro.prehistoriccraft.registry.PrehistoricTags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

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
            return slot == 0 ? stack.getItem() == PrehistoricItems.BOTTLE_OF_BLICE.get() : super.isItemValid(slot, stack);
        }
    };

    //Bottle slot is slot 0
    //Input starts at 0, ends at 5
    //Output starts at 5, ends at 21

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 250;
    private int blice = 0;
    private int maxBlice = 2750;
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

            //TODO: Add custom fluid
            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
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
                    case 2 -> TissueExtractionChamberBlockEntity.this.blice;
                    case 3 -> TissueExtractionChamberBlockEntity.this.maxBlice;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> TissueExtractionChamberBlockEntity.this.progress = value;
                    case 1 -> TissueExtractionChamberBlockEntity.this.maxProgress = value;
                    case 2 -> TissueExtractionChamberBlockEntity.this.blice = value;
                    case 3 -> TissueExtractionChamberBlockEntity.this.maxBlice = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
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
        tag.putInt("blice", blice);
        tag.putInt("maxBlice", maxBlice);
        tag.putBoolean("working", working);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        maxProgress = tag.getInt("maxProgress");
        blice = tag.getInt("blice");
        maxBlice = tag.getInt("maxBlice");
        working = tag.getBoolean("working");
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
        if (itemHandler.getStackInSlot(0).is(PrehistoricItems.BOTTLE_OF_BLICE)) {
            if (maxBlice - blice >= 250) {
                itemHandler.extractItem(0, 1, false);
                blice = blice + 250;
            }
        }
    }

    private boolean tryInitializeRecipe() {
        for (int i = 1; i < 5; i++) {
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

        if (blice < 45) return false;

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
        for (int i = 5; i < 21; i++) {
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
        blice -= 45;

        for (int i = 5; i < 21; i++) {
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
}
