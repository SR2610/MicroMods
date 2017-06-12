package com.sr2610.deathchests;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = DeathChests.MODID, version = DeathChests.VERSION, updateJSON = DeathChests.UPDATEJSON)
public class DeathChests {
	public static final String MODID = "deathchests";
	public static final String CONFIG_LANG = "deathchests.config.title";
	public static final String VERSION = "1.3";
	public static final String UPDATEJSON = "http://sr2610.com/micromods/deathChests/deathChestUpdate.json";

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new DeathEventHandler());
		MinecraftForge.EVENT_BUS.register(new ConfigHandler());

	}
}
