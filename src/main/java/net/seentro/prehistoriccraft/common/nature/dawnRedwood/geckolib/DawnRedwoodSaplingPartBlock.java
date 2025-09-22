package net.seentro.prehistoriccraft.common.nature.dawnRedwood.geckolib;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sun.jna.internal.ReflectionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;
import net.seentro.prehistoriccraft.common.nature.dawnRedwood.DawnRedwoodSaplingBlock;
import net.seentro.prehistoriccraft.common.nature.dawnRedwood.DawnRedwoodSaplingBlockEntity;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class DawnRedwoodSaplingPartBlock extends Block {
    private final Block baseBlock;

    public DawnRedwoodSaplingPartBlock(Properties properties) {
        super(properties);
        this.baseBlock = PrehistoricBlocks.DAWN_REDWOOD_SAPLING.get();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos cur = pos;
        final int MAX_DEPTH = 4;
        for (int i = 0; i <= MAX_DEPTH; i++) {
            BlockPos below = cur.below();
            BlockState blockState = level.getBlockState(below);
            if (blockState.getBlock() == baseBlock) {
                if (!(blockState.getBlock() instanceof DawnRedwoodSaplingBlock && blockState.getValue(DawnRedwoodSaplingBlock.INVISIBLE))) {
                    return true;
                }
            }
            if (blockState.getBlock() != this) return false;
            cur = below;
        }
        return false;
    }

    // optional: override onNeighborChanged to destroy self if canSurvive false
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide() && !state.canSurvive(level, pos)) {
            level.destroyBlock(pos, false);
        }
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
    }
}
