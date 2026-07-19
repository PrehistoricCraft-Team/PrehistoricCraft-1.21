package net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.aridHorsetail;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.seentro.prehistoriccraft.common.block.nature.templates.DoubleVariantBushBlock;
import org.jetbrains.annotations.Nullable;

public class AridHorsetailBlock extends DoubleVariantBushBlock implements EntityBlock {
    public AridHorsetailBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AridHorsetailBlockEntity(blockPos, blockState);
    }
}
