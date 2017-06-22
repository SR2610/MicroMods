package com.sr2610.jukebox;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = JukeboxMod.MODID, version = JukeboxMod.VERSION)
public class JukeboxMod {
	public static final String MODID = "jukebox";
	public static final String VERSION = "1.0";
	public static BlockJukebox jukebox = new BlockJukebox("jukebox");

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		GameRegistry.register(jukebox, new ResourceLocation(MODID, "jukebox"));
		GameRegistry.register(new ItemBlock(jukebox), new ResourceLocation(MODID, "jukebox"));
		registerBlock();
	}

	@SideOnly(Side.CLIENT)
	private static void registerBlock() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(jukebox), 0,
				new ModelResourceLocation("jukebox:jukebox", "inventory"));
	}
}
