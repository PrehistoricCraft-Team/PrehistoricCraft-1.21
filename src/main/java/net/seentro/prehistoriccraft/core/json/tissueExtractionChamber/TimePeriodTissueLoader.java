package net.seentro.prehistoriccraft.core.json.tissueExtractionChamber;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.seentro.prehistoriccraft.PrehistoricCraft;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TimePeriodTissueLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();
    public static final Map<String, List<TissueEntry>> TISSUE_POOLS = new HashMap<>();

    public TimePeriodTissueLoader() {
        super(GSON, "tissues");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        TISSUE_POOLS.clear();

        for (var entry : object.entrySet()) {
            TimePeriodTissueData data = TimePeriodTissueData.CODEC.parse(JsonOps.INSTANCE, entry.getValue()).getOrThrow();

            TISSUE_POOLS.put(data.timePeriod().toLowerCase(Locale.ROOT), data.tissues());
        }

        PrehistoricCraft.LOGGER.info("Loaded tissue data for {} time periods", TISSUE_POOLS.size());
    }
}
