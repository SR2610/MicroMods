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
import net.minecraft.util.ResourceLocation;

public class GuiJukebox extends GuiContainer {

	private IInventory playerInv;
	private TileEntityJukebox te;

	private GuiButton pause;
	private GuiButton next;
	private GuiButton previous;

	public GuiJukebox(IInventory playerInv, TileEntityJukebox te) {
		super(new ContainerJukebox(playerInv, te));

		this.playerInv = playerInv;
		this.te = te;

		this.xSize = 176;
		this.ySize = 166;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	public void initGui() {
		super.initGui();
		this.buttonList.clear();
		this.pause = this.addButton(new GuiButton(0, guiLeft + 145, guiTop + 15, 20, 20, I18n.format("jukebox.pause")));
		this.next = this.addButton(new GuiButton(1, guiLeft + 145, guiTop + 40, 20, 20, I18n.format("jukebox.next")));
		this.previous = this
				.addButton(new GuiButton(2, guiLeft + 10, guiTop + 40, 20, 20, I18n.format("jukebox.previous")));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("jukebox:textures/gui/jukebox.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.te.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, 88 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752); // #404040
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752); // #404040
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("jukebox:textures/gui/jukebox.png"));
		int index = te.selectedTrack;
		int x, y;
		x = index % 6;
		y = index / 6;
		this.drawTexturedModalRect(32 + x * 18, 21 + y * 18, 234, 0, 22, 22);

	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			switch (button.id) {
			case 0:
				// te.togglePause();
				PacketHandler.INSTANCE.sendToServer(new PacketSendPause(te.getPos()));
				break;
			case 1:
				// te.nextSong();
				PacketHandler.INSTANCE.sendToServer(new PacketSendNext(te.getPos()));
				break;
			case 2:
				// te.previousSong();
				PacketHandler.INSTANCE.sendToServer(new PacketSendPrevious(te.getPos()));
				break;
			}
		}

	}
}
