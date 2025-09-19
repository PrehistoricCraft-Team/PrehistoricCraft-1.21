package net.seentro.prehistoriccraft.core.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public interface MultiblockController {
    void notifyOfBreak(Level level, Block block, BlockPos pos);
    void setOriginPosition(BlockPos originPosition, BlockPos pos, Level level);
}
