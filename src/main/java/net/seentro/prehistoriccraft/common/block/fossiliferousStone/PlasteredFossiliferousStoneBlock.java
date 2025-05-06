package net.seentro.prehistoriccraft.common.block.fossiliferousStone;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PlasteredFossiliferousStoneBlock extends Block {
    public PlasteredFossiliferousStoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        /*switch (level.random.nextIntBetweenInclusive(0, 5)) {
            case 0 -> stack.set(PrehistoricDataComponents.FOSSIL_QUALITY.get(), "damaged");
        }
         */
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
    }
}
