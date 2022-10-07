package net.moon.darling;

import lombok.Getter;
import net.moon.darling.database.RedisHandler;
import net.moon.darling.staffchat.StaffChatHandler;
import net.moon.darling.staffchat.command.*;
import net.moon.darling.staffchat.listener.StaffChatListener;
import net.moon.darling.staffconnection.StaffConnectionListener;
import net.moon.darling.utils.Config;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.refinedev.fethmusmioma.CommandHandler;

@Getter
public class DarlingPlugin extends JavaPlugin {

    private Config config;

    private Config messages;

    private RedisHandler redisHandler;

    private StaffChatHandler staffChatHandler;

    public void onEnable() {
        this.config = new Config(this, "config");
        this.messages = new Config(this, "messages");
        this.redisHandler = new RedisHandler(this, this.config.getString("redis.host"), this.config.getInt("redis.port"), this.config.getString("redis.password"), this.config.getString("redis.channel"));
        this.staffChatHandler = new StaffChatHandler();
        this.registerCommands();
        this.registerListeners();
    }

    private void registerCommands() {
        CommandHandler commandHandler = new CommandHandler(this, "darling");
        commandHandler.registerCommand(new StaffChatCommand(this));
        commandHandler.registerCommand(new ReportCommand(this));
        commandHandler.registerCommand(new RequestCommand(this));
        commandHandler.registerCommand(new BroadcastCommand(this));
        commandHandler.registerCommand(new AlertCommand(this));
    }

    private void registerListeners() {
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new StaffChatListener(this), this);
        manager.registerEvents(new StaffConnectionListener(this), this);
    }

}
