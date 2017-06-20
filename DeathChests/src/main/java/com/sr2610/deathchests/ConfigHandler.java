package com.sr2610.deathchests;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = DeathChests.MODID)
@Config.LangKey(DeathChests.CONFIG_LANG)
public class ConfigHandler {

	@Mod.EventBusSubscriber
	private static class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(DeathChests.MODID)) {
				ConfigManager.sync(DeathChests.MODID, Config.Type.INSTANCE);
			}
		}

	}

	@Config.Comment("Set to false if you want a chest to always spawn.")
	public static boolean requiresChest = true;

}