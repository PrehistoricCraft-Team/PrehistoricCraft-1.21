package net.seentro.prehistoriccraft.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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
        Style color = switch (stack.getOrDefault(PrehistoricDataComponents.FOSSIL_QUALITY, "Unknown")) {
            case "damaged" -> Style.EMPTY.withColor(0xFF5555);
            case "incomplete" -> Style.EMPTY.withColor(0xFFA500);
            case "fragmentary" -> Style.EMPTY.withColor(0xFFFF55);
            case "decent" -> Style.EMPTY.withColor(0x55FF55);
            case "rich" -> Style.EMPTY.withColor(0x55FFFF);

            default -> Style.EMPTY.withColor(0xD3D3D3);
        };

        Component qualityIndicator = Component.literal("Fossil Quality: ").setStyle(Style.EMPTY.withColor(0xD3D3D3));
        Component quality = Component.literal(capitalizeNames(stack.getOrDefault(PrehistoricDataComponents.FOSSIL_QUALITY, "Unknown"))).setStyle(color);

        tooltipComponents.add(Component.empty().append(qualityIndicator).append(quality));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
