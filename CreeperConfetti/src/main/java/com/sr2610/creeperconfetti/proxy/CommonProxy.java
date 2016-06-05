package com.sr2610.creeperconfetti.proxy;

import com.sr2610.creeperconfetti.ConfettiHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class CommonProxy {

	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ConfettiHandler());
	}

}
