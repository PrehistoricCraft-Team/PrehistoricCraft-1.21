package net.seentro.prehistoriccraft.common.block.nature.plantStructures.greatHorsetail;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.seentro.prehistoriccraft.common.block.nature.plantStructures.neocalamites.NeocalamitesBlock;
import org.jetbrains.annotations.Nullable;

public class GreatHorsetailBlock extends DoublePlantBlock implements EntityBlock {
    public static final MapCodec<GreatHorsetailBlock> CODEC = simpleCodec(GreatHorsetailBlock::new);
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 1, 3);

    public GreatHorsetailBlock(Properties properties) {
        super(properties);
        //this.registerDefaultState(this.getStateDefinition().any().setValue(VARIANT, 1));
    }

    @Override
    public MapCodec<? extends DoublePlantBlock> codec() {
        return CODEC;
    }

    protected static final VoxelShape SHAPE = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 13.0F, 14.0F);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.DOUBLE_BLOCK_HALF, VARIANT);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GreatHorsetailBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(VARIANT, context.getLevel().random.nextInt(1, 4));
    }
}
