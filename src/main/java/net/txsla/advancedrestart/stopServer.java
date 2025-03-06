package net.txsla.advancedrestart;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class stopServer {

    public static void shutdown () {
        if (config.shutdownMessage != null) sendMessage(config.shutdownMessage);


        // execute shutdown commands here


        switch (config.shutdownMethod)
        {
            case 1:
            default:
                Bukkit.shutdown();
                break;
            case 2:
                Bukkit.spigot().restart();
                break;
        }

    }
    public static void startTimer (int data, String string) {

    }

    public static void sendMessage(String message) { for (Player p : Bukkit.getOnlinePlayers()) { p.sendMessage( format.string(message) );} }
}
