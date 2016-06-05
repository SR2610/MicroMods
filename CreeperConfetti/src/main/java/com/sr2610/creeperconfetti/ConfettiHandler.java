package com.sr2610.creeperconfetti;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleFirework;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

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
				if (creeper.worldObj.isRemote) {
					Random rand = new Random();
						Minecraft.getMinecraft().effectRenderer.addEffect(
								new ParticleFirework.Starter(creeper.worldObj, creeper.posX, creeper.posY + creeper.getEyeHeight(),
										creeper.posZ, 0, 0, 0, Minecraft.getMinecraft().effectRenderer, generateTag()));
					

				}
				creeper.setDead();
			}

		}
	}

	private NBTTagCompound generateTag() {
		NBTTagCompound fireworkTag = new NBTTagCompound();
		NBTTagCompound fireworkTagExplosion = new NBTTagCompound();
		NBTTagCompound fireworkItemTag = new NBTTagCompound();
		NBTTagCompound finalTag = new NBTTagCompound();
		NBTTagList nbttaglist = new NBTTagList();

		NBTTagList explosionsList = new NBTTagList();
		List<Integer> list = Lists.<Integer> newArrayList();
		list.add(ItemDye.DYE_COLORS[1]);
		list.add(ItemDye.DYE_COLORS[11]);
		list.add(ItemDye.DYE_COLORS[4]);

		int[] colours = new int[list.size()];

		for (int i = 0; i < colours.length; i++) {
			colours[i] = ((Integer) list.get(i)).intValue();
		}
		fireworkTag.setIntArray("Colors", colours);
		fireworkTag.setBoolean("Flicker", true);
		fireworkTag.setByte("Type", (byte) 4);
	//	fireworkTag.setTag("Explosion", fireworkTagExplosion);

		nbttaglist.appendTag(fireworkTag);

		fireworkItemTag.setTag("Explosions", nbttaglist);
		return fireworkItemTag;

	}

}
