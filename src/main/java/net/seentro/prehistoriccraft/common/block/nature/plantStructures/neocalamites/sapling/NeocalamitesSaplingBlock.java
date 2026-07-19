package net.seentro.prehistoriccraft.common.block.nature.plantStructures.neocalamites.sapling;

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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;
import net.seentro.prehistoriccraft.common.block.nature.UnderwaterBushBlock;
import net.seentro.prehistoriccraft.common.block.nature.plantStructures.neocalamites.NeocalamitesBlock;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricTags;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class NeocalamitesSaplingBlock extends UnderwaterBushBlock implements BonemealableBlock, SimpleWaterloggedBlock {
    public static final MapCodec<NeocalamitesSaplingBlock> CODEC = simpleCodec(NeocalamitesSaplingBlock::new);
    public static final BooleanProperty IS_STEM = BooleanProperty.create("is_stem");

    public NeocalamitesSaplingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(IS_STEM, false));
    }

    @Override
    protected MapCodec<? extends UnderwaterBushBlock> codec() {
        return CODEC;
    }

    private static final VoxelShape SHAPE = Block.box(7.0, 0.0, 7.0, 9.0, 14.0, 9.0);
    private static final VoxelShape STEM_SHAPE = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);

        return state.getValue(IS_STEM) ? STEM_SHAPE.move(offset.x, offset.y, offset.z) : SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_STEM, WATERLOGGED);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(IS_STEM);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        performBonemeal(level, random, pos, state);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return !blockState.getValue(IS_STEM);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return (double) level.random.nextFloat() < 0.45;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos pos, BlockState state) {
        BlockPos lastWaterBlock = findLastWaterBlock(serverLevel, pos);
        BlockPos surfacePos = findSurfacePos(serverLevel, pos);

        BlockState neocalamitesState = PrehistoricBlocks.NEOCALAMITES.get().defaultBlockState()
                .setValue(NeocalamitesBlock.MIDDLE_SEGMENT_COUNT, NeocalamitesBlock.getMiddleSegmentCount(serverLevel, lastWaterBlock));

        if (!NeocalamitesBlock.isEnoughSpace(serverLevel, neocalamitesState, pos)) return;
        if (!NeocalamitesBlock.hasWaterBlockCloseBy(serverLevel, pos)) return;

        breakPlant(serverLevel, pos, false, true);

        NeocalamitesBlock.placeStemsAndPlantStructure(serverLevel, lastWaterBlock, surfacePos, neocalamitesState);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public static final SoundType SILENT = new SoundType(-1.0F, 1.0F, SoundEvents.AMETHYST_BLOCK_BREAK, SoundEvents.AMETHYST_BLOCK_BREAK, SoundEvents.AMETHYST_BLOCK_BREAK, SoundEvents.AMETHYST_BLOCK_BREAK, SoundEvents.AMETHYST_BLOCK_BREAK);

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(IS_STEM) ? SILENT : super.getSoundType(state, level, pos, entity); // ensure we only play a sound for the sapling block
    }

    /* MULTIBLOCK */

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (countUntilSurface(level, pos) > 6) return null;
        FluidState fluidstate = level.getFluidState(pos);

        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.is(FluidTags.WATER));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockPos surfacePos = findSurfacePos(level, pos);

        BlockPos currentPos = pos;
        while (currentPos.getY() < surfacePos.getY()) {
            level.setBlock(currentPos, state.setValue(IS_STEM, true), Block.UPDATE_CLIENTS);
            currentPos = currentPos.above();
        }

        level.setBlock(surfacePos, state.setValue(WATERLOGGED, false).setValue(IS_STEM, false), Block.UPDATE_ALL);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        int underwaterCount = pos.getY() - findSurfacePos(level, pos).getY();
        return state.getValue(IS_STEM) ? underwaterCount <= 6 && checkGround(level, state, pos) : checkGround(level, state, pos)
                && NeocalamitesBlock.hasWaterBlockCloseBy(level, pos);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(PrehistoricTags.Blocks.NATURAL_GROWABLE) || state.getBlock() instanceof FarmBlock || state.is(this) && state.getValue(IS_STEM);
    }

    private boolean checkGround(LevelReader level, BlockState state, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState belowBlockState = level.getBlockState(blockpos);
        TriState soilDecision = belowBlockState.canSustainPlant(level, blockpos, Direction.UP, state);
        return !soilDecision.isDefault() ? soilDecision.isTrue() : this.mayPlaceOn(belowBlockState, level, blockpos);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.isCreative()) {
            breakPlant(level, pos, false, false); // if the player is in creative, we don't want to drop
        }

        return super.playerWillDestroy(level, pos, state, player);
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
        // every time a tick is scheduled, check the integrity of the sapling
        if (state.getValue(IS_STEM)) {
            if (!canStemSurvive(level, pos)) {
                breakPlant(level, pos, true, false); // if we can't survive, break the whole plant at once and drop the base.
            }
        } else {
            if (!canBaseSurvive(level, pos)) {
                breakPlant(level, pos, true, false); // if we can't survive, break the whole plant at once and drop the base.
            }
        }
    }

    private boolean canStemSurvive(LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.above()).is(this) && level.getFluidState(pos).is(FluidTags.WATER);
    }

    // the base checks the stem height
    private boolean canBaseSurvive(LevelReader level, BlockPos pos) {
        // count the stem height to ensure it isn't higher than six
        int stemCount = 0;
        BlockPos currentPos = pos.below();

        while (level.getBlockState(currentPos).is(this) && level.getBlockState(currentPos).getValue(IS_STEM)) {
            stemCount++;
            currentPos = currentPos.below();
        }

        return stemCount <= 6; // if the stem count is lower or equal to six, we can survive
    }

    private void breakPlant(Level level, BlockPos pos, boolean shouldDrop, boolean silent) {
        BlockPos currentPos = pos;
        // find the base block
        while (level.getBlockState(currentPos.below()).is(this)) {
            currentPos = currentPos.below();
        }

        int plantCount = 0;
        BlockPos basePos = null;

        // walk upwards from the base block, counting up the plantCount each time
        while (level.getBlockState(currentPos).is(this)) {
            plantCount++; // count up plantCount for later destruction
            if (!level.getBlockState(currentPos).getValue(IS_STEM)) basePos = currentPos; // save the base position

            currentPos = currentPos.above();
        }

        /* This is needed because the base block would drop without it. Since it goes from bottom -> up, */
        /* it would break the stem, thus breaking AND dropping the base, even if shouldDrop is false     */

        if (basePos != null) { // if the basePos isn't null, we destroy the base so it doesn't drop in creative
            if (silent) {
                level.removeBlock(currentPos, false);
            } else level.destroyBlock(basePos, shouldDrop);
        } 

        for (int i = 0; i <= plantCount; i++) {
            if (silent) {
                level.removeBlock(currentPos.below(i), false); // go back and destroy each block now that the base is gone
            } else {
                level.destroyBlock(currentPos.below(i), shouldDrop); // go back and destroy each block now that the base is gone
            }
        }
    }

    private BlockPos findLastWaterBlock(LevelReader level, BlockPos pos) {
        BlockPos currentPos = pos;

        while (level.getFluidState(currentPos.below()).is(FluidTags.WATER)) {
            currentPos = currentPos.below();
        }

        return currentPos;
    }

    private BlockPos findSurfacePos(LevelReader level, BlockPos pos) {
        BlockPos currentPos = pos;

        while (level.getFluidState(currentPos).is(FluidTags.WATER)) {
            currentPos = currentPos.above();
        }

        return currentPos;
    }

    private int countUntilSurface(LevelReader level, BlockPos pos) {
        int count = 0;
        BlockPos currentPos = pos;

        while (level.getFluidState(currentPos).is(FluidTags.WATER)) {
            count++;
            currentPos = currentPos.above();
        }

        return count;
    }
}
