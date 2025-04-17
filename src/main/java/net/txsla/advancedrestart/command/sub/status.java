package net.txsla.advancedrestart.command.sub;

import net.txsla.advancedrestart.config;
import net.txsla.advancedrestart.threads.periodic_restart;
import net.txsla.advancedrestart.threads.ram_restart;
import net.txsla.advancedrestart.threads.schedule_restart;
import net.txsla.advancedrestart.threads.tps_restart;
import org.bukkit.command.CommandSender;

import java.util.List;

public class status {
    public static void run(String[] args, CommandSender sender) {
        // retrieves information about the plugin's status
        String out = "§5[ Advanced Restart ]\n§d  status:";
        /*
        {
        \n\n
        [ Advanced Restart ]                            < purple
         status :                                       < light_purple
            disabled (for 59 minutes)                   < enabled=green / disabled=red + minutes
            dailyRestart                                < enabled=green / disabled=red
                \<print schedule list>                  + blue , only shown if enabled
            periodicRestart                             < enabled=green / disabled=red
                \minutes-countdown: <minutes>           + blue , only shown if enabled
            inactiveRestart                             < enabled=green / disabled=red
                \timer: <seconds>                       + blue , only shown if enabled
            lowTPSRestart                               < enabled=green / disabled=red
                |minTPS: <tps>                          + blue , only shown if enabled
                \checks-failed: <int>                   + blue , only shown if enabled
            lowMemory Restart                           < enabled=green / disabled=red
                |minTPS: <tps>                          + blue , only shown if enabled
                \checks-failed: <int>                   + blue , only shown if enabled
        \n\n
        }
        * */

        // whether the plugin is enabled or not
        if (config.allow_restart) {
            out += "\n§a    enabled";
        }
        else {
            out += "\n§c    disabled";
        }

        // scheduled restart information
        if (config.scheduledRestart_enabled) {
            out += "\n§a    scheduledRestart";
            for (String[] time : schedule_restart.schedule) {
                out += "\n§9      " + time[0] + "-" + time[1];
            }
            // print schedule
        }
        else {
            out += "\n§8    scheduledRestart";
        }

        // periodic restart info
        if (config.periodicRestart_enabled) {
            out += "\n§a    periodicRestart";
            out += "\n§9      timer: " + config.periodicRestart_duration;
            out += "\n§9      min_remaining: " + periodic_restart.remaining();
        }
        else {
            out += "\n§8    periodicRestart";
        }

        // inactive restart info
        if (config.inactiveRestart_enabled) {
            out += "\n§a    inactiveRestart";
            out += "\n§9      timer: " + config.inactiveRestart_timer ;
        }
        else {
            out += "\n§8    inactiveRestart";
        }

        // inactive restart info
        if (config.lagRestart_lowTPS_enabled) {
            out += "\n§a    lowTPSRestart";
            out += "\n§9      min_tps: " + config.lagRestart_lowTPS_minTPS;
            out += "\n§9      checks_failed: " + tps_restart.checks_failed;
        }
        else {
            out += "\n§8    lowTPSRestart";
        }

        // inactive restart info
        if (config.lagRestart_lowMemory_enabled) {
            out += "\n§a    lowMemoryRestart";
            out += "\n§9      max_mem_usage: " + config.lagRestart_lowMemory_maxMemUsage;
            out += "\n§9      checks_failed: " + ram_restart.checks_failed;
        }
        else {
            out += "\n§8    lowMemoryRestart";
        }


        sender.sendMessage(out);
    }
    public static List<String> tab(String[] args) { return null; }
}
