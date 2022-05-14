package net.moon.darling.staffchat.command;

import net.moon.darling.DarlingPlugin;
import net.moon.darling.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.refinedev.fethmusmioma.annotation.Command;

public class StaffChatCommand {

    private final DarlingPlugin instance;

    public StaffChatCommand(DarlingPlugin instance) {
        this.instance = instance;
    }

    @Command(label = "staffchat", aliases = {"sc"}, permission = "darling.command.staffchat")
    public void execute(Player player) {
        if (this.instance.getStaffChatHandler().hasStaffChatEnabled(player.getUniqueId())) {
            this.instance.getStaffChatHandler().disableStaffChat(player.getUniqueId());
            player.sendMessage(CC.chat(this.instance.getMessages().getString("staff_chat_disabled")));
            return;
        }

        this.instance.getStaffChatHandler().enableStaffChat(player.getUniqueId());
        player.sendMessage(CC.chat(this.instance.getMessages().getString("staff_chat_enabled")));
    }
}
