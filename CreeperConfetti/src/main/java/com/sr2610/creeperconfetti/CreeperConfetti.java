package com.sr2610.creeperconfetti;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleFirework;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.Explosion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;
import java.util.Random;


@Mod("creeperconfetti")
public class CreeperConfetti
{

    public CreeperConfetti() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods

    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
    }

    @SubscribeEvent
    public void creeperExplodeEvent(LivingEvent.LivingUpdateEvent event){

        EntityCreeper creeper = null;
        if(event.getEntityLiving()instanceof  EntityCreeper)
            creeper = (EntityCreeper)event.getEntityLiving();
        else
            return;
        if (creeper != null && creeper.getCreeperState() == 1) {
            int ignitedTime =  ObfuscationReflectionHelper.getPrivateValue(EntityCreeper.class,creeper,"field_70833_d");

            int fuseTime = ObfuscationReflectionHelper.getPrivateValue(EntityCreeper.class, creeper, "field_82225_f");
            if (ignitedTime >= fuseTime - 1)
                if (willExplodeToConfetti()) {
                    creeper.getEntityWorld().playSound(null, creeper.posX, creeper.posY, creeper.posZ,
                            SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE, SoundCategory.HOSTILE, 0.5F,
                            (1.0F + (creeper.getEntityWorld().rand.nextFloat() - creeper.getEntityWorld().rand.nextFloat()) * 0.2F)
                                    * 0.7F);
                   // if (creeper.getEntityWorld().isRemote)
                        spawnParticles(creeper);
                    creeper.remove(); //Removes the creeper from the world, as if it was dead

                }

        }
    }

    private boolean willExplodeToConfetti() {
        Random rand = new Random();
    /*   if (rand.nextInt(100) >= ConfigHandler.confettiChance || ConfigHandler.confettiChance == 0)
            return false;
        else*/
            return true;
    }

    private void damagePlayers(EntityCreeper creeper) {
        float explosionStrength = creeper.getPowered() ? 2.0F : 1.0F;
        Explosion explosion = new Explosion(creeper.getEntityWorld(), creeper, creeper.posX, creeper.posY, creeper.posZ,
                3 * explosionStrength, false, false);
        explosion.doExplosionA();
    }

   @OnlyIn(Dist.CLIENT)
    private void spawnParticles(EntityCreeper creeper) {
	 //  creeper.getEntityWorld().spawnParticle(Particles.FIREWORK, creeper.posX, creeper.posY, creeper.posZ, 1.0D, 0.0D, 0.0D);

        Minecraft.getInstance().particles.addEffect(new ParticleFirework.Starter(creeper.getEntityWorld(), creeper.posX,
                creeper.posY+(creeper.getPowered()?2.5F:0.5F), creeper.posZ, 0, 0, 0, Minecraft.getInstance().particles, generateTag(creeper)));
    }

   private NBTTagCompound generateTag(EntityCreeper creeper) {
		NBTTagCompound fireworkTag = new NBTTagCompound();
		NBTTagCompound fireworkItemTag = new NBTTagCompound();
		NBTTagList nbttaglist = new NBTTagList();
		List<Integer> list = Lists.<Integer> newArrayList();
		Random rand = new Random();
		list.add(546515);
		list.add(13380608);
		list.add(52261);
		for (int i = 0; i < rand.nextInt(3) + 3; i++)
			list.add(rand.nextInt(15));

		int[] colours = new int[list.size()];

		for (int i = 0; i < colours.length; i++)
			colours[i] = list.get(i).intValue();
		fireworkTag.setIntArray("Colors", colours);
		fireworkTag.setBoolean("Flicker", true);
		fireworkTag.setByte("Type", (byte) (creeper.getPowered()?3:4));
        nbttaglist.add((INBTBase)fireworkTag);

		fireworkItemTag.setTag("Explosions", nbttaglist);
		System.out.println(fireworkItemTag.toString());
		return fireworkItemTag;

	}

}

