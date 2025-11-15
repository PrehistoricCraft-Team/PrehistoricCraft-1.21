package net.seentro.prehistoriccraft.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.utils.FossilSpeciesData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FossilSpeciesLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    public static final FossilSpeciesLoader INSTANCE = new FossilSpeciesLoader();

    private final Map<ResourceLocation, FossilSpeciesData> entries = new HashMap<>();

    private FossilSpeciesLoader() {
        super(GSON, "animals");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map,
            ResourceManager resourceManager,
            ProfilerFiller profiler) {

        entries.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonElement json = entry.getValue();

            try {
                FossilSpeciesData data = GSON.fromJson(json, FossilSpeciesData.class);
                entries.put(id, data);
            } catch (Exception e) {
                PrehistoricCraft.LOGGER.error("Error loading fossil species {}: {}", id, e.getMessage());
            }
        }

        PrehistoricCraft.LOGGER.info("Loaded {} fossil species (JSON)", entries.size());
    }

    public FossilSpeciesData get(ResourceLocation id) {
        return entries.get(id);
    }

    public Map<ResourceLocation, FossilSpeciesData> getAll() {
        return Collections.unmodifiableMap(entries);
    }
}
