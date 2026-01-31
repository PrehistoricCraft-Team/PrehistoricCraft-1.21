package net.seentro.prehistoriccraft.common.nature.plantStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.common.util.TriState;

import java.util.function.BiPredicate;

public class PlantStructure extends Block {
    public static final BooleanProperty INVISIBLE = BooleanProperty.create("invisible");
    public static final IntegerProperty HEIGHT = IntegerProperty.create("height", 1, 6);

    public PlantStructure(Properties properties) {
        super(properties);
    }

    // Can place on?
    protected boolean mayPlaceOn(BlockState state) {
        return state.is(BlockTags.DIRT) || state.getBlock() instanceof FarmBlock;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        int height = state.getValue(HEIGHT);

        for (int i = 0; i < height; i++) {
            BlockState invisibleState = state.setValue(INVISIBLE, true);
            level.setBlock(pos.offset(0, i, 0), invisibleState, Block.UPDATE_CLIENTS);
        }

        level.setBlock(pos, state, Block.UPDATE_ALL);
    }

    // Can the tree survive?
    // Need to check if the ground is valid too!
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        boolean isInvisible = state.getValue(INVISIBLE);
        int height = state.getValue(HEIGHT);

        // Can the base sapling block survive on ground?
        BiPredicate<BlockState, BlockPos> canSaplingSurvive = (soilState, soilPos) -> {
            TriState decision = soilState.canSustainPlant(level, soilPos, Direction.UP, state);
            if (!decision.isDefault()) return decision.isTrue();
            return this.mayPlaceOn(soilState);
        };

        // Only execute if we are the base
        if (!isInvisible) {
            if (!canSaplingSurvive.test(level.getBlockState(pos.below()), pos.below())) return false;

            for (int i = 1; i < height; i++) {
                BlockState offsetState = level.getBlockState(pos.above(i));

                if (offsetState.getBlock() != this) return false;
                if (!offsetState.getValue(INVISIBLE)) return false;
            }

            return true;
        }

        // Check if all blocks are present below
        // Only runs for the invisible blocks

        BlockPos currentPos = pos;
        BlockPos basePos = pos;

        while (level.getBlockState(currentPos.below()).getBlock() == this) {
            if (!level.getBlockState(currentPos.below()).getValue(INVISIBLE)) {
                basePos = currentPos.below();
            }
        }

        int countedHeight = 0;

        while (level.getBlockState(basePos.above()).getBlock() == this) {
            countedHeight++;
            basePos = basePos.above();
        }

        return countedHeight == height;
    }

    // Prevent dropping the invisible parts
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.isCreative()) {
            preventDropFromBase(level, pos);
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    private void preventDropFromBase(Level level, BlockPos pos) {
        BlockPos bottomPos = pos;
        while (level.getBlockState(bottomPos.below()).getBlock() == this) {
            bottomPos = bottomPos.below();
        }

        level.destroyBlock(bottomPos, false);
    }
}
