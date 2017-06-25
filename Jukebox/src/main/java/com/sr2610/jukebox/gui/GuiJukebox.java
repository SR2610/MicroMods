package com.sr2610.jukebox.gui;

import java.io.IOException;

import com.sr2610.jukebox.blocks.TileEntityJukebox;
import com.sr2610.jukebox.container.ContainerJukebox;
import com.sr2610.jukebox.network.PacketHandler;
import com.sr2610.jukebox.network.PacketSendNext;
import com.sr2610.jukebox.network.PacketSendPause;
import com.sr2610.jukebox.network.PacketSendPrevious;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

public class GuiJukebox extends GuiContainer {

	private IInventory playerInv;
	private TileEntityJukebox te;

	GuiButton pause;
	GuiButton play;
	GuiButton next;
	GuiButton previous;

	public GuiJukebox(IInventory playerInv, TileEntityJukebox te) {
		super(new ContainerJukebox(playerInv, te));

		this.playerInv = playerInv;
		this.te = te;

		xSize = 176;
		ySize = 166;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			switch (button.id) {
			case 0:
				PacketHandler.INSTANCE.sendToServer(new PacketSendPause(te.getPos(), true));
				break;
			case 1:
				PacketHandler.INSTANCE.sendToServer(new PacketSendNext(te.getPos()));
				break;
			case 2:
				PacketHandler.INSTANCE.sendToServer(new PacketSendPrevious(te.getPos()));
				break;
			case 3:
				PacketHandler.INSTANCE.sendToServer(new PacketSendPause(te.getPos(), false));
				break;
			}
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(new ResourceLocation("jukebox:textures/gui/jukebox.png"));
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = te.getDisplayName().getUnformattedText();
		fontRenderer.drawString(s, 88 - fontRenderer.getStringWidth(s) / 2, 6, 4210752); // #404040
		if (te.currentlyPlaying != -1) {
			fontRenderer.drawString(
					I18n.format("jukebox.nowplaying")
							+ ((ItemRecord) te.getStackInSlot(te.currentlyPlaying).getItem()).getRecordNameLocal(),
					8, 62, 4210752); // #404040
		} else
			fontRenderer.drawString(I18n.format("jukebox.paused"), 8, 62, 4210752); // #404040
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752); // #404040
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(new ResourceLocation("jukebox:textures/gui/jukebox.png"));
		int index = te.selectedTrack;
		int x, y;
		x = index % 6;
		y = index / 6;
		this.drawTexturedModalRect(6 + x * 18, 22 + y * 18, 236, 0, 20, 20);

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		pause = this.addButton(new GuiButton(0, guiLeft + 145, guiTop + 15, 20, 20, I18n.format("jukebox.pause")));
		play = this.addButton(new GuiButton(3, guiLeft + 120, guiTop + 15, 20, 20, I18n.format("jukebox.play")));
		next = this.addButton(new GuiButton(1, guiLeft + 145, guiTop + 40, 20, 20, I18n.format("jukebox.next")));
		previous = this
				.addButton(new GuiButton(2, guiLeft + 120, guiTop + 40, 20, 20, I18n.format("jukebox.previous")));
	}
}
