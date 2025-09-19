package net.seentro.prehistoriccraft.core.multiblock;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.InvisibleBlockEntity;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import org.jetbrains.annotations.Nullable;

public class InvisibleMultiblockCreator extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final MapCodec<InvisibleMultiblockCreator> CODEC = simpleCodec(InvisibleMultiblockCreator::new);

    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;

    private BlockState block;

    public InvisibleMultiblockCreator(Properties properties) {
        this(properties, 0, 0, 0);
    }

    public InvisibleMultiblockCreator(Properties properties, int x, int y, int z) {
        super(properties);
        this.sizeX = x;
        this.sizeY = y;
        this.sizeZ = z;

        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        Direction forward = state.getValue(FACING);
        Direction right = forward.getClockWise();

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof InvisibleMultiblockCreatorBlockEntity blockEntity)) return;

        blockEntity.clearMultiblockParts();

        blockEntity.setState(state);

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                for (int z = 0; z < sizeZ; z++) {
                    int rightOffset = x -(sizeX - 1); // Make sure we are placing at the right most corner
                    BlockPos toPlace = pos
                            .relative(right, rightOffset)
                            .relative(Direction.UP, y)
                            .relative(forward, z);

                    if (toPlace.equals(pos)) continue;

                    if (level.getBlockState(toPlace).canBeReplaced()) {
                        level.setBlock(toPlace, PrehistoricBlocks.INVISIBLE_BLOCK.get().defaultBlockState(), Block.UPDATE_ALL);
                        blockEntity.addToMultiblockParts(toPlace);
                        if (level.getBlockState(toPlace).getBlock() instanceof MultiblockController multiblockInterface) {
                            multiblockInterface.setOriginPosition(pos, toPlace, level);
                        }
                    } else {
                        breakMultiblock(level, pos);
                    }
                }
            }
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (level.isClientSide()) return;

        breakMultiblock(level, pos);
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    //TODO: Fix breaking the multiblock
    public void breakMultiblock(Level level, BlockPos originPos) {
        if (!(level.getBlockEntity(originPos) instanceof InvisibleMultiblockCreatorBlockEntity blockEntity)) return;

        Direction forward = blockEntity.getState().getValue(FACING);
        Direction right = forward.getClockWise();

         if (!level.getBlockState(originPos).is(Blocks.AIR)) {
             level.removeBlock(originPos, false);
         }

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                for (int z = 0; z < sizeZ; z++) {
                    BlockPos childPos = originPos
                            .relative(right, x - (sizeX - 1))
                            .relative(Direction.UP, y)
                            .relative(forward, z);

                    if (childPos.equals(originPos)) continue;

                    BlockEntity be = level.getBlockEntity(childPos);
                    if (be instanceof InvisibleBlockEntity childEntity) {
                        PrehistoricCraft.LOGGER.info("Child {} has origin {}", childPos, childEntity.getOriginPos());
                        if (originPos.equals(childEntity.getOriginPos())) {
                            level.removeBlock(childPos, false);
                        }
                    }
                }
            }
        }
    }

    /* BLOCK ENTITY */

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InvisibleMultiblockCreatorBlockEntity(pos, state);
    }
}
