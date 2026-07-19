package net.seentro.prehistoriccraft.common.block.nature.simplePlants.horsetail.woodHorsetail;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.seentro.prehistoriccraft.common.block.nature.simplePlants.kerpia.KerpiaBlock;
import net.seentro.prehistoriccraft.common.block.nature.templates.DoubleVariantBushBlock;
import org.jetbrains.annotations.Nullable;

public class WoodHorsetailBlock extends DoubleVariantBushBlock implements EntityBlock {
    public WoodHorsetailBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WoodHorsetailBlockEntity(blockPos, blockState);
    }
}
