package com.northwestwind.cupnoodles.init.tileentities;

import com.northwestwind.cupnoodles.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;

public class WaterBoilerTileEntity extends TileEntity implements ITickableTileEntity {
    private boolean hasWater, isBoiled, underFire;
    private int boilTime;

    public WaterBoilerTileEntity() {
        super(TileEntityInit.water_boiler);
    }

    public boolean isBoiling() {
        return hasWater && underFire;
    }

    public void setHasWater(boolean hasWater) {
        this.hasWater = hasWater;
    }

    public void setBoiled(boolean boiled) {
        isBoiled = boiled;
    }

    public void setUnderFire(boolean underFire) {
        this.underFire = underFire;
    }

    public void setBoilTime(int boilTime) {
        this.boilTime = boilTime;
    }

    public boolean hasWater() {
        return hasWater;
    }

    public boolean isBoiled() {
        return isBoiled;
    }

    public boolean isUnderFire() {
        return underFire;
    }

    public int getBoilTime() {
        return boilTime;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putBoolean("hasWater", hasWater);
        compound.putBoolean("isBoiled", isBoiled);
        compound.putBoolean("underFire", underFire);
        compound.putInt("boilTime", boilTime);
        return compound;
    }

    public void tick() {
        if(hasWorld()) {
            World world = getWorld();
            underFire = world.getBlockState(getPos().down()).getBlock().equals(Blocks.FIRE);
        }
        if(boilTime >= 1800 && hasWater) {
            isBoiled = true;
            boilTime = 0;
        } else if(isBoiled) {
            boilTime = 0;
        } else if(hasWater && underFire) {
            boilTime++;
        } else if(hasWater && boilTime > 0) {
            boilTime--;
        }
        if(!isBoiled && hasWater) markDirty();
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket(){
        CompoundNBT nbtTag = new CompoundNBT();
        write(nbtTag);
        return new SUpdateTileEntityPacket(getPos(), -1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt){
        CompoundNBT tag = pkt.getNbtCompound();
        hasWater = tag.getBoolean("hasWater");
        isBoiled = tag.getBoolean("isBoiled");
        underFire = tag.getBoolean("underFire");
        boilTime = tag.getInt("boilTime");
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbtTag = new CompoundNBT();
        write(nbtTag);
        return nbtTag;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        hasWater = tag.getBoolean("hasWater");
        isBoiled = tag.getBoolean("isBoiled");
        underFire = tag.getBoolean("underFire");
        boilTime = tag.getInt("boilTime");
        super.handleUpdateTag(state, tag);
    }
}
