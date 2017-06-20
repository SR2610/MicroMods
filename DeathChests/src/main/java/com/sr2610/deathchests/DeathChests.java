package com.sr2610.deathchests;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = DeathChests.MODID, version = DeathChests.VERSION)
public class DeathChests {
	public static final String MODID = "deathchests";
	public static final String CONFIG_LANG = "deathchests.config.title";
	public static final String VERSION = "1.3";

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

	}

	@SubscribeEvent
	public void onPlayerDeath(PlayerDropsEvent event) {
		boolean spawnChest = false;
		boolean trapped = false;
		int counter = 0;
		int posX = MathHelper.floor(event.getEntityPlayer().posX);
		int posY = MathHelper.floor(event.getEntityPlayer().posY);
		int posZ = MathHelper.floor(event.getEntityPlayer().posZ);
		World world = event.getEntityPlayer().world;
		BlockPos spawnPos = new BlockPos(posX, posY, posZ);
		if (!world.getBlockState(spawnPos).getBlock().isReplaceable(world, spawnPos))
			return;

		if (ConfigHandler.requiresChest) {
			for (EntityItem droppedStack : event.getDrops()) {
				if (!spawnChest)
					if (droppedStack.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
						droppedStack.getEntityItem().setCount(droppedStack.getEntityItem().getCount() - 1);
						spawnChest = true;
					} else if (droppedStack.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.TRAPPED_CHEST)) {
						droppedStack.getEntityItem().setCount(droppedStack.getEntityItem().getCount() - 1);
						spawnChest = true;
						trapped = true;
					}
			}
		} else {
			spawnChest = true;
		}

		if (spawnChest) {

			if (!trapped) {
				world.setBlockState(spawnPos, Blocks.CHEST.getDefaultState(), 3);
			} else {
				world.setBlockState(spawnPos, Blocks.TRAPPED_CHEST.getDefaultState(), 3);
			}

			TileEntityChest chest = (TileEntityChest) world.getTileEntity(new BlockPos(posX, posY, posZ));

			for (EntityItem droppedItemEntity : event.getDrops()) {
				counter++;
				ItemStack droppedItem = droppedItemEntity.getEntityItem();
				if (counter > chest.getSizeInventory())
					return;
				else {
					chest.setInventorySlotContents(counter - 1, droppedItem);
					droppedItemEntity.setDead(); // Prevent the item from
													// spawning in the world
				}
			}

		}

	}
}
