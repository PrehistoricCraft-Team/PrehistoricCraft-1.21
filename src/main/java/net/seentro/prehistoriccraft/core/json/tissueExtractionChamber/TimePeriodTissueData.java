package net.seentro.prehistoriccraft.core.json.tissueExtractionChamber;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record TimePeriodTissueData(String timePeriod, List<TissueEntry> tissues) {
    public static final Codec<TimePeriodTissueData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("time_period").forGetter(TimePeriodTissueData::timePeriod),
            TissueEntry.CODEC.listOf().fieldOf("tissues").forGetter(TimePeriodTissueData::tissues)
    ).apply(instance, TimePeriodTissueData::new));
}
