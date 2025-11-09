package net.seentro.prehistoriccraft.common.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;

public class LoamGrassBlock extends GrassBlock {

    // Constructor for LoamGrassBlock
    public LoamGrassBlock(Block loamBlock, BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context);
    }

    public BlockState getSoilState() {
        return PrehistoricBlocks.LOAM.get().defaultBlockState();  // Return loam as the soil
    }
}

