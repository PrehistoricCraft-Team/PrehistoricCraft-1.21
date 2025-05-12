package net.seentro.prehistoriccraft.common.block.fossiliferousStone;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.seentro.prehistoriccraft.core.enumerators.FossilTypes;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;

import static net.seentro.prehistoriccraft.registry.PrehistoricBlocks.*;

public class FossiliferousStoneBlock extends Block {
    private FossilTypes fossilType;
    public FossiliferousStoneBlock(Properties properties, FossilTypes fossilType) {
        super(properties);
        this.fossilType = fossilType;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide() && stack.getItem() == PrehistoricItems.PLASTER_WRAP.get()) {
            switch (fossilType) {
                case CAMBRIAN -> setBlockAtPosition(level, pos, PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE);
                case CARBONIFEROUS -> setBlockAtPosition(level, pos, PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE);
                case CRETACEOUS -> setBlockAtPosition(level, pos, PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE);
                case DEVONIAN -> setBlockAtPosition(level, pos, PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE);
                case JURASSIC -> setBlockAtPosition(level, pos, PLASTERED_JURASSIC_FOSSILIFEROUS_STONE);
                case NEOGENE -> setBlockAtPosition(level, pos, PLASTERED_NEOGENE_FOSSILIFEROUS_STONE);
                case ORDOVICIAN -> setBlockAtPosition(level, pos, PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE);
                case PALEOGENE -> setBlockAtPosition(level, pos, PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE);
                case PERMIAN -> setBlockAtPosition(level, pos, PLASTERED_PERMIAN_FOSSILIFEROUS_STONE);
                case PRECAMBRIAN -> setBlockAtPosition(level, pos, PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE);
                case SILURIAN -> setBlockAtPosition(level, pos, PLASTERED_SILURIAN_FOSSILIFEROUS_STONE);
                case TRIASSIC -> setBlockAtPosition(level, pos, PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE);
            }
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    private static void setBlockAtPosition(Level level, BlockPos pos, DeferredBlock<Block> block) {
        level.setBlock(pos, block.get().defaultBlockState(), Block.UPDATE_ALL);
    }
}
