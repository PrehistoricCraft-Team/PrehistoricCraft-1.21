package net.seentro.prehistoriccraft.common.block.fossiliferousStone;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.seentro.prehistoriccraft.core.enums.FossilTypes;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;

import java.util.HashMap;
import java.util.Map;

// static net.seentro.prehistoriccraft.registry.PrehistoricBlocks.*;

public class FossiliferousStoneBlock extends Block {
    private final FossilTypes FOSSIL_TYPE;
    private static final Map<FossilTypes, DeferredBlock<?>> fossilMap = new HashMap<>();
    public FossiliferousStoneBlock(Properties properties, FossilTypes fossilType) {
        super(properties);
        this.FOSSIL_TYPE = fossilType;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide() && stack.getItem() == PrehistoricItems.PLASTER_WRAP.get()) {
            DeferredBlock<?> plasteredFossil = fossilMap.get(FOSSIL_TYPE);
            if (plasteredFossil == null) return ItemInteractionResult.FAIL;

            level.setBlock(pos, plasteredFossil.get().defaultBlockState(), Block.UPDATE_ALL);
            stack.consume(1, player);
        }

        if (!level.isClientSide()) {
            player.displayClientMessage(Component.literal(String.valueOf(level.getBiome(pos).value().getBaseTemperature())), true);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    static {
        fossilMap.put(FossilTypes.CAMBRIAN, PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE);
        fossilMap.put(FossilTypes.CARBONIFEROUS, PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE);
        fossilMap.put(FossilTypes.CRETACEOUS, PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE);
        fossilMap.put(FossilTypes.DEVONIAN, PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE);
        fossilMap.put(FossilTypes.JURASSIC, PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE);
        fossilMap.put(FossilTypes.NEOGENE, PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE);
        fossilMap.put(FossilTypes.ORDOVICIAN, PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE);
        fossilMap.put(FossilTypes.PALEOGENE, PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE);
        fossilMap.put(FossilTypes.PERMIAN, PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE);
        fossilMap.put(FossilTypes.PRECAMBRIAN, PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE);
        fossilMap.put(FossilTypes.SILURIAN, PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE);
        fossilMap.put(FossilTypes.TRIASSIC, PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE);
    }
}
