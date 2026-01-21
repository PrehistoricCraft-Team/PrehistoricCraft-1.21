package net.seentro.prehistoriccraft.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.seentro.prehistoriccraft.registry.PrehistoricTags;

public class ExcavatorPickaxeItem extends Item {
    public ExcavatorPickaxeItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(PrehistoricTags.Blocks.PLASTERED_FOSSIL);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.is(PrehistoricTags.Blocks.PLASTERED_FOSSIL) ? 6.0F : 1.0F;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        stack.hurtAndBreak(1, miningEntity, EquipmentSlot.MAINHAND);
        return true;
    }
}
