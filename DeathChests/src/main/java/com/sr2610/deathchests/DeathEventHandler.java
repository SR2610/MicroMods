package com.sr2610.deathchests;

import java.util.List;

import com.sr2610.deathchests.config.ConfigHandler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DeathEventHandler {

	private boolean foundChest;

	@SubscribeEvent
	public void handlePlayerDeath(PlayerDropsEvent event) {
		if (checkForChest(event.getDrops()))
			transferIntoChest(event);
		foundChest = false;
	}

	private void transferIntoChest(PlayerDropsEvent event) {
		int stacksTransferred = 0;
		EntityPlayer player = event.getEntityPlayer();
		BlockPos position = new BlockPos(player.posX, player.posY, player.posZ);
		player.world.setBlockState(position, Blocks.CHEST.getDefaultState(), 2);
		TileEntityChest chest = (TileEntityChest) player.world.getTileEntity(position);
		
		for (EntityItem droppedStack : event.getDrops()) {
			stacksTransferred++;
			if (stacksTransferred > chest.getSizeInventory())
				return;
			if (droppedStack.getEntityItem().getCount() != 0)
				if (foundChest)
					chest.setInventorySlotContents(stacksTransferred - 2, droppedStack.getEntityItem());
				else
					chest.setInventorySlotContents(stacksTransferred - 1, droppedStack.getEntityItem());
			droppedStack.setDead();

		}

	}

	private boolean checkForChest(List<EntityItem> list) {

		for (EntityItem droppedStack : list) {
			if (droppedStack.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
				droppedStack.getEntityItem().setCount((droppedStack.getEntityItem().getCount())-1);
				foundChest = true;
				return true;
			}

		}
		if (!ConfigHandler.requiresChest)
			return true;

		return false;

	}

}
