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

public class ReportCommand {

    private final DarlingPlugin instance;

    private final Cooldown cooldown = new Cooldown();

    public ReportCommand(DarlingPlugin instance) {
        this.instance = instance;
    }

    @Command(label = "report", appendStrings = true)
    public void execute(Player player, @Parameter(name = "target") Player target, @Parameter(name = "reason") String reason) {
        if (target == null) {
            player.sendMessage(CC.chat("&cThat player is not online."));
            return;
        }

        if (reason == null || reason.isEmpty()) {
            player.sendMessage(CC.chat("&cYou must specify a valid reason."));
            return;
        }

        if (this.cooldown.isOnCooldown(player.getUniqueId())) {
            player.sendMessage(CC.chat("&cYou must wait " + DurationFormatUtils.formatDurationWords(this
                    .cooldown.getRemaining(player.getUniqueId()), true, true) + " before using this command again."));
            return;
        }



       player.sendMessage(CC.chat("&aAll staff on the network have been notified."));
       this.cooldown.placeOnCooldown(player.getUniqueId(), TimeUnit.MINUTES.toMillis(5L));
       String message = this.instance.getMessages().getString("report_message")
               .replace("\\n", "\n")
               .replace("%player%", player.getName())
               .replace("%reported%", target.getName())
               .replace("%server%", this.instance.getConfig().getString("server_name"))
               .replace("%reason%", reason);

       this.instance.getRedisHandler().publish(DataType.REPORT, message);
    }
}
