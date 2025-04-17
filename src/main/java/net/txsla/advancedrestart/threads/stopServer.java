package net.txsla.advancedrestart.threads;

import net.txsla.advancedrestart.config;
import net.txsla.advancedrestart.format;
import org.bukkit.Bukkit;

public class stopServer {

    public static void shutdown () {

        // do not shut down server if user has disabled the plugin
        if (!config.allow_restart) {
            System.out.println("[Advanced Restart] SHUTDOWN CANCELLED - restarts cancelled for " + ((int) config.disable_timer) + " minutes by an admin");
            return;
        }

        // send shutdown message
        if (config.shutdownMessage != null) format.sendMessage(config.shutdownMessage);

        // execute shutdown commands (as console)
        if (config.shutdownCommands != null) {
            // execute shutdown commands here
        }

        // stop server
        switch (config.shutdownMethod)
        {
            case 1:
            default:
                Bukkit.shutdown();
                break;
            case 2:
                Bukkit.spigot().restart();
                break;
            case 3:
                // do nothing
                break;
        }
    }


    public static void send_message_and_sleep_recursively(int timer, int cycles, String message) {
        // sends a message and sleeps recursively
        for (int i = cycles; i > 0; i--) {
            // this is peak software development 
            send_message_and_sleep(
                    timer,
                    message.replaceAll("%[SM]", ""+i )
            );
        }
    }
    public static void send_message_and_sleep(int timer, String message) {
        // sends a message and sleeps
        if (message != null)
            format.sendMessage(message);
        try { Thread.sleep(timer); } catch (Exception e) { if (config.debug) Bukkit.getServer().getConsoleSender().sendMessage("[periodicRestart.setTimer] error?" + e); }
    }
}
