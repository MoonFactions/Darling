package net.moon.darling.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CC {

    public String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public List<String> chat(List<String> s) {
        return s.stream().map(CC::chat).collect(Collectors.toList());
    }

}
