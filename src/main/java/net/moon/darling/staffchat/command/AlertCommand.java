package net.moon.darling.staffchat.command;

import net.moon.darling.DarlingPlugin;
import net.moon.darling.database.data.DataType;
import org.bukkit.command.CommandSender;
import xyz.refinedev.fethmusmioma.annotation.Command;
import xyz.refinedev.fethmusmioma.annotation.Parameter;

public class AlertCommand {

    private final DarlingPlugin instance;

    public AlertCommand(DarlingPlugin instance) {
        this.instance = instance;
    }

    @Command(label = "alert", permission = "darling.command.alert", appendStrings = true)
    public void execute(CommandSender sender, @Parameter(name = "message") String message) {
        this.instance.getRedisHandler().publish(DataType.BROADCAST, "&8[&4Alert&8] &f" + message);
    }
}
