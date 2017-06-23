package com.sr2610.jukebox.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;

public class SlotRecord extends Slot {

	public SlotRecord(IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
		super(inventoryIn, slotIndex, xPosition, yPosition);
		backgroundName = "jukebox:gui/record";
	}

	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ItemRecord;
	}

	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}



}
