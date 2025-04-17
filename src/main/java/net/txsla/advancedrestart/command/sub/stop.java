package net.txsla.advancedrestart.command.sub;

import net.txsla.advancedrestart.config;
import net.txsla.advancedrestart.threads.stop_server;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class stop {
    public static void run(String[] args, CommandSender sender) {
        // allows the plugin to restart the server

        // Force Restart
        if (args.length > 1) {
            if (args[1].equals("force")) {
                sender.sendMessage("Stopping server.");
                stop_server.shutdown();
            }
        }

        // Restart
        if (config.allow_restart) {
            sender.sendMessage("Stopping server.");
            stop_server.shutdown();
        }else {
            sender.sendMessage("Server restarting has been disabled by an admin. Use /ar restart force to shutdown anyways.");
        }
    }
    public static List<String> tab(String[] args) {
        List<String> list = new ArrayList();
        list.add("force");
        return list;
    }
    public static void scheduleRestart() {
        // soon?
    }
}
