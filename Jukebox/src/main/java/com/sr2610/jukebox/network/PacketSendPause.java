package com.sr2610.jukebox.network;

import com.sr2610.jukebox.blocks.TileEntityJukebox;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSendPause implements IMessage {

	private BlockPos blockPos;
	private boolean pause;

	public PacketSendPause() {

	}

	public PacketSendPause(BlockPos pos, boolean shouldPause) {
		blockPos = pos;
		pause = shouldPause;

	}

	@Override
	public void fromBytes(ByteBuf buf) {
		blockPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		pause = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(blockPos.getX());
		buf.writeInt(blockPos.getY());
		buf.writeInt(blockPos.getZ());
		buf.writeBoolean(pause);
	}

	public static class Handler implements IMessageHandler<PacketSendPause, IMessage> {
		private void handle(PacketSendPause message, MessageContext ctx) {
			EntityPlayerMP playerEntity = ctx.getServerHandler().player;
			World world = playerEntity.world;
			TileEntityJukebox juke = (TileEntityJukebox) world.getTileEntity(message.blockPos);
			juke.togglePause(message.pause);

		}

		@Override
		public IMessage onMessage(PacketSendPause message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
	}

}
