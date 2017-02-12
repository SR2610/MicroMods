package com.sr2610.deathchests;

import com.sr2610.deathchests.config.ConfigHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = DeathChests.MODID, version = DeathChests.VERSION, updateJSON = DeathChests.UPDATEJSON, guiFactory = DeathChests.GUIFACTORY)
public class DeathChests {
	public static final String MODID = "deathchests";
	public static final String VERSION = "1.3";
	public static final String UPDATEJSON = "http://sr2610.com/micromods/deathChests/deathChestUpdate.json";
	public static final String GUIFACTORY = "com.sr2610.deathchests.config.GuiFactory";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.initConfig(event);

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new DeathEventHandler());
		MinecraftForge.EVENT_BUS.register(new ConfigHandler());

	}
}
