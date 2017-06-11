package com.sr2610.creeperconfetti;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = ConfettiMod.MOD_ID, version = ConfettiMod.VERSION, updateJSON = ConfettiMod.UPDATEJSON)
public class ConfettiMod {
	public static final String MOD_ID = "creeperconfetti";
	public static final String CONFIG_LANG = "creeperconfetti.config.title";
	public static final String VERSION = "1.3";
	public static final String UPDATEJSON = "https://raw.githubusercontent.com/SR2610/MicroMods/master/CreeperConfetti/confettiUpdate.json";

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ConfettiHandler());
	}

}
