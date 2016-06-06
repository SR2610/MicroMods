package com.sr2610.creeperconfetti.proxy;

import com.sr2610.creeperconfetti.ConfettiHandler;
import com.sr2610.creeperconfetti.config.ConfigHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event){
		ConfigHandler.initConfig(event);
	}

	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ConfettiHandler());
		MinecraftForge.EVENT_BUS.register(new ConfigHandler());

	}

}
