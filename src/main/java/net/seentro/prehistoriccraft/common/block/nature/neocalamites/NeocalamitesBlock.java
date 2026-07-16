package net.seentro.prehistoriccraft.common.block.nature.neocalamites;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.core.multiblock.QuadrupleInvisibleSegmentProperty;
import net.seentro.prehistoriccraft.utils.MultiblockHelper;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;
import static net.seentro.prehistoriccraft.core.multiblock.QuadrupleInvisibleSegmentProperty.*;

public class NeocalamitesBlock extends BushBlock implements EntityBlock, SimpleWaterloggedBlock {
    public static final MapCodec<NeocalamitesBlock> CODEC = simpleCodec(NeocalamitesBlock::new);
    public static final EnumProperty<QuadrupleInvisibleSegmentProperty> SEGMENT = EnumProperty.create("segment", QuadrupleInvisibleSegmentProperty.class);
    public static final IntegerProperty MIDDLE_SEGMENT_COUNT = IntegerProperty.create("middle_segment_count", 1, 2);
    private static final int MAX_STEM_COUNT = 6;

    public NeocalamitesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(SEGMENT, BASE).setValue(MIDDLE_SEGMENT_COUNT, 1).setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    private static final VoxelShape SHAPE = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    private static final VoxelShape INVISIBLE_SHAPE = Block.box(7.0, 0.0, 7.0, 9.0, 4.0, 9.0);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        return state.getValue(SEGMENT) == INVISIBLE ? INVISIBLE_SHAPE.move(offset.x, offset.y, offset.z) : SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SEGMENT, WATERLOGGED, MIDDLE_SEGMENT_COUNT);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new NeocalamitesBlockEntity(blockPos, blockState);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public static final SoundType SILENT = new SoundType(-1.0F, 1.0F, SoundEvents.AMETHYST_BLOCK_BREAK, SoundEvents.AMETHYST_BLOCK_BREAK, SoundEvents.AMETHYST_BLOCK_BREAK, SoundEvents.AMETHYST_BLOCK_BREAK, SoundEvents.AMETHYST_BLOCK_BREAK);

    // we don't want the non-base blocks to have a sound as that spams sounds when we break the plant
    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(SEGMENT) != BASE ? SILENT : super.getSoundType(state, level, pos, entity); // ensure we only play a sound for the base block
    }

    /* MULTIBLOCK LOGIC */

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (countUntilSurface(level, pos) > MAX_STEM_COUNT) return null; // ensure that we are not allowed to place if the stem count would be higher than MAX_STEM_COUNT
        FluidState fluidstate = level.getFluidState(pos);
        int middleSegmentCount = getMiddleSegmentCount(level, pos);

        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.is(FluidTags.WATER)).setValue(MIDDLE_SEGMENT_COUNT, middleSegmentCount);
    }

    // decide to place one or two middle segments based on the position of the block. If we cannot fit as a double, overwrite and set the middle segments to one
    public static int getMiddleSegmentCount(Level level, BlockPos pos) {
        boolean canFitDouble = true;
        for (int i = 1; i < 5; i++) {
            if (!level.getBlockState(pos.above(i)).canBeReplaced()) canFitDouble = false;
        }

        int toReturn = Math.abs(pos.hashCode()) % 2 + 1; // calculate height based on position
        if (!canFitDouble) toReturn = 1; // if we can't fit a double, overwrite with a one high instead

        return toReturn;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        placeStemsAndPlantStructure(level, pos, findSurfacePos(level, pos), state);
    }

    // place all the stems and the main plant structure
    public static void placeStemsAndPlantStructure(Level level, BlockPos pos, BlockPos surfacePos, BlockState state) {
        BlockPos currentPos = pos;
        while (currentPos.getY() < surfacePos.getY()) {
            level.setBlock(currentPos, state.setValue(SEGMENT, STEM).setValue(WATERLOGGED, true), Block.UPDATE_CLIENTS);
            currentPos = currentPos.above();
        }

        placePlantStructure(level, surfacePos, state.setValue(WATERLOGGED, false));
    }

    // place the main plant structure
    public static void placePlantStructure(Level level, BlockPos pos, BlockState state) {
        if (state.getValue(SEGMENT) != BASE) return; // ensure we are placing from the base
        int aboveAddOnto = state.getValue(MIDDLE_SEGMENT_COUNT) == 2 ? 1 : 0; // if we have two middles, offset the pos.above() by one so we can place another middle

        if (state.getValue(MIDDLE_SEGMENT_COUNT) == 2) { // if we have two middles, place the middle segment
            level.setBlock(pos.above(2), state.setValue(SEGMENT, MIDDLE), Block.UPDATE_CLIENTS); // middle
        }

        level.setBlock(pos.above(3 + aboveAddOnto), state.setValue(SEGMENT, INVISIBLE), Block.UPDATE_CLIENTS); // invisible
        level.setBlock(pos.above(2 + aboveAddOnto), state.setValue(SEGMENT, TOP), Block.UPDATE_CLIENTS); // top
        level.setBlock(pos.above(), state.setValue(SEGMENT, MIDDLE), Block.UPDATE_CLIENTS); // middle
        level.setBlock(pos, state.setValue(SEGMENT, BASE), Block.UPDATE_ALL); // base
    }

    // basic can survive checks for base and stem
    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (isSegment(state, BASE)) {
            return isEnoughSpace(level, state, pos) && hasWaterBlockCloseBy(level, pos);
        } else if (isSegment(state, STEM)) {
            int underwaterCount = pos.getY() - findSurfacePos(level, pos).getY();
            return underwaterCount <= MAX_STEM_COUNT;
        }


        return !isSegment(state, BASE) || isEnoughSpace(level, state, pos) && hasWaterBlockCloseBy(level, pos);
    }

    // check if we have enough space to place the entire plant
    public static boolean isEnoughSpace(LevelReader level, BlockState state, BlockPos pos) {
        for (int i = 1; i < state.getValue(MIDDLE_SEGMENT_COUNT) + 3; i++) {
            if (!level.getBlockState(pos.above(i)).canBeReplaced()
                    && !(level.getBlockState(pos.above(i)).getBlock() instanceof NeocalamitesBlock)) return false;
        }

        return true;
    }

    // searches in a range for any water blocks
    public static boolean hasWaterBlockCloseBy(LevelReader level, BlockPos pos) {
        int range = 4; // search from -4 to 4 blocks around pos
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    if (level.getFluidState(pos.offset(x, y, z)).is(FluidTags.WATER)) return true;
                }
            }
        }

        return false;
    }

    // allow the neocalamites block to be placed anywhere if it's a sturdy face (like full blocks, and trapdoors if they are closed) and isn't a magma block
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.isFaceSturdy(level, pos, Direction.UP) && !state.is(Blocks.MAGMA_BLOCK) || isSegment(state, STEM);
    }

    private boolean checkGround(LevelReader level, BlockState state, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState belowBlockState = level.getBlockState(blockpos);
        TriState soilDecision = belowBlockState.canSustainPlant(level, blockpos, Direction.UP, state);
        return !soilDecision.isDefault() ? soilDecision.isTrue() : this.mayPlaceOn(belowBlockState, level, blockpos);
    }

    // walks upwards while in water and returns the first air block
    private static BlockPos findSurfacePos(LevelReader level, BlockPos pos) {
        BlockPos currentPos = pos;

        while (level.getFluidState(currentPos).is(FluidTags.WATER)) {
            currentPos = currentPos.above();
        }

        return currentPos;
    }

    // walks upwards while counting and while in water and stops when it reaches the first air block
    private static int countUntilSurface(LevelReader level, BlockPos pos) {
        int count = 0;
        BlockPos currentPos = pos;

        while (level.getFluidState(currentPos).is(FluidTags.WATER)) {
            count++;
            currentPos = currentPos.above();
        }

        return count;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        BlockState blockState = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        if (!blockState.isAir()) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            level.scheduleTick(currentPos, this, 1); // schedule the validations
        }

        return blockState;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isClientSide()) return;
        // every time a tick is scheduled, check the integrity of the plant
        if (!validateSegment(level, state, pos)) {
            breakPlant(level, pos, null, true); // if we can't survive, break the whole plant at once and drop the base.
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide()) {
            breakPlant(level, pos, player, !player.isCreative()); // break the plant and ensure no drops in creative
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    // break the whole plant. Pass in the player as the entity to get correct drops.
    private void breakPlant(Level level, BlockPos pos, @Nullable Entity entity, boolean shouldDrop) {
        // find the bottom of the plant
        BlockPos currentPos = pos;
        while (level.getBlockState(currentPos.below()).is(this)) {
            currentPos = currentPos.below();
        }

        int plantCount = 0;
        BlockPos basePos = null;

        // walk up the plant to count height and find the base
        while (level.getBlockState(currentPos).is(this)) {
            plantCount++;
            if (isSegment(level.getBlockState(currentPos), BASE)) basePos = currentPos;
            currentPos = currentPos.above();
        }

        if (basePos == null) return;

        // if we want to drop, check if we have a player. If we do, drop with tools, otherwise drop sapling. If we don't want to drop, don't drop.
        if (shouldDrop) {
            if (entity instanceof Player player) {
                MultiblockHelper.destroyBlockWithTool(level, basePos, player);
            } else {
                level.destroyBlock(basePos, true);
            }
        } else {
            level.destroyBlock(basePos, false);
        }

        // remove all the blocks
        for (int i = 0; i <= plantCount; i++) {
            if (basePos.equals(currentPos.below(i))) continue;
            level.destroyBlock(currentPos.below(i), false);
        }
    }

    private boolean validateSegment(LevelReader level, BlockState state, BlockPos pos) {
        // switch on each of the segments and call the respective survival methods
        return switch (state.getValue(SEGMENT)) {
            case STEM -> canStemSurvive(level, state, pos);
            case BASE -> canBaseSurvive(level, state, pos);
            case MIDDLE -> canMiddleSurvive(level, pos);
            case TOP -> canTopSurvive(level, pos);
            case INVISIBLE -> canInvisibleSurvive(level, pos);
        };
    }

    // the stem segment can survive if the block above it is equal to this, the ground is sturdy or equal to this, and it is in water
    private boolean canStemSurvive(LevelReader level, BlockState state, BlockPos pos) {
        return level.getBlockState(pos.above()).is(this) &&
                checkGround(level, state, pos)
                && level.getFluidState(pos).is(FluidTags.WATER);
    }

    // the base checks the complete structure of the entire plant, also checking the stem height
    private boolean canBaseSurvive(LevelReader level, BlockState state, BlockPos pos) {
        // count the stem height to ensure it isn't higher than six
        int stemCount = 0;
        BlockPos currentPos = pos.below();

        while (isSegment(level.getBlockState(currentPos), STEM)) {
            stemCount++;
            currentPos = currentPos.below();
        }

        if (stemCount > MAX_STEM_COUNT) return false; // if the stem count is higher than six, we can't survive

        int middleSegmentCount = state.getValue(MIDDLE_SEGMENT_COUNT);
        int aboveAddOnto = middleSegmentCount == 2 ? 1 : 0; // if we have two middles, offset the pos.above() by one so we can check properly for each height

        if (middleSegmentCount == 2) {
            if (!isSegment(level.getBlockState(pos.above(2)), MIDDLE)) return false; // if the middle segment count is two, check another middle
        }

        if (!isSegment(level.getBlockState(pos.above(3 + aboveAddOnto)), INVISIBLE)) return false; // check for invisible, offset by middle segment count
        if (!isSegment(level.getBlockState(pos.above(2 + aboveAddOnto)), TOP)) return false; // check for top, offset by middle segment count
        if (!isSegment(level.getBlockState(pos.above()), MIDDLE)) return false; // check for middle
        return checkGround(level, state, pos);
    }

    // the middle segment can survive if the block below is a base / middle segment and the block above is a top / middle segment
    private boolean canMiddleSurvive(LevelReader level, BlockPos pos) {
        return (isSegment(level.getBlockState(pos.below()), BASE) || isSegment(level.getBlockState(pos.below()), MIDDLE)) &&
                (isSegment(level.getBlockState(pos.above()), TOP) || isSegment(level.getBlockState(pos.above()), MIDDLE));
    }

    // the top segment can survive if the block below is a middle segment and the block above is the invisible segment
    private boolean canTopSurvive(LevelReader level, BlockPos pos) {
        return isSegment(level.getBlockState(pos.below()), MIDDLE) && isSegment(level.getBlockState(pos.above()), INVISIBLE);
    }

    // the invisible segment can survive if the block below is the top segment
    private boolean canInvisibleSurvive(LevelReader level, BlockPos pos) {
        return isSegment(level.getBlockState(pos.below()), TOP);
    }

    // easy method to check if the block is this and if the segment is correct
    private boolean isSegment(BlockState state, QuadrupleInvisibleSegmentProperty segment) {
        return state.is(this) && state.getValue(SEGMENT) == segment;
    }
}