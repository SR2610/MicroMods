package com.sr2610.jukebox;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int JUKEBOX_GUI = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == JUKEBOX_GUI)
			return new ContainerJukebox(player.inventory,
					(TileEntityJukebox) world.getTileEntity(new BlockPos(x, y, z)));

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == JUKEBOX_GUI)
			return new GuiJukebox(player.inventory, (TileEntityJukebox) world.getTileEntity(new BlockPos(x, y, z)));

		return null;
	}

}
