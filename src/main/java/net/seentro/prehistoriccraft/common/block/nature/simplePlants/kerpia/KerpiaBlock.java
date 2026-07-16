package net.seentro.prehistoriccraft.common.block.nature.simplePlants.kerpia;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.seentro.prehistoriccraft.common.block.nature.neocalamites.NeocalamitesBlock;
import org.jetbrains.annotations.Nullable;

// TODO: Finish Kerpia
public class KerpiaBlock extends BushBlock implements EntityBlock {
    public static final MapCodec<KerpiaBlock> CODEC = simpleCodec(KerpiaBlock::new);
    public KerpiaBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new KerpiaBlockEntity(blockPos, blockState);
    }
}