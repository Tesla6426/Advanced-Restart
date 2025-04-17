package net.txsla.advancedrestart.command.sub;

import net.txsla.advancedrestart.config;
import org.bukkit.command.CommandSender;

import java.util.List;

public class disable {
    public static void run(String[] args, CommandSender sender) {
        // prevents the plugin from restarting the server
        config.allow_restart = false;

        /*
        if (args.length > 2) {

        }else {
            config.disable_timer = 60;
        }
        */

        sender.sendMessage("Advanced Restart Disabled.");
    }
    public static List<String> tab(String[] args) { return null; }
}
