package net.seentro.prehistoriccraft.common.nature.signs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;

public class PrehistoricHangingSignBlockEntity extends SignBlockEntity {
    public PrehistoricHangingSignBlockEntity(BlockPos pos, BlockState blockState) {
        super(PrehistoricBlockEntityTypes.PREHISTORIC_HANGING_SIGN.get(), pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return PrehistoricBlockEntityTypes.PREHISTORIC_HANGING_SIGN.get();
    }
}
