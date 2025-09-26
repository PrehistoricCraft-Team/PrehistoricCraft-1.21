package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.fluid.BaseFluidType;

import java.util.function.Supplier;

public class PrehistoricFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, PrehistoricCraft.MODID);

    // BLICE
    public static final Supplier<FlowingFluid> SOURCE_BLICE_FLUID = FLUIDS.register("source_blice_fluid", () -> new BaseFlowingFluid.Source(PrehistoricFluids.BLICE_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_BLICE_FLUID = FLUIDS.register("flowing_blice_fluid", () -> new BaseFlowingFluid.Flowing(PrehistoricFluids.BLICE_FLUID_PROPERTIES));

    public static final BaseFlowingFluid.Properties BLICE_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
            PrehistoricFluidTypes.BLICE_FLUID_TYPE, SOURCE_BLICE_FLUID, FLOWING_BLICE_FLUID)
            .bucket(PrehistoricItems.BLICE_FLUID_BUCKET);

    // ACID
    public static final Supplier<FlowingFluid> SOURCE_ACID_FLUID = FLUIDS.register("source_acid_fluid", () -> new BaseFlowingFluid.Source(PrehistoricFluids.ACID_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_ACID_FLUID = FLUIDS.register("flowing_acid_fluid", () -> new BaseFlowingFluid.Flowing(PrehistoricFluids.ACID_FLUID_PROPERTIES));

    public static final BaseFlowingFluid.Properties ACID_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
            PrehistoricFluidTypes.ACID_FLUID_TYPE, SOURCE_ACID_FLUID, FLOWING_ACID_FLUID)
            .bucket(PrehistoricItems.ACID_FLUID_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
