package com.sr2610.creeperconfetti;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleFirework;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ConfettiHandler {

	public static final String[] TIME_SINCE_IGNITED = { "timeSinceIgnited", "field_70833_d", "bq" };
	public static final String[] FUSE_TIME = { "fuseTime", "field_82225_f", "br" };

	@SubscribeEvent
	public void creeperExplodeEvent(LivingUpdateEvent event) {
		EntityCreeper creeper = null;
		if (event.getEntityLiving() instanceof EntityCreeper)
			creeper = (EntityCreeper) event.getEntityLiving();
		if (creeper != null && creeper.getCreeperState() == 1) {
			int ignitedTime = ((Integer) ReflectionHelper.getPrivateValue(EntityCreeper.class, creeper,
					TIME_SINCE_IGNITED)).intValue();

			int fuseTime = ((Integer) ReflectionHelper.getPrivateValue(EntityCreeper.class, creeper, FUSE_TIME))
					.intValue();

			if (ignitedTime >= fuseTime - 1) {
				creeper.worldObj.playSound((EntityPlayer) null, creeper.posX, creeper.posY, creeper.posZ,
						SoundEvents.ENTITY_FIREWORK_TWINKLE, SoundCategory.BLOCKS, 0.5F,
						(1.0F + (creeper.worldObj.rand.nextFloat() - creeper.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
				if (creeper.worldObj.isRemote)
					spawnParticles(creeper);
				creeper.setDead();
			}

		}
	}

	@SideOnly(Side.CLIENT)
	private void spawnParticles(EntityCreeper creeper) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleFirework.Starter(creeper.worldObj, creeper.posX,
				creeper.posY, creeper.posZ, 0, 0, 0, Minecraft.getMinecraft().effectRenderer, generateTag()));
	}

	private NBTTagCompound generateTag() {
		NBTTagCompound fireworkTag = new NBTTagCompound();
		new NBTTagCompound();
		NBTTagCompound fireworkItemTag = new NBTTagCompound();
		new NBTTagCompound();
		NBTTagList nbttaglist = new NBTTagList();

		new NBTTagList();
		List<Integer> list = Lists.<Integer> newArrayList();

		Random rand = new Random();

		list.add(ItemDye.DYE_COLORS[1]);
		list.add(ItemDye.DYE_COLORS[11]);
		list.add(ItemDye.DYE_COLORS[4]);
		for (int i = 0; i < rand.nextInt(3) + 3; i++)
			list.add(ItemDye.DYE_COLORS[rand.nextInt(15)]);

		int[] colours = new int[list.size()];

		for (int i = 0; i < colours.length; i++)
			colours[i] = list.get(i).intValue();
		fireworkTag.setIntArray("Colors", colours);
		fireworkTag.setBoolean("Flicker", true);
		fireworkTag.setByte("Type", (byte) 4);
		nbttaglist.appendTag(fireworkTag);

		fireworkItemTag.setTag("Explosions", nbttaglist);
		return fireworkItemTag;

	}

}
