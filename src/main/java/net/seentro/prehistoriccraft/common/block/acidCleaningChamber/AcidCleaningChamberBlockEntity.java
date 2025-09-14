package net.seentro.prehistoriccraft.common.block.acidCleaningChamber;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.TissueExtractionChamberBlockEntity;
import net.seentro.prehistoriccraft.common.screen.acidCleaningChamber.AcidCleaningChamberMenu;
import net.seentro.prehistoriccraft.common.screen.tissueExtractionChamber.TissueExtractionChamberMenu;
import net.seentro.prehistoriccraft.core.systems.WeightedRandom;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;
import net.seentro.prehistoriccraft.registry.PrehistoricDataComponents;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;
import net.seentro.prehistoriccraft.registry.PrehistoricTags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

import java.util.Map;
import java.util.Random;

public class AcidCleaningChamberBlockEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public final ItemStackHandler itemHandler = new ItemStackHandler(13) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot == 0 ? stack.getItem() == Items.WATER_BUCKET : super.isItemValid(slot, stack);
        }
    };

    //Bucket slot is slot 0
    //Input starts at 0, ends at 7
    //Output starts at 7, ends at 13

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 160;
    private int acid = 0;
    private int maxAcid = 3000;
    // 1 = true, 0 = false.
    protected int working;

    private static final Map<Item, ItemStack> FOSSIL_MAP = Map.ofEntries(
            Map.entry(PrehistoricItems.CAMBRIAN_FOSSIL.get(), new ItemStack(PrehistoricItems.CAMBRIAN_FOSSIL_SAMPLE.get())),
            Map.entry(PrehistoricItems.CARBONIFEROUS_FOSSIL.get(), new ItemStack(PrehistoricItems.CARBONIFEROUS_FOSSIL_SAMPLE.get())),
            Map.entry(PrehistoricItems.CRETACEOUS_FOSSIL.get(), new ItemStack(PrehistoricItems.CRETACEOUS_FOSSIL_SAMPLE.get())),
            Map.entry(PrehistoricItems.DEVONIAN_FOSSIL.get(), new ItemStack(PrehistoricItems.DEVONIAN_FOSSIL_SAMPLE.get())),
            Map.entry(PrehistoricItems.JURASSIC_FOSSIL.get(), new ItemStack(PrehistoricItems.JURASSIC_FOSSIL_SAMPLE.get())),
            Map.entry(PrehistoricItems.NEOGENE_FOSSIL.get(), new ItemStack(PrehistoricItems.NEOGENE_FOSSIL_SAMPLE.get())),
            Map.entry(PrehistoricItems.ORDOVICIAN_FOSSIL.get(), new ItemStack(PrehistoricItems.ORDOVICIAN_FOSSIL_SAMPLE.get())),
            Map.entry(PrehistoricItems.PALEOGENE_FOSSIL.get(), new ItemStack(PrehistoricItems.PALEOGENE_FOSSIL_SAMPLE.get())),
            Map.entry(PrehistoricItems.PERMIAN_FOSSIL.get(), new ItemStack(PrehistoricItems.PERMIAN_FOSSIL_SAMPLE.get())),
            Map.entry(PrehistoricItems.PRECAMBRIAN_FOSSIL.get(), new ItemStack(PrehistoricItems.PRECAMBRIAN_FOSSIL_SAMPLE.get())),
            Map.entry(PrehistoricItems.SILURIAN_FOSSIL.get(), new ItemStack(PrehistoricItems.SILURIAN_FOSSIL_SAMPLE.get())),
            Map.entry(PrehistoricItems.TRIASSIC_FOSSIL.get(), new ItemStack(PrehistoricItems.TRIASSIC_FOSSIL_SAMPLE.get()))
    );

    public AcidCleaningChamberBlockEntity(BlockPos pos, BlockState blockState) {
        super(PrehistoricBlockEntityTypes.ACID_CLEANING_CHAMBER_BLOCK_ENTITY.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> AcidCleaningChamberBlockEntity.this.progress;
                    case 1 -> AcidCleaningChamberBlockEntity.this.maxProgress;
                    case 2 -> AcidCleaningChamberBlockEntity.this.acid;
                    case 3 -> AcidCleaningChamberBlockEntity.this.maxAcid;
                    case 4 -> AcidCleaningChamberBlockEntity.this.working;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AcidCleaningChamberBlockEntity.this.progress = value;
                    case 1 -> AcidCleaningChamberBlockEntity.this.maxProgress = value;
                    case 2 -> AcidCleaningChamberBlockEntity.this.acid = value;
                    case 3 -> AcidCleaningChamberBlockEntity.this.maxAcid = value;
                    case 4 -> AcidCleaningChamberBlockEntity.this.working = value;
                }
            }

            @Override
            public int getCount() {
                return 5;
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
        tag.putInt("acid", acid);
        tag.putInt("maxAcid", maxAcid);
        tag.putInt("working", working);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        maxProgress = tag.getInt("maxProgress");
        acid = tag.getInt("acid");
        maxAcid = tag.getInt("maxAcid");
        working = tag.getInt("working");
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
        return Component.translatable("block.prehistoriccraft.acid_cleaning_chamber");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new AcidCleaningChamberMenu(containerId, playerInventory, this, this.data);
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
    private int validInputSlot = -1;
    private static final Random random = new Random();

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (isBeingViewed(level)) {
            if (working == 0)
                this.triggerAnim("controller", "open_doors");
        }
        handleAcid();

        if (!tryInitializeRecipe()) {
            fullReset();
            setChanged(level, pos, state);
            return;
        }

        if (!hasRecipe()) {
            ItemStack input = validInputSlot >= 0 ? itemHandler.getStackInSlot(validInputSlot) : ItemStack.EMPTY;
            if (input.isEmpty() || !(input.is(PrehistoricTags.Items.FOSSILS) || input.has(PrehistoricDataComponents.FOSSIL_QUALITY))) {
                reset();
            }

            this.stopTriggeredAnim("controller", "working");
            working = 0;
            setChanged(level, pos, state);

            return;
        }

        working = 1;
        this.triggerAnim("controller", "working");
        progress++;
        setChanged(level, pos, state);

        if (progress >= maxProgress) {
            craft();
            reset();
        }
    }

    private boolean tryInitializeRecipe() {
        for (int i = 1; i < 7; i++) {
            ItemStack input = itemHandler.getStackInSlot(i);
            if (input.is(PrehistoricTags.Items.FOSSILS) && input.has(PrehistoricDataComponents.FOSSIL_QUALITY)) {
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
        if (input.isEmpty() || !(input.is(PrehistoricTags.Items.FOSSILS) && input.has(PrehistoricDataComponents.FOSSIL_QUALITY))) return false;

        if (acid < 16) return false;

        if (result == null) {
            result = simulateOutput(input);
        }

        return canOutput(result);
    }

    private ItemStack simulateOutput(ItemStack input) {
        double wasteChance = 0.45;

        if (random.nextDouble() >= wasteChance) {
            WeightedRandom<ItemLike> waste = new WeightedRandom<>();
            waste.addItem(Blocks.GRAVEL, 35);
            waste.addItem(Items.FLINT, 35);
            waste.addItem(Items.BONE, 20);
            waste.addItem(Blocks.BONE_BLOCK, 10);
            return new ItemStack(waste.getRandomItem());
        }

        ItemStack toOutput = FOSSIL_MAP.get(input.getItem()).copy();
        toOutput.set(PrehistoricDataComponents.FOSSIL_QUALITY, input.get(PrehistoricDataComponents.FOSSIL_QUALITY));
        return toOutput;
    }

    private boolean canOutput(ItemStack output) {
        for (int i = 7; i < 13; i++) {
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
        acid -= 16;

        for (int i = 7; i < 13; i++) {
            ItemStack leftover = itemHandler.insertItem(i, result.copy(), false);
            if (leftover.isEmpty()) {
                break;
            }
        }
    }

    private void handleAcid() {
        if (itemHandler.getStackInSlot(0).is(Items.WATER_BUCKET)) {
            if (this.maxAcid - this.acid >= 1000) {
                itemHandler.extractItem(0, 1, false);
                this.acid = this.acid + 1000;
            }
        }
    }

    private void reset() {
        progress = 0;
        validInputSlot = -1;
        result = null;
    }

    private void fullReset() {
        result = null;
        progress = 0;
        validInputSlot = -1;

        this.stopTriggeredAnim("controller", "working");
        working = 0;
    }

    private boolean isBeingViewed(Level level) {
        for (Player player : level.players()) {
            if (player.containerMenu instanceof AcidCleaningChamberMenu menu && menu.getBlockEntity() == this) {
                return true;
            }
        }
        return false;
    }
}
