package net.seentro.prehistoriccraft.core.json.tissueExtractionChamber;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record TissueEntry(String name, String tissueType) {
    public static final Codec<TissueEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("display_name").forGetter(TissueEntry::name),
            Codec.STRING.fieldOf("tissue_type").forGetter(TissueEntry::tissueType)
    ).apply(instance, TissueEntry::new));
}
