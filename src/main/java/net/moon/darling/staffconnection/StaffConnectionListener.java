package net.moon.darling.staffconnection;

import net.moon.darling.DarlingPlugin;
import net.moon.darling.database.data.DataType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StaffConnectionListener implements Listener {

    private final DarlingPlugin instance;

    public StaffConnectionListener(DarlingPlugin instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("darling.join.notify")) {
            String message = this.instance.getMessages().getString("staff_join_message")
                    .replace("%staff%", player.getName())
                    .replace("%server%", this.instance.getConfig().getString("server_name"));
            this.instance.getRedisHandler().publish(DataType.STAFF_JOIN, message);
            event.setJoinMessage(null);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("darling.join.notify")) {
            String message = this.instance.getMessages().getString("staff_quit_message")
                    .replace("%staff%", player.getName())
                    .replace("%server%", this.instance.getConfig().getString("server_name"));
        this.instance.getRedisHandler().publish(DataType.STAFF_QUIT, message);
            event.setQuitMessage(null);
        }
    }
}
