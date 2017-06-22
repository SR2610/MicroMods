package com.sr2610.jukebox;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockJukebox extends Block implements ITileEntityProvider {
	public BlockJukebox(String unlocalizedName) {
		super(Material.ROCK);
		setUnlocalizedName(JukeboxMod.MODID + ":" + unlocalizedName);
		setCreativeTab(CreativeTabs.DECORATIONS);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityJukebox();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
		TileEntityJukebox te = (TileEntityJukebox) world.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(world, pos, te);
		super.breakBlock(world, pos, blockstate);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (stack.hasDisplayName()) {
			((TileEntityJukebox) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
		}
	}
}