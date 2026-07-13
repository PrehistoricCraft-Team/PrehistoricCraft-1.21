package net.seentro.prehistoriccraft.common.block.nature.neocalamites.sapling;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.seentro.prehistoriccraft.common.block.nature.UnderwaterBushBlock;
import org.jetbrains.annotations.Nullable;

public class NeocalamitesSaplingBlock extends UnderwaterBushBlock implements BonemealableBlock, LiquidBlockContainer {
    public static final MapCodec<NeocalamitesSaplingBlock> CODEC = simpleCodec(NeocalamitesSaplingBlock::new);
    public static final BooleanProperty IS_STEM = BooleanProperty.create("is_stem");

    public NeocalamitesSaplingBlock(Properties properties) {
        super(properties);
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
        builder.add(IS_STEM);
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
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {

    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        return false;
    }
}
