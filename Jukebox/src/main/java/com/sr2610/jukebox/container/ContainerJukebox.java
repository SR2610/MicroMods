package com.sr2610.jukebox.container;

import com.sr2610.jukebox.blocks.TileEntityJukebox;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerJukebox extends Container {

	private TileEntityJukebox te;

	private int[] cachedFields;

	public ContainerJukebox(IInventory playerInv, TileEntityJukebox te) {
		this.te = te;

		for (int y = 0; y < 2; ++y) {
			for (int x = 0; x < 6; ++x) {
				addSlotToContainer(new SlotRecord(te, x + y * 6, 35 + x * 18, 24 + y * 18));
			}
		}

		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x) {
			addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.isUsableByPlayer(playerIn);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		boolean allFieldsHaveChanged = false;
		boolean fieldHasChanged[] = new boolean[te.getFieldCount()];
		if (cachedFields == null) {
			cachedFields = new int[te.getFieldCount()];
			allFieldsHaveChanged = true;
		}
		for (int i = 0; i < cachedFields.length; ++i) {
			if (allFieldsHaveChanged || cachedFields[i] != te.getField(i)) {
				cachedFields[i] = te.getField(i);
				fieldHasChanged[i] = true;
			}
		}
		for (IContainerListener listener : listeners) {
			for (int fieldID = 0; fieldID < te.getFieldCount(); ++fieldID) {
				if (fieldHasChanged[fieldID]) {

					listener.sendWindowProperty(this, fieldID, cachedFields[fieldID]);
				}
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < te.getSizeInventory()) {
				if (!mergeItemStack(itemstack1, te.getSizeInventory(), inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(itemstack1, 0, te.getSizeInventory(), false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		te.setField(id, data);
	}

}
