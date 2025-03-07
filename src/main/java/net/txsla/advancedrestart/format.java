package net.txsla.advancedrestart;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class format {
    public static Component string (String string) {
        return MiniMessage.miniMessage().deserialize(
                // ampersand replacements will remain so that both text formatting methods work simultaneously
                // \& functionality will be added soon (remind me to update the regex)
                string.replaceAll("&", "§")
        );
    }
    public static void sendMessage(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(string(message));
    }
    public static void sendMessage(Component message) {
        for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(message);
    }
}
