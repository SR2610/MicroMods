package com.sr2610.drawbridge.blocks;

import com.sr2610.drawbridge.DrawbridgeMod;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBlocks {

	public static BlockDrawbridge drawbridge = new BlockDrawbridge("drawbridge");

	public static void createBlocks() {

		RegisterBlockAndItemBlock(drawbridge, "drawbridge");

	}

	private static void RegisterBlockAndItemBlock(Block block, String name) {
		block.setRegistryName(new ResourceLocation(DrawbridgeMod.MODID, name));
		ForgeRegistries.BLOCKS.register(block);
		ItemBlock iBlock = new ItemBlock(block);
		iBlock.setRegistryName(new ResourceLocation(DrawbridgeMod.MODID, name));
		ForgeRegistries.ITEMS.register(iBlock);
	}

}
