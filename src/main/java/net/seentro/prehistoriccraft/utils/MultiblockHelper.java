package net.seentro.prehistoriccraft.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class MultiblockHelper {
    public static void destroyBlockWithTool(Level level, BlockPos pos, Player player) {
        BlockState state = level.getBlockState(pos);

        if (level instanceof ServerLevel serverLevel) {
            List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, level.getBlockEntity(pos), player, player.getMainHandItem());
            for (ItemStack drop : drops) {
                Block.popResource(level, pos, drop);
            }
        }

        level.destroyBlock(pos, false);
    }
}
