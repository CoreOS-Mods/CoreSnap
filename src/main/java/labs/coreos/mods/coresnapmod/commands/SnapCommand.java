package labs.coreos.mods.coresnapmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class SnapCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("snap")
            .requires(source -> source.hasPermission(2)) // Requires OP level 2
            .executes(SnapCommand::executeSnap));
    }

    private static int executeSnap(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        
        try {
            ServerLevel world = source.getLevel();
            final int[] entityCount = {0};
            
            // Get all entities and kill them
            // Deprecated actual command
            for (Entity entity : world.getAllEntities()) {
                // Skip command source to prevent self-destruction
                if (entity == source.getEntity()) {
                    continue;
                }
                
                // Kill the entity
                entity.discard();
                entityCount[0]++;
            }
            
            final int count = entityCount[0];
            
            // Send custom feedback (suppresses default /kill message)
            source.sendSuccess(
                () -> Component.literal("§6*SNAP* §r" + count + " entities turned to dust!"), 
                true // Broadcast to ops
            );
            
            return count;
            
        } catch (Exception e) {
            source.sendFailure(Component.literal("§cFailed to snap entities: " + e.getMessage()));
            return 0;
        }
    }
}
