package com.northwestwind.cupnoodles.init.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class CupBlock extends Block implements IWaterLoggable {
    private static final VoxelShape SHAPE = Stream.of(Block.makeCuboidShape(6, 0, 6, 10, 1, 10), Block.makeCuboidShape(6, 0, 5, 10, 2, 6), Block.makeCuboidShape(6, 0, 10, 10, 2, 11), Block.makeCuboidShape(10, 0, 6, 11, 2, 10), Block.makeCuboidShape(5, 0, 6, 6, 2, 10), Block.makeCuboidShape(6, 2, 4, 10, 6, 5), Block.makeCuboidShape(6, 2, 11, 10, 6, 12), Block.makeCuboidShape(11, 2, 6, 12, 6, 10), Block.makeCuboidShape(4, 2, 6, 5, 6, 10), Block.makeCuboidShape(5, 2, 5, 6, 6, 6), Block.makeCuboidShape(5, 2, 10, 6, 6, 11), Block.makeCuboidShape(10, 2, 10, 11, 6, 11), Block.makeCuboidShape(10, 2, 5, 11, 6, 6), Block.makeCuboidShape(6, 6, 3, 10, 13, 4), Block.makeCuboidShape(6, 6, 12, 10, 13, 13), Block.makeCuboidShape(3, 6, 6, 4, 13, 10), Block.makeCuboidShape(12, 6, 6, 13, 13, 10), Block.makeCuboidShape(4, 6, 5, 5, 13, 6), Block.makeCuboidShape(5, 6, 4, 6, 13, 5), Block.makeCuboidShape(5, 6, 11, 6, 13, 12), Block.makeCuboidShape(4, 6, 10, 5, 13, 11), Block.makeCuboidShape(11, 6, 10, 12, 13, 11), Block.makeCuboidShape(10, 6, 11, 11, 13, 12), Block.makeCuboidShape(10, 6, 4, 11, 13, 5), Block.makeCuboidShape(11, 6, 5, 12, 13, 6)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public CupBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
}
