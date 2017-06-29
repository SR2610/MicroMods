package com.sr2610.drawbridge.blocks;

import com.sr2610.drawbridge.DrawbridgeMod;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class BlockRenderRegistry {

	public static void registerBlockRenderers() {

		registerBlock(ModBlocks.drawbridge, "drawbridge_basic");
	}

	private static void registerBlock(Block block, String filename) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation(DrawbridgeMod.MODID + ":" + filename, "inventory"));
	}

	private static void registerBlock(Item item, int meta, String filename) {
		ModelLoader.setCustomModelResourceLocation(item, meta,
				new ModelResourceLocation(DrawbridgeMod.MODID + ":" + filename, "inventory"));
	}

}
