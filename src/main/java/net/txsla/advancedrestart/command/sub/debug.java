package net.txsla.advancedrestart.command.sub;

import net.txsla.advancedrestart.config;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class debug {
    public static void run(String[] args, CommandSender sender) {
        // enables debug without needing to update the config
        switch (args[1]) {
            case "enable":
                config.debug = true;
                sender.sendMessage("Debug enabled.");
                break;
            case "disable":
                config.debug = false;
                sender.sendMessage("Debug disabled.");
                break;
            case "query":
            default:
                sender.sendMessage("Debug is set to " + config.debug);
                break;
        }
    }
    public static List<String> tab(String[] args) {
        if (args.length > 1) {
         List<String> list = new ArrayList<>();
         list.add("enable");
         list.add("disable");
         list.add("query");
         return list;
        }
        return null;
    }
}
