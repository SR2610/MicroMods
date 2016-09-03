package com.sr2610.deathchests.config;

import com.sr2610.deathchests.DeathChests;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	public static Configuration configFile;
	public static boolean requiresChest = true;

	public static void initConfig(FMLPreInitializationEvent event) {
		configFile = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (eventArgs.getModID().equals(DeathChests.MODID))
			syncConfig();
	}

	private static void syncConfig() {

	

		requiresChest = configFile.getBoolean("Requires Chest", Configuration.CATEGORY_GENERAL, requiresChest,
				"Set to false if a chest is not needed to spawn a death chest.");

		if (configFile.hasChanged())
			configFile.save();
	}

}
