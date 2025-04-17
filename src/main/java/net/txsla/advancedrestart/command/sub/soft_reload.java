package net.txsla.advancedrestart.command.sub;

import org.bukkit.command.CommandSender;

import java.util.List;

public class soft_reload {
    public static void run(String[] args, CommandSender sender) {
        // reloads configs from file
        if (net.txsla.advancedrestart.config.load()) {
            sender.sendMessage("Config settings reloaded.");
        }else {
            sender.sendMessage("Failed to load configs!");
        }
    }
    public static List<String> tab(String[] args) {
        return null;
    }
}
