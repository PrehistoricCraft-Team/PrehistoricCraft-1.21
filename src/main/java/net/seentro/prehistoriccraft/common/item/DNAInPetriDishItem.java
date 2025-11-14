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

public class DNAInPetriDishItem extends Item {
    public DNAInPetriDishItem(Properties properties) {
        super(properties);
    }

    private String capitalizeNames(String quality) {
        quality = Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(quality)).map(StringUtils::capitalize).collect(Collectors.joining(" "));
        return quality;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        
        Component speciesIndicator = Component.literal("Fossil Species: ").setStyle(Style.EMPTY.withColor(0xD3D3D3));
        Component species = Component.literal(capitalizeNames(stack.getOrDefault(PrehistoricDataComponents.FOSSIL_SPECIES, "Unknown") + "\n"));
        Component qualityIndicator = Component.literal("Quality: ").setStyle(Style.EMPTY.withColor(0xD3D3D3));
        Component quality = Component.literal(capitalizeNames((stack.getOrDefault(PrehistoricDataComponents.DNA_QUALITY, "Unknown")).toString()) + "\n");
        Component isContaminatedIndicator = Component.literal("Is contaminated: ").setStyle(Style.EMPTY.withColor(0xD3D3D3));
        Component isContaminated = Component.literal(capitalizeNames((stack.getOrDefault(PrehistoricDataComponents.DNA_CONTAMINATED, "Unknown").toString())));


        tooltipComponents.add(Component.empty().append(speciesIndicator).append(species)
        .append(qualityIndicator).append(quality)
        .append(isContaminatedIndicator).append(isContaminated));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
