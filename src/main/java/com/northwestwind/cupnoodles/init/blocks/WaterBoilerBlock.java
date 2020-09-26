package com.northwestwind.cupnoodles.init.blocks;

import com.northwestwind.cupnoodles.init.ItemInit;
import com.northwestwind.cupnoodles.init.tileentities.WaterBoilerTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;

import java.util.Random;
import java.util.stream.Stream;

public class WaterBoilerBlock extends Block {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 8, 16),
            Block.makeCuboidShape(2, 8, 2, 14, 11, 14),
            Block.makeCuboidShape(4, 11, 4, 12, 12, 12),
            Block.makeCuboidShape(7, 12, 7, 9, 13, 9),
            Block.makeCuboidShape(7, 8, 12, 9, 10, 17),
            Block.makeCuboidShape(7, 8, 1, 9, 14, 3),
            Block.makeCuboidShape(7, 14, 2, 9, 15, 4),
            Block.makeCuboidShape(7, 14, 12, 9, 15, 14),
            Block.makeCuboidShape(7, 15, 3, 9, 16, 13),
            Block.makeCuboidShape(7, 7, 13, 9, 14, 15)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 8, 16),
            Block.makeCuboidShape(2, 8, 2, 14, 11, 14),
            Block.makeCuboidShape(4, 11, 4, 12, 12, 12),
            Block.makeCuboidShape(7, 12, 7, 9, 13, 9),
            Block.makeCuboidShape(11, 8, 7, 16, 10, 9),
            Block.makeCuboidShape(1, 8, 7, 3, 14, 9),
            Block.makeCuboidShape(2, 14, 7, 4, 15, 9),
            Block.makeCuboidShape(12, 14, 7, 14, 15, 9),
            Block.makeCuboidShape(3, 15, 7, 13, 16, 9),
            Block.makeCuboidShape(13, 7, 7, 15, 14, 9)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 8, 16),
            Block.makeCuboidShape(2, 8, 2, 14, 11, 14),
            Block.makeCuboidShape(4, 11, 4, 12, 12, 12),
            Block.makeCuboidShape(7, 12, 7, 9, 13, 9),
            Block.makeCuboidShape(7, 8, 0, 9, 10, 5),
            Block.makeCuboidShape(7, 8, 1, 9, 14, 3),
            Block.makeCuboidShape(7, 14, 2, 9, 15, 4),
            Block.makeCuboidShape(7, 14, 12, 9, 15, 14),
            Block.makeCuboidShape(7, 15, 3, 9, 16, 13),
            Block.makeCuboidShape(7, 7, 13, 9, 14, 15)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 8, 16),
            Block.makeCuboidShape(2, 8, 2, 14, 11, 14),
            Block.makeCuboidShape(4, 11, 4, 12, 12, 12),
            Block.makeCuboidShape(7, 12, 7, 9, 13, 9),
            Block.makeCuboidShape(0, 8, 7, 5, 10, 9),
            Block.makeCuboidShape(1, 8, 7, 3, 14, 9),
            Block.makeCuboidShape(2, 14, 7, 4, 15, 9),
            Block.makeCuboidShape(12, 14, 7, 14, 15, 9),
            Block.makeCuboidShape(3, 15, 7, 13, 16, 9),
            Block.makeCuboidShape(13, 7, 7, 15, 14, 9)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public WaterBoilerBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(FACING)) {
            case EAST:
                return SHAPE_E;
            case WEST:
                return SHAPE_W;
            case SOUTH:
                return SHAPE_S;
            default:
                return SHAPE_N;
        }
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        ItemStack itemStack = player.getHeldItem(handIn);
        if(!(tileentity instanceof WaterBoilerTileEntity)) return ActionResultType.PASS;
        if(!((WaterBoilerTileEntity) tileentity).hasWater()) {
            if(itemStack.getItem().equals(Items.WATER_BUCKET)) {
                ((WaterBoilerTileEntity) tileentity).setHasWater(true);
                player.inventory.setInventorySlotContents(player.inventory.getSlotFor(itemStack), new ItemStack(Items.BUCKET));
                player.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1, 1);
                return ActionResultType.SUCCESS;
            }
        } else {
            if(itemStack.getItem().equals(Items.BUCKET)) {
                ((WaterBoilerTileEntity) tileentity).setHasWater(false);
                if(!((WaterBoilerTileEntity) tileentity).isBoiled() && !player.isCreative() && !player.isSpectator()) player.inventory.setInventorySlotContents(player.inventory.getSlotFor(itemStack), new ItemStack(Items.WATER_BUCKET));
                else player.inventory.setInventorySlotContents(player.inventory.getSlotFor(itemStack), new ItemStack(ItemInit.boiled_water_bucket));
                player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1, 1);
                ((WaterBoilerTileEntity) tileentity).setBoiled(false);
                ((WaterBoilerTileEntity) tileentity).setBoilTime(0);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if(!(tileentity instanceof WaterBoilerTileEntity)) return;
        if(((WaterBoilerTileEntity) tileentity).isBoiled()) {
            if(rand.nextDouble() < 0.7D) worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX() + 0.5, pos.up().getY(), pos.getZ() + 0.5, 0, 0.05, 0);
            if (rand.nextDouble() < 0.2D) worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }
    }

    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new WaterBoilerTileEntity();
    }

    @Override
    public boolean isValidPosition(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        return hasEnoughSolidSide(p_196260_2_, p_196260_3_.down(), Direction.UP);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
