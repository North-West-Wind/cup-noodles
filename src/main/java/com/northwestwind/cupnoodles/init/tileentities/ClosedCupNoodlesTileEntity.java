package com.northwestwind.cupnoodles.init.tileentities;

import com.northwestwind.cupnoodles.init.BlockInit;
import com.northwestwind.cupnoodles.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class ClosedCupNoodlesTileEntity extends TileEntity implements ITickableTileEntity {
    private boolean hasWater;
    private int waterTick;

    public ClosedCupNoodlesTileEntity() {
        super(TileEntityInit.closed_cup_noodles);
    }

    public boolean hasWater() {
        return hasWater;
    }

    public void setHasWater(boolean hasWater) {
        this.hasWater = hasWater;
    }

    public int getWaterTick() {
        return waterTick;
    }

    @Override
    public void tick() {
        if(hasWater && waterTick < 600) waterTick++;
        else if(hasWater && getWorld() != null) {
            BlockState state = BlockInit.cup_noodles.getDefaultState();
            getWorld().setBlockState(getPos(), state);
            getWorld().notifyBlockUpdate(pos, state, state, 3);
        }
        if(hasWater) markDirty();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putBoolean("hasWater", hasWater);
        compound.putInt("waterTick", waterTick);
        return compound;
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
        waterTick = tag.getInt("waterTick");
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
        waterTick = tag.getInt("waterTick");
        super.handleUpdateTag(state, tag);
    }
}
