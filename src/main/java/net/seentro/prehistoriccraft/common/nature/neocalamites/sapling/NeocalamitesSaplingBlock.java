package net.seentro.prehistoriccraft.common.nature.neocalamites.sapling;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.datafix.fixes.BlockEntityKeepPacked;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.nature.neocalamites.NeocalamitesBlock;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;

import static net.seentro.prehistoriccraft.common.nature.neocalamites.NeocalamitesBlock.*;

public class NeocalamitesSaplingBlock extends BushBlock implements BonemealableBlock {
    public static final MapCodec<NeocalamitesSaplingBlock> CODEC = simpleCodec(NeocalamitesSaplingBlock::new);

    public static final BooleanProperty IS_STEM = BooleanProperty.create("is_stem");

    public NeocalamitesSaplingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(IS_STEM, false));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
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
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return !state.getValue(IS_STEM);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return (double) level.random.nextFloat() < 0.45;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int height = random.nextIntBetweenInclusive(3, 4);
        BlockState neocalamitesState = PrehistoricBlocks.NEOCALAMITES.get().defaultBlockState().setValue(HEIGHT, height);

        for (int i = 1; i < height; i++) {
            if (level.getBlockState(pos.above(i)).getBlock() != this && !level.getBlockState(pos.above(i)).canBeReplaced()) return;
        }

        // Place the other parts
        if (height == 3) {
            level.setBlock(pos.above(2), neocalamitesState.setValue(IS_TOP, true), Block.UPDATE_CLIENTS);
        } else {
            level.setBlock(pos.above(3), neocalamitesState.setValue(IS_TOP, true), Block.UPDATE_CLIENTS);
            level.setBlock(pos.above(2), neocalamitesState.setValue(IS_MIDDLE, true), Block.UPDATE_CLIENTS);
        }

        level.setBlock(pos.above(), neocalamitesState.setValue(IS_MIDDLE, true), Block.UPDATE_CLIENTS);
        level.setBlock(pos, neocalamitesState.setValue(IS_BASE, true), Block.UPDATE_ALL);
    }
}
