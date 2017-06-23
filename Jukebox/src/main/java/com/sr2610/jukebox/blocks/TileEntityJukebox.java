package com.sr2610.jukebox.blocks;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityJukebox extends TileEntity implements IInventory {

	private NonNullList<ItemStack> contents = NonNullList.<ItemStack>withSize(12, ItemStack.EMPTY);
	private String customName;
	public int selectedTrack = 0;

	@Override
	public void clear() {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = ItemStackHelper.getAndSplit(contents, index, count);

		if (!itemstack.isEmpty()) {
			markDirty();
		}

		return itemstack;
	}

	@Override
	public ITextComponent getDisplayName() {
		return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName());
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return selectedTrack;
		default:
			return 0;
		}
	}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getName() {
		return hasCustomName() ? customName : "container.jukebox";
	}

	@Override
	public int getSizeInventory() {
		return 12;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return contents.get(index);
	}

	@Override
	public boolean hasCustomName() {
		return customName != null && !customName.isEmpty();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : contents) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (stack.getItem() instanceof ItemRecord) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	public void nextSong() {
		if (isEmpty()) {
			return;
		}
		selectedTrack++;
		if (selectedTrack >= 12) {
			selectedTrack = 0;
		}
		while (contents.get(selectedTrack).isEmpty() || contents.get(selectedTrack) == null) {
			selectedTrack++;
			if (selectedTrack >= 11) {
				selectedTrack = 0;
			}
		}

	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	public void previousSong() {
		if (isEmpty()) {
			return;
		}
		selectedTrack--;
		if (selectedTrack <= -1) {
			selectedTrack = 11;
		}
		while (contents.get(selectedTrack).isEmpty() || contents.get(selectedTrack) == null) {
			selectedTrack--;
			if (selectedTrack <= 0) {
				selectedTrack = 11;
			}
		}

	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		contents = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);

		ItemStackHelper.loadAllItems(compound, contents);

		if (compound.hasKey("CustomName", 8)) {
			customName = compound.getString("CustomName");
		}

		selectedTrack = compound.getInteger("Track");
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(contents, index);
	}

	public void setCustomName(String name) {
		customName = name;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0:
			selectedTrack = value;
			break;
		}

	}

	@Override
	public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
		contents.set(index, stack);

		if (stack.getCount() > getInventoryStackLimit()) {
			stack.setCount(getInventoryStackLimit());
		}

		markDirty();
	}

	public void togglePause(boolean pause) {

		world.playEvent(1010, pos, 0);
		world.playRecord(pos, (SoundEvent) null);

		if (!contents.get(selectedTrack).isEmpty() && !pause)

			world.playEvent((EntityPlayer) null, 1010, pos, Item.getIdFromItem(contents.get(selectedTrack).getItem()));

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		ItemStackHelper.saveAllItems(compound, contents);

		if (hasCustomName()) {
			compound.setString("CustomName", customName);
		}

		compound.setInteger("Track", selectedTrack);
		return compound;
	}

}
