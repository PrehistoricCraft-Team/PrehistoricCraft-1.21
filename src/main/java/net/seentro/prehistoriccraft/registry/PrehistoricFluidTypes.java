package net.seentro.prehistoriccraft.registry;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.fluid.BaseFluidType;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class PrehistoricFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = ResourceLocation.parse("block/water_still");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, PrehistoricCraft.MODID);

    public static final Supplier<FluidType> BLICE_FLUID_TYPE = registerFluidType("blice_fluid_type",
            new BaseFluidType(WATER_STILL_RL, WATER_STILL_RL,
                    0xffccaa00, new Vector3f(108f / 255f, 168f / 255f, 212f / 255f), FluidType.Properties.create()));

    public static final Supplier<FluidType> ACID_FLUID_TYPE = registerFluidType("acid_fluid_type",
            new BaseFluidType(WATER_STILL_RL, WATER_STILL_RL,
                    0xffc6fc03, new Vector3f(108f / 255f, 168f / 255f, 212f / 255f), FluidType.Properties.create()));

    private static Supplier<FluidType> registerFluidType(String name, FluidType fluidType) {
        return FLUID_TYPES.register(name, () -> fluidType);
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
