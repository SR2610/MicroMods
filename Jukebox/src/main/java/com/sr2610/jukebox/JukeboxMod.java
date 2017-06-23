package com.sr2610.jukebox;

import com.sr2610.jukebox.blocks.BlockJukebox;
import com.sr2610.jukebox.blocks.TileEntityJukebox;
import com.sr2610.jukebox.gui.GuiHandler;
import com.sr2610.jukebox.network.PacketHandler;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = JukeboxMod.MODID, version = JukeboxMod.VERSION)
public class JukeboxMod {

	public static class ClientProxy extends CommonProxy {
		@Override
		public void init(FMLInitializationEvent e) {
			super.init(e);

		}

		@Override
		public void preInit(FMLPreInitializationEvent e) {
			super.preInit(e);
			registerBlock();

		}
	}

	public static class CommonProxy {
		public void init(FMLInitializationEvent e) {
			NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		}

		public void postInit(FMLPostInitializationEvent e) {

		}

		public void preInit(FMLPreInitializationEvent e) {
			GameRegistry.register(jukebox, new ResourceLocation(MODID, "jukebox"));
			GameRegistry.register(new ItemBlock(jukebox), new ResourceLocation(MODID, "jukebox"));
			GameRegistry.registerTileEntity(TileEntityJukebox.class, "jb_jukebox");
			MinecraftForge.EVENT_BUS.register(new JukeboxMod());
			PacketHandler.registerMessages("jukebox");
		}
	}

	@Instance
	public static JukeboxMod instance = new JukeboxMod();
	public static final String MODID = "jukebox";

	public static final String VERSION = "1.0";

	public static BlockJukebox jukebox = new BlockJukebox("jukebox");

	@SidedProxy
	public static CommonProxy proxy;

	@SideOnly(Side.CLIENT)
	private static void registerBlock() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(jukebox), 0,
				new ModelResourceLocation("jukebox:jukebox", "inventory"));
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		proxy.preInit(event);

	}

	@SubscribeEvent
	public void textureStich(TextureStitchEvent.Pre event) {

		event.getMap().registerSprite(new ResourceLocation("jukebox:gui/record"));
	}
}
