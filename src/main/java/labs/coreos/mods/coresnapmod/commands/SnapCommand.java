package labs.coreos.mods.coresnapmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class SnapCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("snap")
            .requires(source -> source.hasPermission(2)) // Requires OP level 2
            .executes(SnapCommand::executeSnap));
    }

    private static int executeSnap(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        
        try {
            // Execute /kill @e command to kill all entities
            String killCommand = "kill @e";
            source.getServer().getCommands().performPrefixedCommand(source, killCommand);
            
            // Send feedback
            source.sendSuccess(
                () -> Component.literal("§6*SNAP* §rAll entities turned to dust!"), 
                true // Broadcast to ops
            );
            
            return 1; // Success
            
        } catch (Exception e) {
            source.sendFailure(Component.literal("§cFailed to snap entities: " + e.getMessage()));
            return 0;
        }
    }
}
