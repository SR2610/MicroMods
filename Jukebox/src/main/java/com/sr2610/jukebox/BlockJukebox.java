package com.sr2610.jukebox;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockJukebox extends Block {
	public BlockJukebox(String unlocalizedName) {
		super(Material.ROCK);
		setUnlocalizedName(JukeboxMod.MODID + ":" + unlocalizedName);
		setCreativeTab(CreativeTabs.DECORATIONS);
	}
}