package net.moon.darling.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import lombok.Getter;
import net.moon.darling.DarlingPlugin;
import net.moon.darling.database.data.DataType;
import net.moon.darling.database.subscribe.JedisSubscriber;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.function.Consumer;

@Getter
public class RedisHandler {

    private final JedisPool jedisPool;

    private final DarlingPlugin instance;

    private final Gson gson = new GsonBuilder()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .serializeNulls()
            .disableHtmlEscaping()
            .create();

    private String password;

    private String channel;

    public RedisHandler(DarlingPlugin instance, String host, int port, String password, String channel) {
        this.instance = instance;
        this.password = password;
        this.channel = channel;
        this.jedisPool = new JedisPool(host, port);
        this.subscribe();
    }

    public void subscribe() {
        new Thread(() -> this.runCommand(jedis -> jedis.subscribe(new JedisSubscriber(this.instance), channel))).start();
    }

    public void publish(DataType dataType, String message) {
        this.runCommand(jedis -> jedis.publish(this.channel, dataType + "||" + message));
    }

    public void runCommand(Consumer<Jedis> consumer) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            if (!this.password.isEmpty()) {
                jedis.auth(this.password);
            }
            consumer.accept(jedis);
        }
    }


}
