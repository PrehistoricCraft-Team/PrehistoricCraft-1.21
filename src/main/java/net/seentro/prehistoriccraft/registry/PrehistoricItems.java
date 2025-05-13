package net.seentro.prehistoriccraft.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.item.FossilItem;


public class PrehistoricItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(PrehistoricCraft.MODID);

    /* FOSSILS */
    public static final DeferredItem<Item> CAMBRIAN_FOSSIL = ITEMS.register("cambrian_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> CARBONIFEROUS_FOSSIL = ITEMS.register("carboniferous_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> CRETACEOUS_FOSSIL = ITEMS.register("cretaceous_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> DEVONIAN_FOSSIL = ITEMS.register("devonian_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> JURASSIC_FOSSIL = ITEMS.register("jurassic_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> NEOGENE_FOSSIL = ITEMS.register("neogene_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> ORDOVICIAN_FOSSIL = ITEMS.register("ordovician_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> PALEOGENE_FOSSIL = ITEMS.register("paleogene_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> PERMIAN_FOSSIL = ITEMS.register("permian_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> PRECAMBRIAN_FOSSIL = ITEMS.register("precambrian_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> SILURIAN_FOSSIL = ITEMS.register("silurian_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> TRIASSIC_FOSSIL   = ITEMS.register("triassic_fossil", () -> new FossilItem(new Item.Properties()));

    public static final DeferredItem<Item> EXCAVATOR_PICKAXE   = ITEMS.register("excavator_pickaxe", () -> new PickaxeItem(Tiers.IRON, new Item.Properties()));

    /* GYPSUM & PLASTER */
    public static final DeferredItem<Item> GYPSUM_POWDER = ITEMS.register("gypsum_powder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PLASTER_POWDER = ITEMS.register("plaster_powder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PLASTER_WRAP = ITEMS.register("plaster_wrap", () -> new Item(new Item.Properties()));
    /* AMBER */
    public static final DeferredItem<Item> AMBER = ITEMS.register("amber", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
