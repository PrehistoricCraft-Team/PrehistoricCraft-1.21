package net.seentro.prehistoriccraft.common.nature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class FlammableRotatedPillarBlock extends RotatedPillarBlock {
    private static Map<Block, Block> STRIP_MAP = new HashMap<>();

    public FlammableRotatedPillarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        STRIP_MAP.put(PrehistoricBlocks.DAWN_REDWOOD_LOG.get(), PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_LOG.get());
        STRIP_MAP.put(PrehistoricBlocks.DAWN_REDWOOD_WOOD.get(), PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_WOOD.get());

        if (context.getItemInHand().getItem() instanceof AxeItem) {
            Block logBlock = state.getBlock();
            if (STRIP_MAP.containsKey(logBlock)) {
                return STRIP_MAP.get(logBlock).defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }

        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
