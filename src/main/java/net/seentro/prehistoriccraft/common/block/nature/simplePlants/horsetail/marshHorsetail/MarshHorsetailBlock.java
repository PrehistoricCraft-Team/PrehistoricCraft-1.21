package net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.marshHorsetail;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.roughHorsetail.RoughHorsetailBlockEntity;
import net.seentro.prehistoriccraft.common.block.nature.templates.DoubleVariantBushBlock;
import org.jetbrains.annotations.Nullable;

public class MarshHorsetailBlock extends DoubleVariantBushBlock implements EntityBlock {
    public MarshHorsetailBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MarshHorsetailBlockEntity(blockPos, blockState);
    }
}
