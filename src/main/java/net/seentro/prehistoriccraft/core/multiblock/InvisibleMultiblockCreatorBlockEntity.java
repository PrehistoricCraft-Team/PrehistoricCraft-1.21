package net.seentro.prehistoriccraft.core.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;

import java.util.ArrayList;
import java.util.List;

public class InvisibleMultiblockCreatorBlockEntity extends BlockEntity {
    private final List<BlockPos> multiblockParts = new ArrayList<>();
    private BlockState state;
    public InvisibleMultiblockCreatorBlockEntity(BlockPos pos, BlockState blockState) {
        super(PrehistoricBlockEntityTypes.INVISIBLE_MULTIBLOCK_CREATOR_BLOCK_ENTITY.get(), pos, blockState);
    }

    public List<BlockPos> getMultiblockParts() {
        return multiblockParts;
    }

    public void addToMultiblockParts(BlockPos pos) {
        this.multiblockParts.add(pos);
    }

    public void clearMultiblockParts() {
        this.multiblockParts.clear();
    }

    public BlockState getState() {
        return state;
    }

    public void setState(BlockState state) {
        this.state = state;
    }

}
