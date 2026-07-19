package net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.roughHorsetail;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.seentro.prehistoriccraft.common.block.nature.templates.DoubleVariantBushBlock;
import org.jetbrains.annotations.Nullable;

public class RoughHorsetailBlock extends DoubleVariantBushBlock implements EntityBlock {
    public RoughHorsetailBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RoughHorsetailBlockEntity(blockPos, blockState);
    }
}
