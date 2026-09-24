package net.seentro.prehistoriccraft.common.block.nature.templates;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.seentro.prehistoriccraft.registry.PrehistoricBlockStateProperties.VARIANT_DOUBLE;

public class DoubleVariantBushBlock extends BushBlock {
    public static final MapCodec<DoubleVariantBushBlock> CODEC = simpleCodec(DoubleVariantBushBlock::new);
    public DoubleVariantBushBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(VARIANT_DOUBLE, 1));
    }

    protected static final VoxelShape SHAPE = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 13.0F, 14.0F);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIANT_DOUBLE);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        int variant = Math.floorMod(context.getClickedPos().hashCode(), 2) + 1; // calculate variant based on position
        return defaultBlockState().setValue(VARIANT_DOUBLE, variant);
    }
}
