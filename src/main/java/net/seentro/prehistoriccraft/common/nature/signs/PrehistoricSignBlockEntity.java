package net.seentro.prehistoriccraft.common.nature.signs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;

public class PrehistoricSignBlockEntity extends SignBlockEntity {
    public PrehistoricSignBlockEntity(BlockPos pos, BlockState blockState) {
        super(PrehistoricBlockEntityTypes.PREHISTORIC_SIGN.get(), pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return PrehistoricBlockEntityTypes.PREHISTORIC_SIGN.get();
    }
}
