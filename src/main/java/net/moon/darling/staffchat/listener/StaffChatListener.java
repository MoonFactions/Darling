package net.moon.darling.staffchat.listener;

import net.milkbowl.vault.chat.Chat;
import net.moon.darling.DarlingPlugin;
import net.moon.darling.database.data.DataType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChatListener implements Listener {

    private final DarlingPlugin instance;

    private Chat chat;

    public StaffChatListener(DarlingPlugin instance) {
        this.instance = instance;
        if (this.instance.getServer().getPluginManager().isPluginEnabled("Vault")) {
            this.chat = this.instance.getServer().getServicesManager().getRegistration(Chat.class).getProvider();
        }
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().replace("%", "%%");

        if (event.isCancelled() || !this.instance.getStaffChatHandler().hasStaffChatEnabled(player.getUniqueId())) {
            return;
        }

        String formatted;

        if (this.chat == null) {
            formatted = this.instance.getMessages().getString("staff_chat_format")
                    .replace("%server%", this.instance.getConfig().getString("server_name"))
                    .replace("%player%", player.getName())
                    .replace("%message%", message);
        } else {
            formatted = this.instance.getMessages().getString("staff_chat_format_vault")
                    .replace("%server%", this.instance.getConfig().getString("server_name"))
                            .replace("%player%", player.getName())
                            .replace("%message%", message)
                            .replace("%prefix%", chat.getGroupPrefix(player.getWorld(), chat.getPrimaryGroup(player)));
        }

        this.instance.getRedisHandler().publish(DataType.STAFF_CHAT_MESSAGE, formatted);
        event.setCancelled(true);
    }
}
