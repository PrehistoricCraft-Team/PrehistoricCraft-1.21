package net.seentro.prehistoriccraft.entity.dinosaur.water.dayongaspis;


import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DayongaspisRenderer extends GeoEntityRenderer<DayongaspisEntity> {
    public DayongaspisRenderer(EntityRendererProvider.Context context) {
        super(context, new DayongaspisModel());
    }
}
