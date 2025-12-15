package net.seentro.prehistoriccraft.common.block.dnaRecombinator;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;
import org.jetbrains.annotations.Nullable;

public class DNARecombinatorBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final MapCodec<DNARecombinatorBlock> CODEC = simpleCodec(DNARecombinatorBlock::new);

    private static final VoxelShape BOUNDING_BOX_NORTH = Shapes.or(
            Block.box(0, 0, 2, 16, 3, 16),
            Block.box(1, 3, 10, 6, 15, 15),
            Block.box(10, 3, 10, 15, 15, 15),
            Block.box(6, 3, 4, 10, 12, 16)
    );
    private static final VoxelShape BOUNDING_BOX_SOUTH = Shapes.or(
            Block.box(0, 0, 0, 16, 3, 14),
            Block.box(10, 3, 1, 15, 15, 6),
            Block.box(1, 3, 1, 6, 15, 6),
            Block.box(6, 3, 0, 10, 12, 12)
    );
    private static final VoxelShape BOUNDING_BOX_WEST = Shapes.or(
            Block.box(0, 0, 0, 14, 3, 16),
            Block.box(1, 3, 1, 6, 15, 6),
            Block.box(1, 3, 10, 6, 15, 15),
            Block.box(0, 3, 6, 12, 12, 10)
    );
    private static final VoxelShape BOUNDING_BOX_EAST = Shapes.or(
            Block.box(2, 0, 0, 16, 3, 16),
            Block.box(10, 3, 10, 15, 15, 15),
            Block.box(10, 3, 1, 15, 15, 6),
            Block.box(4, 3, 6, 16, 12, 10)
    );


    public DNARecombinatorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch(state.getValue(FACING)) {
            case SOUTH -> BOUNDING_BOX_SOUTH;
            case WEST -> BOUNDING_BOX_EAST;
            case EAST -> BOUNDING_BOX_WEST;
            default -> BOUNDING_BOX_NORTH;
        };
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    /* BLOCK ENTITY */

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DNARecombinatorBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : createTickerHelper(blockEntityType, PrehistoricBlockEntityTypes.DNA_RECOMBINATOR_BLOCK_ENTITY.get(), ((level1, blockPos, blockState, blockEntity) -> blockEntity.tick(level1, blockPos, blockState)));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof DNARecombinatorBlockEntity blockEntity) {
                player.openMenu(new SimpleMenuProvider(blockEntity, Component.translatable("block.prehistoriccraft.dna_recombinator")), pos);
                if (blockEntity.working == 0) blockEntity.triggerAnim("controller", "open_doors");
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof DNARecombinatorBlockEntity blockEntity) {
                blockEntity.drop();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
