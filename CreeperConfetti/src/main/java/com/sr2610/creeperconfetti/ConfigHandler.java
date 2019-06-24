package com.sr2610.creeperconfetti;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();

    public static class General {
        public final ForgeConfigSpec.ConfigValue<Boolean> DamagePlayers;
        public final ForgeConfigSpec.ConfigValue<Integer> ConfettiChance;

        public General(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            DamagePlayers = builder
                    .comment("Confetti Explosions Damage Players [false/true|default:false]")
                    .translation("damageplayers.creeperconfetti.config")
                    .define("damagePlayers", false);
            ConfettiChance = builder
                    .comment("The %chance that any given creeper will explode into confetti  [0..100|default:100]")
                    .translation("confettichance.creeperconfetti.config")
                    .defineInRange("confettiChance", 100, 0,100);
            builder.pop();
        }
    }
}