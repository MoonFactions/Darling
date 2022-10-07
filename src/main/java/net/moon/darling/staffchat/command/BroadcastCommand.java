package net.moon.darling.staffchat.command;

import net.moon.darling.DarlingPlugin;
import net.moon.darling.database.data.DataType;
import org.bukkit.command.CommandSender;
import xyz.refinedev.fethmusmioma.annotation.Command;
import xyz.refinedev.fethmusmioma.annotation.Parameter;

public class BroadcastCommand {

    private final DarlingPlugin instance;

    public BroadcastCommand(DarlingPlugin instance) {
        this.instance = instance;
    }

    @Command(label = "broadcast", aliases = {"bc", "announce", "announcement"}, permission = "darling.command.broadcast", appendStrings = true)
    public void execute(CommandSender sender, @Parameter(name = "message") String message) {
        this.instance.getRedisHandler().publish(DataType.BROADCAST, message);
    }

}
