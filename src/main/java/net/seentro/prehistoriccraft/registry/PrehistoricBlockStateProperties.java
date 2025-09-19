package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.seentro.prehistoriccraft.core.InvisibleProperty;

public class PrehistoricBlockStateProperties {
    public static final EnumProperty<InvisibleProperty> INVISIBLE = EnumProperty.create("invisible", InvisibleProperty.class);
}
