package com.sr2610.jukebox.blocks;

import com.sr2610.jukebox.JukeboxMod;
import com.sr2610.jukebox.gui.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockJukebox extends Block implements ITileEntityProvider {
	public BlockJukebox(String unlocalizedName) {
		super(Material.ROCK);
		setUnlocalizedName(JukeboxMod.MODID + ":" + unlocalizedName);
		setCreativeTab(CreativeTabs.DECORATIONS);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
		TileEntityJukebox te = (TileEntityJukebox) world.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(world, pos, te);
		world.playEvent(1010, pos, 0);
		world.playRecord(pos, (SoundEvent) null);
		super.breakBlock(world, pos, blockstate);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityJukebox();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			if (playerIn.isSneaking()) {
				TileEntityJukebox juke = (TileEntityJukebox) worldIn.getTileEntity(pos);
				juke.nextSong();
			} else {
				playerIn.openGui(JukeboxMod.instance, GuiHandler.JUKEBOX_GUI, worldIn, pos.getX(), pos.getY(),
						pos.getZ());
			}
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (stack.hasDisplayName()) {
			((TileEntityJukebox) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
		}
	}

}