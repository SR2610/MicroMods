package com.sr2610.deathchests;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DeathEventHandler {

	@SubscribeEvent
	public void handlePlayerDeath(PlayerDropsEvent event) {
		if (checkForChest(event.getDrops()))
			System.out.println("Found a chest");

	}

	private boolean checkForChest(List<EntityItem> list) {

		for (EntityItem droppedStack : list) {
			if (droppedStack.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
				return true;
			}
		}
		return false;

	}

}
