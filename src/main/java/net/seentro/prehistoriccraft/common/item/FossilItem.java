package net.seentro.prehistoriccraft.common.item;

import com.google.common.base.Strings;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.seentro.prehistoriccraft.registry.PrehistoricDataComponents;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

        Component speciesIndicator = Component.literal("Fossil Species: ").setStyle(Style.EMPTY.withColor(0xD3D3D3));
        Component species = Component.literal(capitalizeNames(stack.getOrDefault(PrehistoricDataComponents.FOSSIL_SPECIES, "Unknown"))).setStyle(Style.EMPTY.withColor(0x6C6C6C));

        if (Objects.equals(stack.get(PrehistoricDataComponents.FOSSIL_SPECIES), "Unknown")) {
            Component errorMessage = Component.literal("Something went wrong, please report this to the developers!").setStyle(Style.EMPTY.withColor(0xFF0000));
            tooltipComponents.add(errorMessage);
        }

        tooltipComponents.add(Component.empty().append(qualityIndicator).append(quality));
        tooltipComponents.add(Component.empty().append(speciesIndicator).append(species));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
