package net.seentro.prehistoriccraft.common.block.nature.plantStructures.greatHorsetail;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.seentro.prehistoriccraft.registry.PrehistoricBlockStateProperties.VARIANT_TRIPLE;

public class GreatHorsetailBlock extends DoublePlantBlock implements EntityBlock {
    public static final MapCodec<GreatHorsetailBlock> CODEC = simpleCodec(GreatHorsetailBlock::new);

    public GreatHorsetailBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends DoublePlantBlock> codec() {
        return CODEC;
    }

    protected static final VoxelShape SHAPE = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 16.0F, 14.0F);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.DOUBLE_BLOCK_HALF, VARIANT_TRIPLE);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GreatHorsetailBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        int variant = Math.floorMod(context.getClickedPos().hashCode(), 3) + 1; // calculate variant based on position
        return this.defaultBlockState().setValue(VARIANT_TRIPLE, variant);
    }
}
