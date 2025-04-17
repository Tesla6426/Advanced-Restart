package net.txsla.advancedrestart.command.sub;

import net.txsla.advancedrestart.config;
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

        if (config.allow_restart) {}
    }
    public static List<String> tab(String[] args) {
        return null;
    }
}
