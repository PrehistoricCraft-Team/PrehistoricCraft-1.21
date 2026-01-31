package net.seentro.prehistoriccraft.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.data.FossilSpeciesLoader;
import net.seentro.prehistoriccraft.registry.PrehistoricDataComponents;
import net.seentro.prehistoriccraft.utils.FossilSpeciesData;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DNAInPetriDishItem extends Item {

    public DNAInPetriDishItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 16;
    }

    private String formatSpeciesName(String raw) {
        if (raw == null || raw.isEmpty()) {
            return "Unknown";
        }

        String id = raw;
        int colon = id.indexOf(':');
        if (colon >= 0) {
            id = id.substring(colon + 1);
        }

        id = id.replace('_', ' ');
        return Arrays.stream(id.split(" "))
                .filter(s -> !s.isEmpty())
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(" "));
    }

    private boolean isAnimalSpecies(String speciesId) {
        if (speciesId == null || speciesId.isEmpty()) {
            return false;
        }

        ResourceLocation id;
        if (speciesId.contains(":")) {
            id = ResourceLocation.tryParse(speciesId);
        } else {
            id = ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, speciesId);
        }

        if (id == null) {
            PrehistoricCraft.LOGGER.error("Invalid ResourceLocation for FOSSIL_SPECIES: {}", speciesId);
            return false;
        }

        FossilSpeciesData data = FossilSpeciesLoader.INSTANCE.get(id);

        if (data == null) {
            return false;
        }

        return data.isAnimal;
    }


    private int getSpeciesColor(String speciesId) {
        if (isAnimalSpecies(speciesId)) {
            return 0x00FF00;
        } else {
            return 0xFFA500;
        }
    }

    private int getQualityColor(int quality) {
        if (quality < 0) quality = 0;
        if (quality > 100) quality = 100;

        float t = quality / 100.0f;

        int r;
        int g;
        int b = 0;

        if (t <= 0.5f) {
            r = 255;
            g = (int) (t * 2 * 255);
        } else {
            g = 255;
            r = (int) ((1.0f - (t - 0.5f) * 2) * 255);
        }

        if (r < 0) r = 0;
        if (g < 0) g = 0;
        if (r > 255) r = 255;
        if (g > 255) g = 255;

        return (r << 16) | (g << 8) | b;
    }

    @Override
    public void appendHoverText(ItemStack stack,
                                TooltipContext context,
                                List<Component> tooltipComponents,
                                TooltipFlag tooltipFlag) {

        String speciesId = stack.getOrDefault(
                PrehistoricDataComponents.FOSSIL_SPECIES.get(),
                "unknown"
        );
        String speciesName = formatSpeciesName(speciesId);

        Component speciesIndicator = Component.literal("Species: ")
                .setStyle(Style.EMPTY.withColor(0xD3D3D3));
        Component speciesValue = Component.literal(speciesName)
                .setStyle(Style.EMPTY.withColor(getSpeciesColor(speciesId)));

        tooltipComponents.add(speciesIndicator.copy().append(speciesValue));

        int qualityValue = stack.getOrDefault(
                PrehistoricDataComponents.DNA_QUALITY.get(),
                0
        );

        Component qualityIndicator = Component.literal("DNA Quality: ")
                .setStyle(Style.EMPTY.withColor(0xD3D3D3));
        Component qualityText = Component.literal(qualityValue + "%")
                .setStyle(Style.EMPTY.withColor(getQualityColor(qualityValue)));

        tooltipComponents.add(qualityIndicator.copy().append(qualityText));

        boolean contaminated = stack.getOrDefault(
                PrehistoricDataComponents.DNA_CONTAMINATED.get(),
                false
        );

        if (contaminated) {
            Component contamination = Component.literal("! DNA SAMPLE CONTAMINATED !")
                    .setStyle(Style.EMPTY.withColor(0xFF0000).withBold(true));
            tooltipComponents.add(contamination);
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
