package net.moon.darling.staffchat.command;

import net.moon.darling.DarlingPlugin;
import net.moon.darling.database.data.DataType;
import net.moon.darling.utils.CC;
import net.moon.darling.utils.Cooldown;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.entity.Player;
import xyz.refinedev.fethmusmioma.annotation.Command;
import xyz.refinedev.fethmusmioma.annotation.Parameter;

import java.util.concurrent.TimeUnit;

public class RequestCommand {

    private final DarlingPlugin instance;

    private final Cooldown cooldown = new Cooldown();

    public RequestCommand(DarlingPlugin instance) {
        this.instance = instance;
    }

    @Command(label = "request", aliases = {"helpop", "staffhelp"}, appendStrings = true)
    public void execute(Player player, @Parameter(name = "reason") String reason) {
        if (reason == null || reason.isEmpty()) {
            player.sendMessage(CC.chat("&cYou must send a valid reason."));
            return;
        }

        if (cooldown.isOnCooldown(player.getUniqueId())) {
            player.sendMessage(CC.chat("&cYou must wait " + DurationFormatUtils.formatDurationWords(this
                    .cooldown.getRemaining(player.getUniqueId()), true, true) + " before using this command again."));
            return;
        }

        player.sendMessage(CC.chat("&aAll staff on the network have been notified."));
        cooldown.placeOnCooldown(player.getUniqueId(), TimeUnit.MINUTES.toMillis(5L));
        String message = "&9[Request] &7(%server%) &9%player% &bhas requested assistance\\n      &9Reason: &b%reason%"
                .replace("\\n", "\n")
                .replace("%server%", this.instance.getConfig().getString("server_name"))
                .replace("%player%", player.getName())
                .replace("%reason%", reason);

        this.instance.getRedisHandler().publish(DataType.HELP, message);

    }
}
