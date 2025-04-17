package net.txsla.advancedrestart.command.sub;

import net.txsla.advancedrestart.config;
import org.bukkit.command.CommandSender;

import java.util.List;

public class enable {
    public static void run(String[] args, CommandSender sender) {
        // allows the plugin to restart the server
        config.allow_restart = true;
        sender.sendMessage("Advanced Restart Enabled.");
    }
    public static List<String> tab(String[] args) {
        return null;
    }
}
