package net.txsla.advancedrestart;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyFormat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class format {
    public static Component string (String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }
    public static void sendMessage(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(string(message));
    }
    public static void sendMessage(Component message) {
        for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(message);
    }
}
