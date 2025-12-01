package net.seentro.prehistoriccraft.common.block.dnaSeparationFilter;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.seentro.prehistoriccraft.registry.PrehistoricBlockEntityTypes;
import org.jetbrains.annotations.Nullable;


public class DNASeparationFilterBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final MapCodec<DNASeparationFilterBlock> CODEC = simpleCodec(DNASeparationFilterBlock::new);

    private static final VoxelShape BOUNDING_BOX_NORTH = Shapes.or(
            Block.box(0, 0, 11, 16, 16, 13),
            Block.box(3.5, 0, 13, 12.5, 15, 15),
            Block.box(6, 15, 13, 10, 16, 15),
            Block.box(0, 0, 5, 16, 6, 13),
            Block.box(5, 0, 0, 11, 6, 5),
            Block.box(3.5, 6, 3, 12.5, 9, 11),
            Block.box(0, 9, 5, 16, 12, 13),
            Block.box(5, 9, 0, 11, 12, 5),
            Block.box(3.5, 12, 3, 12.5, 15, 11),
            Block.box(0, 15, 5, 16, 16, 13),
            Block.box(5, 15, 0, 11, 16, 5)
    );
    private static final VoxelShape BOUNDING_BOX_NORTH_TOP = Shapes.or(
            Block.box(6, 0, 13, 10, 6, 15),
            Block.box(6, 2, 11, 10, 6, 13),
            Block.box(4, 3, 8, 12, 13, 11),
            Block.box(5.5, 3, 5, 10.5, 13, 8),
            Block.box(5.5, 13, 6, 10.5, 13.5, 10),
            Block.box(0, 0, 5, 16, 2, 13),
            Block.box(5, 0, 0, 11, 2, 5),
            Block.box(3, 2, 5, 13, 3, 11),
            Block.box(6, 2, 2, 10, 3, 5),
            Block.box(2, 9.5, 9.5, 3, 15.5, 15.5),
            Block.box(13, 9.5, 9.5, 14, 15.5, 15.5),
            Block.box(3, 10, 10, 13, 15, 15)
    );
    private static final VoxelShape BOUNDING_BOX_SOUTH = Shapes.or(
            Block.box(0, 0, 3, 16, 16, 5),
            Block.box(3.5, 0, 1, 12.5, 15, 3),
            Block.box(6, 15, 1, 10, 16, 3),
            Block.box(0, 0, 3, 16, 6, 11),
            Block.box(5, 0, 11, 11, 6, 16),
            Block.box(3.5, 6, 5, 12.5, 9, 13),
            Block.box(0, 9, 3, 16, 12, 11),
            Block.box(5, 9, 11, 11, 12, 16),
            Block.box(3.5, 12, 5, 12.5, 15, 13),
            Block.box(0, 15, 3, 16, 16, 11),
            Block.box(5, 15, 11, 11, 16, 16)
    );
    private static final VoxelShape BOUNDING_BOX_SOUTH_TOP = Shapes.or(
            Block.box(6, 0, 1, 10, 6, 3),
            Block.box(6, 2, 3, 10, 6, 5),
            Block.box(4, 3, 5, 12, 13, 8),
            Block.box(5.5, 3, 8, 10.5, 13, 11),
            Block.box(5.5, 13, 6, 10.5, 13.5, 10),
            Block.box(0, 0, 3, 16, 2, 11),
            Block.box(5, 0, 11, 11, 2, 16),
            Block.box(3, 2, 5, 13, 3, 11),
            Block.box(6, 2, 11, 10, 3, 14),
            Block.box(2, 9.5, 0.5, 3, 15.5, 6.5),
            Block.box(13, 9.5, 0.5, 14, 15.5, 6.5),
            Block.box(3, 10, 1, 13, 15, 6)
    );
    private static final VoxelShape BOUNDING_BOX_EAST = Shapes.or(
            Block.box(3, 0, 0, 5, 16, 16),
            Block.box(1, 0, 3.5, 3, 15, 12.5),
            Block.box(1, 15, 6, 3, 16, 10),
            Block.box(3, 0, 0, 11, 6, 16),
            Block.box(11, 0, 5, 16, 6, 11),
            Block.box(5, 6, 3.5, 13, 9, 12.5),
            Block.box(3, 9, 0, 11, 12, 16),
            Block.box(11, 9, 5, 16, 12, 11),
            Block.box(5, 12, 3.5, 13, 15, 12.5),
            Block.box(3, 15, 0, 11, 16, 16),
            Block.box(11, 15, 5, 16, 16, 11)
    );
    private static final VoxelShape BOUNDING_BOX_EAST_TOP = Shapes.or(
            Block.box(1, 0, 6, 3, 6, 10),
            Block.box(3, 2, 6, 5, 6, 10),
            Block.box(5, 3, 4, 8, 13, 12),
            Block.box(8, 3, 5.5, 11, 13, 10.5),
            Block.box(6, 13, 5.5, 10, 13.5, 10.5),
            Block.box(3, 0, 0, 11, 2, 16),
            Block.box(11, 0, 5, 16, 2, 11),
            Block.box(5, 2, 3, 11, 3, 13),
            Block.box(11, 2, 6, 14, 3, 10),
            Block.box(0.5, 9.5, 2, 6.5, 15.5, 3),
            Block.box(0.5, 9.5, 13, 6.5, 15.5, 14),
            Block.box(1, 10, 3, 6, 15, 13)
    );
    private static final VoxelShape BOUNDING_BOX_WEST = Shapes.or(
            Block.box(11, 0, 0, 13, 16, 16),
            Block.box(13, 0, 3.5, 15, 15, 12.5),
            Block.box(13, 15, 6, 15, 16, 10),
            Block.box(5, 0, 0, 13, 6, 16),
            Block.box(0, 0, 5, 5, 6, 11),
            Block.box(3, 6, 3.5, 11, 9, 12.5),
            Block.box(5, 9, 0, 13, 12, 16),
            Block.box(0, 9, 5, 5, 12, 11),
            Block.box(3, 12, 3.5, 11, 15, 12.5),
            Block.box(5, 15, 0, 13, 16, 16),
            Block.box(0, 15, 5, 5, 16, 11)
    );
    private static final VoxelShape BOUNDING_BOX_WEST_TOP = Shapes.or(
            Block.box(13, 0, 6, 15, 6, 10),
            Block.box(11, 2, 6, 13, 6, 10),
            Block.box(8, 3, 4, 11, 13, 12),
            Block.box(5, 3, 5.5, 8, 13, 10.5),
            Block.box(6, 13, 5.5, 10, 13.5, 10.5),
            Block.box(5, 0, 0, 13, 2, 16),
            Block.box(0, 0, 5, 5, 2, 11),
            Block.box(5, 2, 3, 11, 3, 13),
            Block.box(2, 2, 6, 5, 3, 10),
            Block.box(9.5, 9.5, 13, 15.5, 15.5, 14),
            Block.box(9.5, 9.5, 2, 15.5, 15.5, 3),
            Block.box(10, 10, 3, 15, 15, 13)
    );

    public DNASeparationFilterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return switch (state.getValue(FACING)) {
                case SOUTH -> BOUNDING_BOX_SOUTH;
                case WEST -> BOUNDING_BOX_WEST;
                case EAST -> BOUNDING_BOX_EAST;
                default -> BOUNDING_BOX_NORTH;
            };
        }

        return switch (state.getValue(FACING)) {
            case SOUTH -> BOUNDING_BOX_SOUTH_TOP;
            case WEST -> BOUNDING_BOX_WEST_TOP;
            case EAST -> BOUNDING_BOX_EAST_TOP;
            default -> BOUNDING_BOX_NORTH_TOP;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.INVISIBLE;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    /* DOUBLE BLOCK */

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (facing.getAxis() != Direction.Axis.Y
                || doubleblockhalf == DoubleBlockHalf.LOWER != (facing == Direction.UP)
                || facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(level, currentPos)
                    ? Blocks.AIR.defaultBlockState()
                    : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context)) {
            return this.defaultBlockState()
                    .setValue(FACING, context.getHorizontalDirection().getOpposite())
                    .setValue(HALF, DoubleBlockHalf.LOWER);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockPos blockpos = pos.above();
        level.setBlock(blockpos, state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return super.canSurvive(state, level, pos);
        } else {
            BlockState blockstate = level.getBlockState(pos.below());
            if (state.getBlock() != this) return super.canSurvive(state, level, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            if (player.isCreative()) {
                preventDropFromBottomPart(level, pos, state, player);
            } else {
                dropResources(state, level, pos, null, player, player.getMainHandItem());
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @javax.annotation.Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(level, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
    }

    protected static void preventDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.is(state.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockstate1 = blockstate.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(blockpos, blockstate1, 35);
                level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }
    }


    /* BLOCK ENTITY */

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER
                ? new DNASeparationFilterBlockEntity(pos, state)
                : null;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, PrehistoricBlockEntityTypes.DNA_SEPARATION_FILTER_BLOCK_ENTITY.get(),
                (lvl, pos, blockState, blockEntity) -> blockEntity.tick(lvl, pos, blockState));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        BlockPos bottomPos = state.getValue(HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos;

        if (state.getValue(HALF) == DoubleBlockHalf.UPPER &&stack.is(Items.WATER_BUCKET)) {
            if (!level.isClientSide() && level.getBlockEntity(bottomPos) instanceof DNASeparationFilterBlockEntity blockEntity) {
                if (blockEntity.tryInsertWaterFromBucket(player, hand)) {
                    return ItemInteractionResult.SUCCESS;
                }
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        if (!level.isClientSide() && level.getBlockEntity(bottomPos) instanceof DNASeparationFilterBlockEntity blockEntity) {
            player.openMenu(new SimpleMenuProvider(blockEntity,
                    Component.translatable("block.prehistoriccraft.dna_separation_filter")), bottomPos);

            if (blockEntity.working == 0) {
                blockEntity.triggerAnim("controller", "open_doors");
            }

            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
                if (level.getBlockEntity(pos) instanceof DNASeparationFilterBlockEntity blockEntity) {
                    blockEntity.drop();
                    level.updateNeighbourForOutputSignal(pos, this);
                }
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}