package com.sr2610.deathchests;

import net.minecraftforge.common.config.Config;

@Config(modid = DeathChests.MODID)
@Config.LangKey(DeathChests.CONFIG_LANG)
public class ConfigHandler {

	@Config.Comment("Set to false if you want a chest to always spawn.")
	public static boolean requiresChest = true;

}
