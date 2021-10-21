package io.github.jodlodi;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DragonFightConfigEndergetic.MOD_ID)
@Mod.EventBusSubscriber(modid = DragonFightConfigEndergetic.MOD_ID)
public class DragonFightConfigEndergetic
{
    public static final String MOD_ID = "dragonfightconfigendergetic";

    public DragonFightConfigEndergetic() {
        IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }
}