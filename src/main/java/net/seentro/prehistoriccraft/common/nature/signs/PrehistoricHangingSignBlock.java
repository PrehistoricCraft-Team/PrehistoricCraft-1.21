package net.seentro.prehistoriccraft.common.nature.signs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class PrehistoricHangingSignBlock extends CeilingHangingSignBlock {
    public PrehistoricHangingSignBlock(WoodType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PrehistoricHangingSignBlockEntity(pos, state);
    }
}
