package net.seentro.prehistoriccraft.common.block.nature.neocalamites;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.core.multiblock.QuadrupleInvisibleSegmentProperty;
import org.jetbrains.annotations.Nullable;

import static net.seentro.prehistoriccraft.core.multiblock.QuadrupleInvisibleSegmentProperty.*;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class NeocalamitesBlock extends BushBlock implements EntityBlock, SimpleWaterloggedBlock {
    public static final MapCodec<NeocalamitesBlock> CODEC = simpleCodec(NeocalamitesBlock::new);
    public static final EnumProperty<QuadrupleInvisibleSegmentProperty> SEGMENT = EnumProperty.create("segment", QuadrupleInvisibleSegmentProperty.class);
    public static final IntegerProperty MIDDLE_SEGMENT_COUNT = IntegerProperty.create("middle_segment_count", 1, 2);

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

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(SEGMENT) != BASE ? SILENT : super.getSoundType(state, level, pos, entity); // ensure we only play a sound for the base block
    }

    /* MULTIBLOCK LOGIC */

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        FluidState fluidstate = level.getFluidState(pos);

        boolean canFitDouble = true;
        for (int i = 1; i < 5; i++) {
            if (!level.getBlockState(pos.above(i)).canBeReplaced()) canFitDouble = false;
        }

        // decide to place one or two middle segments based on the position of the block. If we cannot fit as a double, overwrite and set the middle segments to one
        int middleSegmentCount = Math.abs(pos.hashCode()) % 2 + 1;
        if (!canFitDouble) middleSegmentCount = 1;

        PrehistoricCraft.LOGGER.info("{}: {}", level.isClientSide ? "CLIENT" : "SERVER", middleSegmentCount);

        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.is(FluidTags.WATER)).setValue(MIDDLE_SEGMENT_COUNT, middleSegmentCount);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
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

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (isSegment(state, BASE)) {
            return isEnoughSpace(level, state, pos) && checkGround(level, state, pos);
        }
        return true;
    }

    private boolean isEnoughSpace(LevelReader level, BlockState state, BlockPos pos) {
        for (int i = 1; i < state.getValue(MIDDLE_SEGMENT_COUNT) + 3; i++) {
            if (!level.getBlockState(pos.above(i)).canBeReplaced() && !level.getBlockState(pos.above(i)).is(this)) return false;
        }

        return true;
    }

    private boolean checkGround(LevelReader level, BlockState state, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState belowBlockState = level.getBlockState(blockpos);
        TriState soilDecision = belowBlockState.canSustainPlant(level, blockpos, Direction.UP, state);
        return !soilDecision.isDefault() ? soilDecision.isTrue() : this.mayPlaceOn(belowBlockState, level, blockpos);
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
        // every time a tick is scheduled, check the integrity of the plant
        if (!validateSegment(level, state, pos)) {
            breakPlant(level, pos, true); // if we can't survive, break the whole plant at once and drop the base.
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.isCreative()) {
            breakPlant(level, pos, false); // if the player is in creative, we don't want to drop
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    private void breakPlant(Level level, BlockPos pos, boolean shouldBreak) {
        BlockPos currentPos = pos;
        // find the base block
        while (level.getBlockState(currentPos.below()).is(this)) {
            currentPos = currentPos.below();
        }

        // walk upwards from the base block, breaking each block on the way
        while (level.getBlockState(currentPos).is(this)) {
            level.destroyBlock(currentPos, shouldBreak);
            currentPos = currentPos.above();
        }
    }

    private boolean validateSegment(LevelReader level, BlockState state, BlockPos pos) {
        // switch on each of the segments and call the respective survival methods
        return switch (state.getValue(SEGMENT)) {
            case STEM -> true;
            case BASE -> canBaseSurvive(level, state, pos);
            case MIDDLE -> canMiddleSurvive(level, pos);
            case TOP -> canTopSurvive(level, pos);
            case INVISIBLE -> canInvisibleSurvive(level, pos);
        };
    }

    // the base checks the complete structure of the entire plant
    private boolean canBaseSurvive(LevelReader level, BlockState state, BlockPos pos) {
        int middleSegmentCount = state.getValue(MIDDLE_SEGMENT_COUNT);
        int aboveAddOnto = middleSegmentCount == 2 ? 1 : 0; // if we have two middles, offset the pos.above() by one so we can check properly for each height

        if (middleSegmentCount == 2) {
            if (!isSegment(level.getBlockState(pos.above(2)), MIDDLE)) return false; // if the middle segment count is two, check another middle
        }

        if (!isSegment(level.getBlockState(pos.above(3 + aboveAddOnto)), INVISIBLE)) return false; // check for invisible, offset by middle segment count
        if (!isSegment(level.getBlockState(pos.above(2 + aboveAddOnto)), TOP)) return false; // check for top, offset by middle segment count
        return isSegment(level.getBlockState(pos.above()), MIDDLE); // check for middle
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
