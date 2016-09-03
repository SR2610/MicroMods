package com.sr2610.deathchests.config;

import com.sr2610.deathchests.DeathChests;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {
	public ConfigGui(GuiScreen parent) {
		super(parent,
				new ConfigElement(ConfigHandler.configFile.getCategory(Configuration.CATEGORY_GENERAL))
						.getChildElements(),
				DeathChests.MODID, false, false, GuiConfig.getAbridgedConfigPath(ConfigHandler.configFile.toString()));

	}
}