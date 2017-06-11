package com.sr2610.creeperconfetti;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ConfettiMod.MOD_ID)
@Config.LangKey(ConfettiMod.CONFIG_LANG)
public class ConfigHandler {

	@Config.Comment("The percent chance for a creeper to explode into confetti.")
	public static int confettiChance = 100;

	@Config.Comment("Set to true if you still want creepers to damage players.")
	public static boolean damagesPlayers = false;

}

@Mod.EventBusSubscriber
class EventHandler {
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(ConfettiMod.MOD_ID)) {
			ConfigManager.sync(ConfettiMod.MOD_ID, Config.Type.INSTANCE);
		}
	}

}
