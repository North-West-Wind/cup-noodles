package com.northwestwind.cupnoodles.init.blocks;

import com.northwestwind.cupnoodles.init.ItemInit;
import com.northwestwind.cupnoodles.init.tileentities.ClosedCupNoodlesTileEntity;
import com.northwestwind.cupnoodles.init.tileentities.WaterBoilerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;

import java.util.Random;

public class ClosedCupNoodlesBlock extends Block {
    public ClosedCupNoodlesBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return CupNoodlesBlock.SHAPES[0];
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        ItemStack itemStack = player.getHeldItem(handIn);
        if(!(tileentity instanceof ClosedCupNoodlesTileEntity)) return ActionResultType.PASS;
        if(!((ClosedCupNoodlesTileEntity) tileentity).hasWater()) {
            if(itemStack.getItem().equals(ItemInit.boiled_water_bucket)) {
                ((ClosedCupNoodlesTileEntity) tileentity).setHasWater(true);
                if(!player.isCreative() && !player.isSpectator()) player.inventory.setInventorySlotContents(player.inventory.getSlotFor(itemStack), new ItemStack(Items.BUCKET));
                player.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1, 1);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if(!(tileentity instanceof ClosedCupNoodlesTileEntity)) return;
        ClosedCupNoodlesTileEntity tileEntity = (ClosedCupNoodlesTileEntity) tileentity;
        if(rand.nextDouble() < 0.7D && tileEntity.hasWater()) worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX() + 0.5, pos.up().getY(), pos.getZ() + 0.5, 0, 0.05, 0);
    }

    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ClosedCupNoodlesTileEntity();
    }
}
