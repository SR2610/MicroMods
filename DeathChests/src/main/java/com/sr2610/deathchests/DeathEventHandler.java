package com.sr2610.deathchests;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DeathEventHandler {


	@SubscribeEvent
	public void onPlayerDeath(PlayerDropsEvent event) {
		boolean spawnChest = false;
		int counter = 0;
		if(!ConfigHandler.requiresChest) {
			spawnChest = true;
		}
		
		for(EntityItem droppedStack : event.getDrops()) {
			if(!spawnChest)
			if(droppedStack.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.CHEST)) { //If the player has a chest
				droppedStack.getEntityItem().setCount(droppedStack.getEntityItem().getCount()-1);
				spawnChest = true;
			}
		}
		
		if(spawnChest) {
			int posX = MathHelper.floor(event.getEntityPlayer().posX);
			int posY = MathHelper.floor(event.getEntityPlayer().posY);
			int posZ = MathHelper.floor(event.getEntityPlayer().posZ);
			World world = event.getEntityPlayer().world;
			
			world.setBlockState(new BlockPos(posX, posY, posZ), Blocks.CHEST.getDefaultState(), 2);
			TileEntityChest chest = (TileEntityChest) world.getTileEntity(new BlockPos(posX, posY, posZ));
			for(EntityItem droppedItemEntity : event.getDrops()) {
				counter++;
				ItemStack droppedItem = droppedItemEntity.getEntityItem();				
				if(counter > chest.getSizeInventory()) { //If the chest is full we just want standard drop behaviour
					return;
				} else {
					chest.setInventorySlotContents(counter - 1, droppedItem);
					droppedItemEntity.setDead(); //Prevent the item from spawning in the world
				}
			}
		}
	}
}
