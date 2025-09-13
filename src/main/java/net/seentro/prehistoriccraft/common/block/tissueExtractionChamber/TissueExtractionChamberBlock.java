package net.seentro.prehistoriccraft.common.block.tissueExtractionChamber;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;
import org.jetbrains.annotations.Nullable;

public class TissueExtractionChamberBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final MapCodec<TissueExtractionChamberBlock> CODEC = simpleCodec(TissueExtractionChamberBlock::new);

    private static final VoxelShape BOUNDING_BOX_NORTH = Shapes.or(
            Block.box(1, 0, 0, 15, 4, 4),
            Block.box(11, 4, 1, 13, 15, 3),
            Block.box(7.5, 4, 1, 8.5, 11, 2),
            Block.box(3, 4, 1, 5, 15, 3),
            Block.box(10, 2, 4, 11, 3, 5),
            Block.box(5, 2, 4, 6, 3, 5),
            Block.box(4, 0, 5, 12, 6, 10.9),
            Block.box(6, 6, 6, 10, 11, 10),
            Block.box(5, 11, 0, 11, 16, 10.9)
    );
    private static final VoxelShape BOUNDING_BOX_SOUTH = Shapes.or(
            Block.box(1, 0, 12, 15, 4, 16),
            Block.box(3, 4, 13, 5, 15, 15),
            Block.box(7.5, 4, 14, 8.5, 11, 15),
            Block.box(11, 4, 13, 13, 15, 15),
            Block.box(5, 2, 11, 6, 3, 12),
            Block.box(10, 2, 11, 11, 3, 12),
            Block.box(4, 0, 5.1, 12, 6, 11),
            Block.box(6, 6, 6, 10, 11, 10),
            Block.box(5, 11, 5.1, 11, 16, 16)
    );
    private static final VoxelShape BOUNDING_BOX_EAST = Shapes.or(
            Block.box(0, 0, 1, 4, 4, 15),
            Block.box(1, 4, 3, 3, 15, 5),
            Block.box(1, 4, 7.5, 2, 11, 8.5),
            Block.box(1, 4, 11, 3, 15, 13),
            Block.box(4, 2, 5, 5, 3, 6),
            Block.box(4, 2, 10, 5, 3, 11),
            Block.box(5, 0, 4, 10.9, 6, 12),
            Block.box(6, 6, 6, 10, 11, 10),
            Block.box(0, 11, 5, 10.9, 16, 11)
    );
    private static final VoxelShape BOUNDING_BOX_WEST = Shapes.or(
            Block.box(12, 0, 1, 16, 4, 15),
            Block.box(13, 4, 11, 15, 15, 13),
            Block.box(14, 4, 7.5, 15, 11, 8.5),
            Block.box(13, 4, 3, 15, 15, 5),
            Block.box(11, 2, 10, 12, 3, 11),
            Block.box(11, 2, 5, 12, 3, 6),
            Block.box(5.1, 0, 4, 11, 6, 12),
            Block.box(6, 6, 6, 10, 11, 10),
            Block.box(5.1, 11, 5, 16, 16, 11)
    );


    public TissueExtractionChamberBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch(state.getValue(FACING)) {
            case NORTH -> BOUNDING_BOX_NORTH;
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
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
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
        return new TissueExtractionChamberBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : createTickerHelper(blockEntityType, PrehistoricBlockEntityTypes.TISSUE_EXTRACTION_CHAMBER_BLOCK_ENTITY.get(), ((level1, blockPos, blockState, blockEntity) -> blockEntity.tick(level1, blockPos, blockState)));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof TissueExtractionChamberBlockEntity blockEntity) {
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(blockEntity, Component.translatable("block.prehistoriccraft.tissue_extraction_chamber")), pos);
                blockEntity.triggerAnim("drawerController", "open_drawers");
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof TissueExtractionChamberBlockEntity blockEntity) {
                blockEntity.drop();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
