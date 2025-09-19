package net.seentro.prehistoriccraft.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;

public class InvisibleBlockEntity extends BlockEntity {
    private BlockPos originPos;
    public InvisibleBlockEntity(BlockPos pos, BlockState blockState) {
        super(PrehistoricBlockEntityTypes.INVISIBLE_BLOCK_ENTITY.get(), pos, blockState);
    }

    public BlockPos getOriginPos() {
        return this.originPos;
    }
    public void setOriginPos(BlockPos pos) {
        this.originPos = pos;
    }
}
