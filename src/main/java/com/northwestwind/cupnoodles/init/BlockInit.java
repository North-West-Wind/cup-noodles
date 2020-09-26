package com.northwestwind.cupnoodles.init;

import com.northwestwind.cupnoodles.CupNoodlesMod;
import com.northwestwind.cupnoodles.init.blocks.ClosedCupNoodlesBlock;
import com.northwestwind.cupnoodles.init.blocks.CupBlock;
import com.northwestwind.cupnoodles.init.blocks.CupNoodlesBlock;
import com.northwestwind.cupnoodles.init.blocks.WaterBoilerBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = CupNoodlesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(CupNoodlesMod.MOD_ID)
public class BlockInit {
    public static final CupNoodlesBlock cup_noodles = (CupNoodlesBlock) new CupNoodlesBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(3.0f).sound(SoundType.STONE).notSolid()).setRegistryName("cup_noodles");
    public static final CupBlock cup = (CupBlock) new CupBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(3.0f).sound(SoundType.STONE).notSolid()).setRegistryName("cup");
    public static final WaterBoilerBlock water_boiler = (WaterBoilerBlock) new WaterBoilerBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(5.0f, 6.0f).sound(SoundType.STONE).notSolid().harvestTool(ToolType.PICKAXE).harvestLevel(1)).setRegistryName("water_boiler");
    public static final ClosedCupNoodlesBlock closed_cup_noodles = (ClosedCupNoodlesBlock) new ClosedCupNoodlesBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(3.0f).sound(SoundType.STONE).notSolid()).setRegistryName("closed_cup_noodles");

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        event.getRegistry().register(cup_noodles);
        event.getRegistry().register(cup);
        event.getRegistry().register(water_boiler);
        event.getRegistry().register(closed_cup_noodles);
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(
                new BlockItem(closed_cup_noodles, new Item.Properties().maxStackSize(1).group(ItemGroup.FOOD))
                        .setRegistryName("closed_cup_noodles"));
        event.getRegistry().register(
                new BlockItem(cup_noodles, new Item.Properties().maxStackSize(1).group(ItemGroup.FOOD))
                        .setRegistryName("cup_noodles"));
        event.getRegistry().register(
                new BlockItem(cup, new Item.Properties().maxStackSize(1).group(ItemGroup.DECORATIONS))
                        .setRegistryName("cup"));
        event.getRegistry().register(
                new BlockItem(water_boiler, new Item.Properties().maxStackSize(1).group(ItemGroup.DECORATIONS))
                        .setRegistryName("water_boiler"));
    }
}
