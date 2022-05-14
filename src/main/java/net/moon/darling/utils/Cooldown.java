package net.moon.darling.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cooldown {

    private final Map<UUID, Long> cooldownMap = new HashMap<>();

    public long getRemaining(UUID uuid) {
        return this.cooldownMap.get(uuid) - System.currentTimeMillis();
    }

    public boolean isOnCooldown(UUID uuid) {
        return this.cooldownMap.containsKey(uuid) && this.cooldownMap.get(uuid) > System.currentTimeMillis();
    }

    public void placeOnCooldown(UUID uuid, long duration) {
        this.cooldownMap.put(uuid, (System.currentTimeMillis() + duration));
    }

}
