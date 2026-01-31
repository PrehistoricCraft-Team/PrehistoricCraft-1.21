package net.seentro.prehistoriccraft.common.nature.dawnRedwood;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.seentro.prehistoriccraft.common.nature.plantStructures.ThreeStageFlowerPlantStructure;
import org.jetbrains.annotations.Nullable;

public class DawnRedwoodSaplingBlock extends ThreeStageFlowerPlantStructure implements BonemealableBlock, EntityBlock {
    public DawnRedwoodSaplingBlock(SuspiciousStewEffects effects, TreeGrower treeGrower, Properties properties) {
        super(effects, treeGrower, properties);
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
        if (state.getValue(INVISIBLE)) return;

        this.advanceTree(level, pos, state, random);
    }
}
