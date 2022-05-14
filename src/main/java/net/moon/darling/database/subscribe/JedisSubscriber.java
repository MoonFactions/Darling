package net.moon.darling.database.subscribe;

import net.moon.darling.DarlingPlugin;
import net.moon.darling.database.data.DataType;
import net.moon.darling.utils.CC;
import org.bukkit.entity.Player;
import redis.clients.jedis.JedisPubSub;

import java.util.regex.Pattern;

public class JedisSubscriber extends JedisPubSub {

    private final DarlingPlugin instance;

    public JedisSubscriber(DarlingPlugin instance) {
        this.instance = instance;
    }

    @Override
    public void onMessage(String channel, String message) {
        String[] strings = message.split(Pattern.quote("||"));
        DataType dataType = DataType.valueOf(strings[0]);
        String playerMessage = strings[1];

        switch (dataType) {
            case HELP: {
                for (Player player : this.instance.getServer().getOnlinePlayers()) {
                    if (player.hasPermission("darling.command.helpop.receive")) {
                        player.sendMessage(CC.chat(playerMessage));
                    }
                }
                break;
            }

            case REPORT: {
                for (Player player : this.instance.getServer().getOnlinePlayers()) {
                    if (player.hasPermission("darling.command.report.receive")) {
                        player.sendMessage(CC.chat(playerMessage));
                    }
                }
                break;
            }

            case STAFF_JOIN:
            case STAFF_QUIT: {
                for (Player player : this.instance.getServer().getOnlinePlayers()) {
                    if (player.hasPermission("darling.join.notify")) {
                        player.sendMessage(CC.chat(playerMessage));
                    }
                }
                break;
            }

            case STAFF_CHAT_MESSAGE: {
                for (Player player : this.instance.getServer().getOnlinePlayers()) {
                    if (player.hasPermission("darling.command.staffchat")) {
                        player.sendMessage(CC.chat(playerMessage));
                    }
                }
                break;
            }
        }

    }
}
