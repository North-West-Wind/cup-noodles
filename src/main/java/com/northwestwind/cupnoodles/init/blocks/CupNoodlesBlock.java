package com.northwestwind.cupnoodles.init.blocks;

import com.northwestwind.cupnoodles.init.BlockInit;
import com.northwestwind.cupnoodles.init.ItemInit;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class CupNoodlesBlock extends Block {
    public static final IntegerProperty BITES = IntegerProperty.create("sips", 0, 3);

    public static final VoxelShape[] SHAPES = new VoxelShape[]{
            Stream.of(Block.makeCuboidShape(6, 0, 6, 10, 1, 10), Block.makeCuboidShape(6, 0, 5, 10, 2, 6), Block.makeCuboidShape(6, 0, 10, 10, 2, 11), Block.makeCuboidShape(10, 0, 6, 11, 2, 10), Block.makeCuboidShape(5, 0, 6, 6, 2, 10), Block.makeCuboidShape(6, 2, 4, 10, 6, 5), Block.makeCuboidShape(6, 2, 11, 10, 6, 12), Block.makeCuboidShape(11, 2, 6, 12, 6, 10), Block.makeCuboidShape(4, 2, 6, 5, 6, 10), Block.makeCuboidShape(5, 2, 5, 6, 6, 6), Block.makeCuboidShape(5, 2, 10, 6, 6, 11), Block.makeCuboidShape(10, 2, 10, 11, 6, 11), Block.makeCuboidShape(10, 2, 5, 11, 6, 6), Block.makeCuboidShape(6, 6, 3, 10, 13, 4), Block.makeCuboidShape(6, 6, 12, 10, 13, 13), Block.makeCuboidShape(3, 6, 6, 4, 13, 10), Block.makeCuboidShape(12, 6, 6, 13, 13, 10), Block.makeCuboidShape(4, 6, 5, 5, 13, 6), Block.makeCuboidShape(5, 6, 4, 6, 13, 5), Block.makeCuboidShape(5, 6, 11, 6, 13, 12), Block.makeCuboidShape(4, 6, 10, 5, 13, 11), Block.makeCuboidShape(11, 6, 10, 12, 13, 11), Block.makeCuboidShape(10, 6, 11, 11, 13, 12), Block.makeCuboidShape(10, 6, 4, 11, 13, 5), Block.makeCuboidShape(11, 6, 5, 12, 13, 6), Block.makeCuboidShape(6, 9, 4, 10, 10, 12), Block.makeCuboidShape(5, 9, 5, 6, 10, 11), Block.makeCuboidShape(10, 9, 5, 11, 10, 11), Block.makeCuboidShape(11, 9, 6, 12, 10, 10), Block.makeCuboidShape(4, 9, 6, 5, 10, 10), Block.makeCuboidShape(4, 12, 6, 12, 13, 10), Block.makeCuboidShape(5, 12, 5, 11, 13, 6), Block.makeCuboidShape(5, 12, 10, 11, 13, 11), Block.makeCuboidShape(6, 12, 11, 10, 13, 12), Block.makeCuboidShape(6, 12, 4, 10, 13, 5)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get(),
            Stream.of(Block.makeCuboidShape(6, 0, 6, 10, 1, 10), Block.makeCuboidShape(6, 0, 5, 10, 2, 6), Block.makeCuboidShape(6, 0, 10, 10, 2, 11), Block.makeCuboidShape(10, 0, 6, 11, 2, 10), Block.makeCuboidShape(5, 0, 6, 6, 2, 10), Block.makeCuboidShape(6, 2, 4, 10, 6, 5), Block.makeCuboidShape(6, 2, 11, 10, 6, 12), Block.makeCuboidShape(11, 2, 6, 12, 6, 10), Block.makeCuboidShape(4, 2, 6, 5, 6, 10), Block.makeCuboidShape(5, 2, 5, 6, 6, 6), Block.makeCuboidShape(5, 2, 10, 6, 6, 11), Block.makeCuboidShape(10, 2, 10, 11, 6, 11), Block.makeCuboidShape(10, 2, 5, 11, 6, 6), Block.makeCuboidShape(6, 6, 3, 10, 13, 4), Block.makeCuboidShape(6, 6, 12, 10, 13, 13), Block.makeCuboidShape(3, 6, 6, 4, 13, 10), Block.makeCuboidShape(12, 6, 6, 13, 13, 10), Block.makeCuboidShape(4, 6, 5, 5, 13, 6), Block.makeCuboidShape(5, 6, 4, 6, 13, 5), Block.makeCuboidShape(5, 6, 11, 6, 13, 12), Block.makeCuboidShape(4, 6, 10, 5, 13, 11), Block.makeCuboidShape(11, 6, 10, 12, 13, 11), Block.makeCuboidShape(10, 6, 11, 11, 13, 12), Block.makeCuboidShape(10, 6, 4, 11, 13, 5), Block.makeCuboidShape(11, 6, 5, 12, 13, 6), Block.makeCuboidShape(6, 9, 4, 10, 10, 12), Block.makeCuboidShape(5, 9, 5, 6, 10, 11), Block.makeCuboidShape(10, 9, 5, 11, 10, 11), Block.makeCuboidShape(11, 9, 6, 12, 10, 10), Block.makeCuboidShape(4, 9, 6, 5, 10, 10)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get(),
            Stream.of(Block.makeCuboidShape(6, 0, 6, 10, 1, 10), Block.makeCuboidShape(6, 0, 5, 10, 2, 6), Block.makeCuboidShape(6, 0, 10, 10, 2, 11), Block.makeCuboidShape(10, 0, 6, 11, 2, 10), Block.makeCuboidShape(5, 0, 6, 6, 2, 10), Block.makeCuboidShape(6, 2, 4, 10, 6, 5), Block.makeCuboidShape(6, 2, 11, 10, 6, 12), Block.makeCuboidShape(11, 2, 6, 12, 6, 10), Block.makeCuboidShape(4, 2, 6, 5, 6, 10), Block.makeCuboidShape(5, 2, 5, 6, 6, 6), Block.makeCuboidShape(5, 2, 10, 6, 6, 11), Block.makeCuboidShape(10, 2, 10, 11, 6, 11), Block.makeCuboidShape(10, 2, 5, 11, 6, 6), Block.makeCuboidShape(6, 6, 3, 10, 13, 4), Block.makeCuboidShape(6, 6, 12, 10, 13, 13), Block.makeCuboidShape(3, 6, 6, 4, 13, 10), Block.makeCuboidShape(12, 6, 6, 13, 13, 10), Block.makeCuboidShape(4, 6, 5, 5, 13, 6), Block.makeCuboidShape(5, 6, 4, 6, 13, 5), Block.makeCuboidShape(5, 6, 11, 6, 13, 12), Block.makeCuboidShape(4, 6, 10, 5, 13, 11), Block.makeCuboidShape(11, 6, 10, 12, 13, 11), Block.makeCuboidShape(10, 6, 11, 11, 13, 12), Block.makeCuboidShape(10, 6, 4, 11, 13, 5), Block.makeCuboidShape(11, 6, 5, 12, 13, 6), Block.makeCuboidShape(6, 6, 4, 10, 7, 12), Block.makeCuboidShape(5, 6, 5, 6, 7, 11), Block.makeCuboidShape(10, 6, 5, 11, 7, 11), Block.makeCuboidShape(11, 6, 6, 12, 7, 10), Block.makeCuboidShape(4, 6, 6, 5, 7, 10)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get(),
            Stream.of(Block.makeCuboidShape(6, 3, 5, 10, 4, 11), Block.makeCuboidShape(10, 3, 6, 11, 4, 10), Block.makeCuboidShape(5, 3, 6, 6, 4, 10), Block.makeCuboidShape(6, 0, 6, 10, 1, 10), Block.makeCuboidShape(6, 0, 5, 10, 2, 6), Block.makeCuboidShape(6, 0, 10, 10, 2, 11), Block.makeCuboidShape(10, 0, 6, 11, 2, 10), Block.makeCuboidShape(5, 0, 6, 6, 2, 10), Block.makeCuboidShape(6, 2, 4, 10, 6, 5), Block.makeCuboidShape(6, 2, 11, 10, 6, 12), Block.makeCuboidShape(11, 2, 6, 12, 6, 10), Block.makeCuboidShape(4, 2, 6, 5, 6, 10), Block.makeCuboidShape(5, 2, 5, 6, 6, 6), Block.makeCuboidShape(5, 2, 10, 6, 6, 11), Block.makeCuboidShape(10, 2, 10, 11, 6, 11), Block.makeCuboidShape(10, 2, 5, 11, 6, 6), Block.makeCuboidShape(6, 6, 3, 10, 13, 4), Block.makeCuboidShape(6, 6, 12, 10, 13, 13), Block.makeCuboidShape(3, 6, 6, 4, 13, 10), Block.makeCuboidShape(12, 6, 6, 13, 13, 10), Block.makeCuboidShape(4, 6, 5, 5, 13, 6), Block.makeCuboidShape(5, 6, 4, 6, 13, 5), Block.makeCuboidShape(5, 6, 11, 6, 13, 12), Block.makeCuboidShape(4, 6, 10, 5, 13, 11), Block.makeCuboidShape(11, 6, 10, 12, 13, 11), Block.makeCuboidShape(10, 6, 11, 11, 13, 12), Block.makeCuboidShape(10, 6, 4, 11, 13, 5), Block.makeCuboidShape(11, 6, 5, 12, 13, 6)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get()
    };

    public CupNoodlesBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BITES, 0));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(BITES)];
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            ItemStack itemstack = player.getHeldItem(handIn);
            if (this.func_226911_a_(worldIn, pos, state, player, handIn).isSuccessOrConsume()) {
                return ActionResultType.SUCCESS;
            }

            if (itemstack.isEmpty()) {
                return ActionResultType.CONSUME;
            }
        }

        return this.func_226911_a_(worldIn, pos, state, player, handIn);
    }

    private ActionResultType func_226911_a_(IWorld world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand) {
        if (!player.canEat(false)) {
            return ActionResultType.PASS;
        } else {
            int i = state.get(BITES);
            if(i != 0) player.getFoodStats().addStats(4, 1.2F);
            if (i < 3) {
                if(i != 0 && !player.getHeldItem(hand).getItem().equals(ItemInit.chopstick) && !player.isCreative()) return ActionResultType.PASS;
                world.setBlockState(pos, state.with(BITES, i + 1), 3);
            } else {
                world.setBlockState(pos, BlockInit.cup.getDefaultState(), 3);
            }

            return ActionResultType.SUCCESS;
        }
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return (7 - blockState.get(BITES)) * 2;
    }

    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}
