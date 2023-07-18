package xyz.superswolemmobs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.superswolemmobs.command.CommandChangeRenderValue;
import xyz.superswolemmobs.command.CommandToggleDebug;
import xyz.superswolemmobs.command.CommandToggleDistanceModifier;

/* Impressive... very nice... now let's see paul allans minecraft mod... */
@Mod(
        modid = LongDistanceTracking.MOD_ID,
        name = LongDistanceTracking.MOD_NAME,
        version = LongDistanceTracking.VERSION,
        clientSideOnly = true
)

public class LongDistanceTracking {

    /** Mod dependent signing information **/
    public static final String MOD_ID = "longdistancetracking";
    public static final String MOD_NAME = "LongDistanceTracking";
    public static final String VERSION = "1.0";

    /** Environment logger (client-console) **/
    public static final Logger modLogger = LogManager.getLogger(MOD_ID);

    /** Mod instance created by forge **/
    @Mod.Instance(MOD_ID)
    public static LongDistanceTracking MOD_INSTANCE;

    /** Render Distance weight value - affects how far entities will be allowed to render
     * (1 = 16blocks, 2 = 32blocks, 3 = 48blocks, 4 = 64 blocks) [increments by 16 every time] **/
    public static int customRenderDistanceWeight = 12;
    public static int FIXED_MAX_DISTANCE_ALLOWED = 16; // the maximum will be 256 blocks (idk why you would want this, but good luck)

    /** Is the mod allowed to handle how entities are rendered? **/
    public static boolean allowCustomRenderDistance = true;

    /** Are we checking for spicy bugs? **/
    public static boolean debugMode = false;

    /** {@link LongDistanceTracking} Mod Constructor **/
    public LongDistanceTracking() {
        MOD_INSTANCE = this;
    }

    /** Initialization event called after the {@link net.minecraftforge.fml.common.event.FMLPreInitializationEvent}
     * has successfully run. The registry events in this method run after the aforementioned event has passed. **/
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {

        // register and load our listeners - allows the mod to handle logic
        MinecraftForge.EVENT_BUS.register(new EntityTrackingHandler());

        // register and load our commands - allow the player to control aspects of the mod in-game
        ClientCommandHandler.instance.registerCommand(new CommandChangeRenderValue());
        ClientCommandHandler.instance.registerCommand(new CommandToggleDistanceModifier());
        ClientCommandHandler.instance.registerCommand(new CommandToggleDebug());
    }

    /** Event called after the {@link net.minecraftforge.fml.common.event.FMLPostInitializationEvent}
     * has successfully run. The events in this method run always after the entire mod has been loaded. **/
    @Mod.EventHandler
    public void initComplete(FMLLoadCompleteEvent e) {

        // log to console - successfully loaded the mod
        modLogger.log(Level.INFO, "Successfully loaded " + MOD_NAME + " mod  [v" + VERSION + "]");
    }

    /** Are we allowing the mod to give players 20/20 vision? **/
    public static void setModifyingTrackingRange(boolean allow) {
        allowCustomRenderDistance = allow;
    }

    /** Are we doing the spooky? **/
    public static void setDebugMode(boolean allow) {
        debugMode = allow;
    }

    /** @return {@link EntityPlayerSP} instance (client-side)
     * (the user who is enjoying the mod!) **/
    public static EntityPlayerSP getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

}
