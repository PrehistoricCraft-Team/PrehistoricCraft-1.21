package net.seentro.prehistoriccraft.common.nature.dawnRedwood;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class DawnRedwoodSaplingBlock extends BaseEntityBlock implements BonemealableBlock {
    public static final MapCodec<DawnRedwoodSaplingBlock> CODEC = RecordCodecBuilder.mapCodec(
            blockInstance -> blockInstance.group(TreeGrower.CODEC.fieldOf("tree").forGetter(saplingBlock -> saplingBlock.treeGrower), propertiesCodec())
                    .apply(blockInstance, DawnRedwoodSaplingBlock::new));

    public static final IntegerProperty STAGES = IntegerProperty.create("stages", 1, 3);
    public static final BooleanProperty INVISIBLE = BooleanProperty.create("invisible");
    protected final TreeGrower treeGrower;

    public DawnRedwoodSaplingBlock(TreeGrower treeGrower, Properties properties) {
        super(properties);
        this.treeGrower = treeGrower;
        this.registerDefaultState(this.getStateDefinition().any().setValue(STAGES, 1).setValue(INVISIBLE, false));
    }

    private static final VoxelShape STAGE_1 = Block.box(4.0, 0.0, 4.0, 12.0, 14.0, 12.0);
    private static final VoxelShape INVISIBLE_OTHER_STAGES = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(STAGES).equals(1)) {
            return STAGE_1;
        }

        return INVISIBLE_OTHER_STAGES;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGES, INVISIBLE);
    }

    private int randomTickCount = 0;

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (state.getValue(INVISIBLE)) return;

        if (randomTickCount == 30) {
            this.advanceTree(level, pos, state, random);
            randomTickCount = 0;
        } else {
            randomTickCount++;
        }
    }

    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        int stage = state.getValue(STAGES);

        if (stage >= 3) {
            treeGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
            return;
        }

        int nextStage = Math.min(stage + 1, 3);
        int required = requiredInvisibleForStage(nextStage);

        for (int i = 1; i <= required; i++) {
            BlockPos check = pos.above(i);
            BlockState checkState = level.getBlockState(check);

            if (checkState.isAir()) continue;

            if (checkState.getBlock() == this && checkState.getValue(INVISIBLE)) continue;

            return;
        }

        BlockState newBase = state.setValue(STAGES, nextStage).setValue(INVISIBLE, false);
        BlockState invisibleState = newBase.setValue(INVISIBLE, true);

        for (int i = required; i >= 1; i--) {
            BlockPos target = pos.above(i);
            BlockState cur = level.getBlockState(target);

            if (cur.getBlock() == this && cur.getValue(INVISIBLE) && cur.getValue(STAGES) == nextStage) {
                continue;
            }

            level.setBlock(target, invisibleState, Block.UPDATE_CLIENTS);
        }

        level.setBlock(pos, newBase, Block.UPDATE_ALL);
    }

    protected boolean mayPlaceOn(BlockState state) {
        return state.is(BlockTags.DIRT) || state.getBlock() instanceof FarmBlock;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        int stage = state.getValue(STAGES);
        boolean invisible = state.getValue(INVISIBLE);
        int required = requiredInvisibleForStage(stage);

        BiPredicate<BlockState, BlockPos> canSaplingSurvive = (soilState, soilPos) -> {
            TriState decision = soilState.canSustainPlant(level, soilPos, Direction.UP, state);
            if (!decision.isDefault()) return decision.isTrue();
            return this.mayPlaceOn(soilState);
        };

        if (!invisible) {
            BlockPos soilPos = pos.below();
            BlockState soilState = level.getBlockState(soilPos);
            if (!canSaplingSurvive.test(soilState, soilPos)) return false;

            for (int i = 1; i <= required; i++) {
                BlockState above = level.getBlockState(pos.above(i));
                if (above.getBlock() != this) return false;
                if (!above.getValue(INVISIBLE)) return false;
                if (above.getValue(STAGES) != stage) return false;
            }
            return true;
        }

        BlockPos current = pos;
        for (int depth = 0; depth <= 4; depth++) {
            BlockPos below = current.below();
            BlockState belowState = level.getBlockState(below);

            if (belowState.getBlock() != this) return false;

            if (!belowState.getValue(INVISIBLE)) {
                if (belowState.getValue(STAGES) != stage) return false;

                BlockPos soilPos = below.below();
                BlockState soilState = level.getBlockState(soilPos);
                if (!canSaplingSurvive.test(soilState, soilPos)) return false;

                for (int i = 1; i <= required; i++) {
                    BlockState above = level.getBlockState(below.above(i));
                    if (above.getBlock() != this) return false;
                    if (!above.getValue(INVISIBLE)) return false;
                    if (above.getValue(STAGES) != stage) return false;
                }

                int yOffset = pos.getY() - below.getY();
                return yOffset >= 1 && yOffset <= required;
            }

            current = below;
        }

        return false;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(STAGES).equals(3) ? state.getShape(level, pos) : Shapes.empty();
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide() && !state.canSurvive(level, pos)) {
            breakWholeSapling((ServerLevel) level, pos);
        }
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
    }

    private void breakWholeSapling(ServerLevel level, BlockPos pos) {
        BlockPos base = pos;
        while (level.getBlockState(base.below()).getBlock() == this) {
            base = base.below();
        }

        List<BlockPos> parts = new ArrayList<>();
        BlockPos cur = base;
        while (level.getBlockState(cur).getBlock() == this) {
            parts.add(cur);
            cur = cur.above();
        }

        for (BlockPos partPos : parts) {
            level.destroyBlock(partPos, true);
        }

        level.destroyBlock(base, true);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DawnRedwoodSaplingBlockEntity(pos, state);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return !state.getValue(INVISIBLE);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return (double) level.random.nextFloat() < 0.45;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        this.advanceTree(level, pos, state, random);
    }

    private static int requiredInvisibleForStage(int stage) {
        return switch (stage) {
            case 2 -> 1;
            case 3 -> 3;
            default -> 0;
        };
    }
}
