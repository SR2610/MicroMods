package com.sr2610.creeperconfetti;

import net.minecraft.entity.monster.EntityCreeper;
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
				if(!creeper.isServerWorld())
				spawnConfetti(creeper);
				creeper.setDead();
			}
		}
	}

	private void spawnConfetti(EntityCreeper creeper) {
		
	}

}
