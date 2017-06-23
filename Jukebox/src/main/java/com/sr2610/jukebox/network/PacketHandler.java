package com.sr2610.jukebox.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	private static int packetId = 0;

	public static SimpleNetworkWrapper INSTANCE = null;

	public PacketHandler() {
	}

	public static int nextID() {
		return packetId++;
	}

	public static void registerMessages(String channelName) {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}

	public static void registerMessages() {
		INSTANCE.registerMessage(PacketSendNext.Handler.class, PacketSendNext.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSendPrevious.Handler.class, PacketSendPrevious.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSendPause.Handler.class, PacketSendPause.class, nextID(), Side.SERVER);
	}
}
