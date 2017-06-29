package com.sr2610.drawbridge.blocks;

import com.sr2610.drawbridge.DrawbridgeMod;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockDrawbridge extends Block {

	public BlockDrawbridge(String unlocalizedName) {
		super(Material.IRON, MapColor.IRON);
		setUnlocalizedName(DrawbridgeMod.MODID + ":" + unlocalizedName);
		setCreativeTab(CreativeTabs.REDSTONE);
		setHardness(3.5F);
		setSoundType(SoundType.METAL);

	}

}
