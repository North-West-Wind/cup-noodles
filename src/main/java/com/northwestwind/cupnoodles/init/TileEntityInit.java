package com.northwestwind.cupnoodles.init;

import com.northwestwind.cupnoodles.CupNoodlesMod;
import com.northwestwind.cupnoodles.init.tileentities.ClosedCupNoodlesTileEntity;
import com.northwestwind.cupnoodles.init.tileentities.WaterBoilerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = CupNoodlesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(CupNoodlesMod.MOD_ID)
public class TileEntityInit {
    public static final TileEntityType<?> water_boiler = TileEntityType.Builder.create(WaterBoilerTileEntity::new, BlockInit.water_boiler).build(null);
    public static final TileEntityType<?> closed_cup_noodles = TileEntityType.Builder.create(ClosedCupNoodlesTileEntity::new, BlockInit.closed_cup_noodles).build(null);

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(water_boiler.setRegistryName("water_boiler"));
        event.getRegistry().register(closed_cup_noodles.setRegistryName("closed_cup_noodles"));
    }
}
