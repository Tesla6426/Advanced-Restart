package net.txsla.advancedrestart.command.sub;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class config {
    public static void run(String[] args, CommandSender sender) {
        // easily read the configuration file without needing to access the server backend
        if (args.length < 2) {return;}

        switch (args[1]) {
            case "read":
                if (args.length >= 3) {
                    try {
                        sender.sendMessage("Config value " + args[2] + " is set to " + net.txsla.advancedrestart.config.plugin.getConfig().get(args[2]));
                    } catch (Exception e) {
                        sender.sendMessage("Config does not exist.");
                    }
                }
                else {
                    sender.sendMessage("You must reload or select a value to read.");
                }
                break;
            case "reload":
                if (net.txsla.advancedrestart.config.load()) {
                    sender.sendMessage("Config settings reloaded.");
                }else {
                    sender.sendMessage("Failed to load configs!");
                }
            default:
                break;
        }
    }
    public static List<String> tab(String[] args) {

        if (args.length == 2) {
            List<String> list = new ArrayList<>();
            list.add("read");
            list.add("reload"); // config reload and /ar soft-reload are the same thing
            return list;
        }
        // return list of configs
        if (args.length == 3) {
            return config_options();
        }
        return null;
    }
    public static List<String> config_options() {
        // list of every config
        List<String> list = new ArrayList<>();

        list.add("scheduledRestart.enabled");
        list.add("scheduledRestart.schedule");
        list.add("scheduledRestart.message");

        list.add("periodicRestart.enabled");
        list.add("periodicRestart.duration");
        list.add("periodicRestart.message");

        list.add("restartWarning.secondsWarn.enabled");
        list.add("restartWarning.secondsWarn.seconds");
        list.add("restartWarning.secondsWarn.countdown");
        list.add("restartWarning.secondsWarn.message");

        list.add("restartWarning.minuteWarn.enabled");
        list.add("restartWarning.minuteWarn.minutes");
        list.add("restartWarning.minuteWarn.countdown");
        list.add("restartWarning.minuteWarn.message");

        list.add("inactiveRestart.enabled");
        list.add("inactiveRestart.timer");
        list.add("inactiveRestart.message");

        list.add("lagRestart.lowTPS.enabled");
        list.add("lagRestart.lowTPS.minTPS");
        list.add("lagRestart.lowTPS.checks");
        list.add("lagRestart.lowTPS.message");

        list.add("lagRestart.lowMemory.enabled");
        list.add("lagRestart.lowMemory.maxMemUsage");
        list.add("lagRestart_lowMemory_checks");
        list.add("lagRestart_lowMemory_message");

        list.add("shutdownMethod");
        list.add("shutdownMessage");

        list.add("shutdownCommands");

        list.add("debug");

        return list;
    }
}
