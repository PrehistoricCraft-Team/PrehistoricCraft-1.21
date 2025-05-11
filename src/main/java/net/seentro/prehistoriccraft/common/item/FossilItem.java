package net.seentro.prehistoriccraft.common.item;

import com.google.common.base.Strings;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.seentro.prehistoriccraft.registry.PrehistoricDataComponents;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FossilItem extends Item {
    public FossilItem(Properties properties) {
        super(properties);
    }

    private String capitalizeNames(String quality) {
        quality = Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(quality)).map(StringUtils::capitalize).collect(Collectors.joining(" "));
        return quality;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Fossil Quality: " + capitalizeNames(stack.getOrDefault(PrehistoricDataComponents.FOSSIL_QUALITY, "Unknown"))));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
