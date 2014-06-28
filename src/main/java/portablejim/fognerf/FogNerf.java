package portablejim.fognerf;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "fognerf", name="FogNerf", guiFactory = "portablejim.fognerf.client.FogNerfGuiFactory")
public class FogNerf {
    public static Configuration configFile;

    @Mod.Instance("fognerf")
    public static FogNerf instance;

    public static boolean lavaFogNerf = true;
    public static boolean voidFogNerf = true;
    public static boolean waterFogNerf = true;

    @SuppressWarnings("UnusedDeclaration")
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configFile = new Configuration(event.getSuggestedConfigurationFile());

        syncConfig();
    }

    @Mod.EventHandler
    public void init(@SuppressWarnings("UnusedParameters") FMLPreInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(this);
        FMLCommonHandler.instance().bus().register(instance);
    }

    @SuppressWarnings("UnusedDeclaration")
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals("fognerf")) {
            syncConfig();
        }
    }
    public static void syncConfig() {
        lavaFogNerf = configFile.getBoolean("Nerf lava fog", Configuration.CATEGORY_GENERAL, lavaFogNerf, "Nerf the fog in lava (i.e. enable clear lava)");
        voidFogNerf = configFile.getBoolean("Nerf void fog", Configuration.CATEGORY_GENERAL, voidFogNerf, "Nerf the void fog when at low map levels");
        waterFogNerf = configFile.getBoolean("Nerf water fog", Configuration.CATEGORY_GENERAL, waterFogNerf, "Nerf the fog in water (i.e. enable clear water)");

        if(configFile.hasChanged()) {
            configFile.save();
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public float lavaFog() {
        return lavaFogNerf ? 0.3F : 2.0F;
    }

    @SuppressWarnings("UnusedDeclaration")
    public float voidFog(float nerfedValue, float regularValue) {
        return voidFogNerf ? nerfedValue : regularValue;
    }

    @SuppressWarnings("UnusedDeclaration")
    public float waterFog() {
        return waterFogNerf ? 0.01F : 0.1F;
    }
}
