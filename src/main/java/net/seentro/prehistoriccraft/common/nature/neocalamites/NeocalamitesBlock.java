package net.seentro.prehistoriccraft.common.nature.neocalamites;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.BiPredicate;

public class NeocalamitesBlock extends BushBlock implements EntityBlock {
    public static final MapCodec<NeocalamitesBlock> CODEC = simpleCodec(NeocalamitesBlock::new);

    public static final IntegerProperty UNDERWATER_HEIGHT = IntegerProperty.create("underwater_height", 0, 6);
    public static final IntegerProperty HEIGHT = IntegerProperty.create("height", 3, 4);

    public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");
    public static final BooleanProperty IS_MIDDLE = BooleanProperty.create("is_middle");
    public static final BooleanProperty IS_BASE = BooleanProperty.create("is_base");
    public static final BooleanProperty IS_STEM = BooleanProperty.create("is_stem");

    public NeocalamitesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(IS_STEM, false).setValue(IS_MIDDLE, false).setValue(IS_BASE, false).setValue(IS_TOP, false));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    private static final VoxelShape SHAPE = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);

        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_STEM, IS_BASE, IS_MIDDLE, IS_TOP, UNDERWATER_HEIGHT, HEIGHT);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NeocalamitesBlockEntity(pos, state);
    }

    // Can place on?
    protected boolean mayPlaceOn(BlockState state) {
        return state.is(BlockTags.DIRT) || state.getBlock() instanceof FarmBlock;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        RandomSource random = RandomSource.create();
        int height = random.nextIntBetweenInclusive(3, 4);

        return this.defaultBlockState().setValue(HEIGHT, height);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        // Place the other parts
        if (state.getValue(HEIGHT) == 3) {
            level.setBlock(pos.above(2), state.setValue(IS_TOP, true), Block.UPDATE_CLIENTS);
        } else {
            level.setBlock(pos.above(3), state.setValue(IS_TOP, true), Block.UPDATE_CLIENTS);
            level.setBlock(pos.above(2), state.setValue(IS_MIDDLE, true), Block.UPDATE_CLIENTS);
        }

        level.setBlock(pos.above(), state.setValue(IS_MIDDLE, true), Block.UPDATE_CLIENTS);
        level.setBlock(pos, state.setValue(IS_BASE, true), Block.UPDATE_ALL);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        int underwaterHeight = state.getValue(UNDERWATER_HEIGHT);
        int height = state.getValue(HEIGHT);

        // Only runs for the base
        if (state.getValue(IS_BASE)) {
            if (!super.canSurvive(state, level, pos)) return false;

            for (int i = 1; i < height; i++) {
                BlockState offsetState = level.getBlockState(pos.above(i));

                if (offsetState.getBlock() != this) return false;
                if (!offsetState.getValue(IS_MIDDLE) && !offsetState.getValue(IS_TOP)) return false;
            }

            if (level.getBlockState(pos).getBlock() != this && !level.getBlockState(pos).getValue(IS_BASE)) return false;
            if (level.getBlockState(pos.above()).getBlock() != this && !level.getBlockState(pos.above()).getValue(IS_MIDDLE)) return false;

            if (height == 3) {
                return level.getBlockState(pos.above(2)).getBlock() == this && level.getBlockState(pos.above(2)).getValue(IS_TOP);
            } else if (height == 4) {
                if (level.getBlockState(pos.above(2)).getBlock() != this && !level.getBlockState(pos.above(2)).getValue(IS_MIDDLE)) return false;
                return level.getBlockState(pos.above(3)).getBlock() == this && level.getBlockState(pos.above(3)).getValue(IS_TOP);
            }

            return true;
        }

        // Only runs for non-base blocks

        if (state.getValue(IS_TOP)) {
            return level.getBlockState(pos.below()).getBlock() == this;
        }

        if (state.getValue(IS_MIDDLE)) {
            return level.getBlockState(pos.below()).getBlock() == this && level.getBlockState(pos.above()).getBlock() == this;
        }

        for (int i = 1; i < height; i++) {
            if (level.getBlockState(pos.above(i)).getBlock() != this && !level.getBlockState(pos.above(i)).canBeReplaced()) {
                return false;
            }
        }

        // Make sure the code only runs for completed structures!
        if (!state.getValue(IS_BASE) && !state.getValue(IS_MIDDLE) && !state.getValue(IS_TOP) && !state.getValue(IS_STEM)) {
            return super.canSurvive(state, level, pos);
        }

        return false;
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        if (!level.isClientSide()) {
            if (!canSurvive(state, level, pos) && state.getValue(IS_BASE)) {
                destroyPlant((Level) level, pos);
            }
        }
    }

    // Destroy if we can't survive
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.isCreative()) {
            destroyPlant(level, pos);
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    private void destroyPlant(Level level, BlockPos pos) {
        BlockPos currentPos = pos;
        while (level.getBlockState(currentPos.above()).getBlock() == this) {
            currentPos = currentPos.above();
        }

        while (level.getBlockState(currentPos).getBlock() == this) {
            level.destroyBlock(currentPos, false);
            currentPos = currentPos.below();
        }
    }
}
