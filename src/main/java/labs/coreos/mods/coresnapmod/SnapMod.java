package labs.coreos.mods.coresnapmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import labs.coreos.mods.coresnapmod.commands.SnapCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnapMod implements ModInitializer {
    public static final String MOD_ID = "snapmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing SnapMod - Perfectly balanced, as all things should be.");
        
        // This code block Register the /snap command
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            SnapCommand.register(dispatcher);
        });
        
        LOGGER.info("SnapMod initialized! Use /snap to erase entities.");
    }
}
