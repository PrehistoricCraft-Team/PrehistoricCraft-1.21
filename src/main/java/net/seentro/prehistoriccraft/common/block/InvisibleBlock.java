package net.seentro.prehistoriccraft.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.seentro.prehistoriccraft.core.multiblock.InvisibleMultiblockCreator;
import net.seentro.prehistoriccraft.core.multiblock.MultiblockController;
import org.jetbrains.annotations.Nullable;

public class InvisibleBlock extends BaseEntityBlock implements MultiblockController {
    public static final MapCodec<InvisibleBlock> CODEC = simpleCodec(InvisibleBlock::new);
    public InvisibleBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void setOriginPosition(BlockPos originPosition, BlockPos pos, Level level) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof InvisibleBlockEntity blockEntity) {
            blockEntity.setOriginPos(originPosition);
        }
    }

    @Override
    public void notifyOfBreak(Level level, Block block, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof InvisibleBlockEntity blockEntity)) return;

        if (level.getBlockState(blockEntity.getOriginPos()).getBlock() != block) {
            //7level.removeBlock(pos, false);
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (level.isClientSide()) return;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof InvisibleBlockEntity blockEntity)) return;

        BlockPos originPos = blockEntity.getOriginPos();
        if (originPos == null) return;

        // Only trigger if origin still exists and is a valid creator
        BlockState originState = level.getBlockState(originPos);
        if (originState.getBlock() instanceof InvisibleMultiblockCreator creator) {
            creator.breakMultiblock(level, originPos);
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    /* BLOCK ENTITY */

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InvisibleBlockEntity(pos, state);
    }
}
