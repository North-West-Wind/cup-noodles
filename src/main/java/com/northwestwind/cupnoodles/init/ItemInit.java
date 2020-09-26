package com.northwestwind.cupnoodles.init;

import com.northwestwind.cupnoodles.CupNoodlesMod;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = CupNoodlesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(CupNoodlesMod.MOD_ID)
public class ItemInit {
    public static final Item boiled_water_bucket = new Item(new Item.Properties().maxStackSize(1).group(ItemGroup.MISC).food(new Food.Builder().hunger(0).saturation(0).build())).setRegistryName("boiled_water_bucket");
    public static final Item chopstick = new Item(new Item.Properties().maxStackSize(1).group(ItemGroup.MISC)).setRegistryName("chopstick");
    public static final Item noodles = new Item(new Item.Properties().maxStackSize(64).group(ItemGroup.FOOD)).setRegistryName("noodles");

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(boiled_water_bucket);
        event.getRegistry().register(chopstick);
        event.getRegistry().register(noodles);
    }
}
