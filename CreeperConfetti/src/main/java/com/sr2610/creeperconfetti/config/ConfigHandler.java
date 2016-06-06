package com.sr2610.creeperconfetti.config;

import com.sr2610.creeperconfetti.ConfettiMod;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	public static Configuration configFile;
	public static int confettiChance = 100;
	public static boolean damagesPlayers = false;

	public static void initConfig(FMLPreInitializationEvent event) {
		configFile = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (eventArgs.getModID().equals(ConfettiMod.MODID))
			syncConfig();
	}

	private static void syncConfig() {

		confettiChance = configFile.getInt("Confetti Chance", Configuration.CATEGORY_GENERAL, confettiChance, 0, 100,
				"The percent chance for a creeper to explode into confetti.");

		damagesPlayers = configFile.getBoolean("Damages Players", Configuration.CATEGORY_GENERAL, damagesPlayers,
				"Set to true if you still want creepers to damage players.");

		if (configFile.hasChanged())
			configFile.save();
	}

}
