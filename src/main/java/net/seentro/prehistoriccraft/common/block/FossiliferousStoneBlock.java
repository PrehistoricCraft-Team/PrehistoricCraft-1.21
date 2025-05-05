package net.seentro.prehistoriccraft.common.block;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;

import java.util.List;

public class FossiliferousStoneBlock extends Block {
    private final ItemLike fossilDrop;
    private final int min;
    private final int max;

    public FossiliferousStoneBlock(Properties props, ItemLike fossilDrop, int min, int max) {
        super(props);
        this.fossilDrop = fossilDrop;
        this.min = min;
        this.max = max;
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        ItemStack tool = builder.getOptionalParameter(LootContextParams.TOOL);
        RandomSource random = builder.getLevel().getRandom();

        // Drop self with Silk Touch
        if (tool != null && EnchantmentHelper.getItemEnchantmentLevel((Holder<Enchantment>) Enchantments.SILK_TOUCH, tool) > 0) {
            return List.of(new ItemStack(this));
        }

        // Only drop fossils with excavator_pickaxe
        if (tool != null && tool.is(PrehistoricItems.EXCAVATOR_PICKAXE.get())) {
            int fortune = EnchantmentHelper.getItemEnchantmentLevel((Holder<Enchantment>) Enchantments.FORTUNE, tool);
            int count = min + random.nextInt((max - min) + 1);

            // Apply fortune-like bonus
            count += random.nextInt(fortune + 1);
            return List.of(new ItemStack(fossilDrop, count));
        }

        // Otherwise, no drops
        return List.of();
    }
}
