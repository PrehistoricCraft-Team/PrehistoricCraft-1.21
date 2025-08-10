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

public class TissueItem extends Item {
    public TissueItem(Properties properties) {
        super(properties);
    }

    private String capitalizeNames(String quality) {
        quality = Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(quality)).map(StringUtils::capitalize).collect(Collectors.joining(" "));
        return quality;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Style color = switch (stack.getOrDefault(PrehistoricDataComponents.FOSSIL_SPECIES, "unknown")) {
            case "unknown" -> Style.EMPTY.withColor(0x6C6C6C);

            default -> Style.EMPTY.withColor(0xD3D3D3);
        };

        Component speciesIndicator = Component.literal("Species: ").setStyle(Style.EMPTY.withColor(0xD3D3D3));
        Component species = Component.literal(capitalizeNames(stack.getOrDefault(PrehistoricDataComponents.FOSSIL_SPECIES, "unknown"))).setStyle(color);

        tooltipComponents.add(Component.empty().append(speciesIndicator).append(species));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
