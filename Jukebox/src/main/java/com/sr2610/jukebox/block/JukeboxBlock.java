package com.sr2610.jukebox.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class JukeboxBlock extends BlockHorizontal {


    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.125D, 0.0D, 0.125D, 0.875D, 0.9375D, 0.875D);


    public JukeboxBlock(final Properties properties) {
        super(properties);
        // Set the default values for our blockstate properties
      /*  setDefaultState(this.getDefaultState()
                .with(HORIZONTAL_FACING, Direction.NORTH)
        );
*/
    }

    @Nonnull
    @Override
    public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos, final ISelectionContext context) {
        return SHAPE;
    }


    @Override
    public boolean isTransparent(BlockState state) {
        return true;
    }

/*
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }


    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING)));
    }


    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
    }*/
}
