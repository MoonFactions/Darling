package net.moon.darling.staffchat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class StaffChatHandler {

    private final Set<UUID> staffChatSet;

    public StaffChatHandler() {
        this.staffChatSet = new HashSet<>();
    }

    public void enableStaffChat(UUID uuid) {
        this.staffChatSet.add(uuid);
    }

    public void disableStaffChat(UUID uuid) {
        this.staffChatSet.remove(uuid);
    }

    public boolean hasStaffChatEnabled(UUID uuid) {
        return this.staffChatSet.contains(uuid);
    }

}
