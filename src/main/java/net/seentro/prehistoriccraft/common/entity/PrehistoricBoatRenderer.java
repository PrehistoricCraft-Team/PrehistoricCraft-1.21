package net.seentro.prehistoriccraft.common.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import jdk.dynalink.support.SimpleRelinkableCallSite;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricEnumExtensions;

import java.util.HashMap;
import java.util.Map;

import static net.seentro.prehistoriccraft.registry.PrehistoricEnumExtensions.*;

public class PrehistoricBoatRenderer extends BoatRenderer {
    private final Map<Boat.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources = new HashMap<>();

    public PrehistoricBoatRenderer(EntityRendererProvider.Context context, boolean chestBoat) {
        super(context, chestBoat);
        this.boatResources.put(DAWN_REDWOOD.getValue(), Pair.of(getTextureLocation(DAWN_REDWOOD.getValue(), chestBoat), createBoatModel(context, DAWN_REDWOOD.getValue(), chestBoat)));
    }

    private static ResourceLocation getTextureLocation(Boat.Type type, boolean chestBoat) {
        String typeString = type.getName().replaceAll("prehistoriccraft:", "");
        return chestBoat
                ? ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/entity/chest_boat/" + typeString + ".png")
                : ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/entity/boat/" + typeString + ".png");
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, Boat.Type type, boolean chestBoat) {
        ModelLayerLocation modellayerlocation = chestBoat ? ModelLayers.createChestBoatModelName(type) : ModelLayers.createBoatModelName(type);
        ModelPart modelpart = context.bakeLayer(modellayerlocation);
        return chestBoat ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
   }

    @Override
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        return this.boatResources.get(boat.getVariant());
    }
}
