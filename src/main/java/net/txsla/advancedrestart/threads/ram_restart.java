package net.txsla.advancedrestart.threads;

import net.txsla.advancedrestart.config;
import net.txsla.advancedrestart.format;
import org.bukkit.Bukkit;

public class ram_restart {
    public static int checks_failed;
    Runtime r = Runtime.getRuntime();
    Thread ramRestart;
    public ram_restart() {
        if (config.debug) Bukkit.getServer().getConsoleSender().sendMessage("[ramRestart.ramRestart] top");
        ramManager();
    }
    public void ramManager() {
        ramRestart = new Thread(()->
        {
            checks_failed = 0;
            while (true) {
                // increment or decrement checks counter depending on tps
                // optimise later with if-else
                if (getRAM() > config.lagRestart_lowMemory_maxMemUsage) checks_failed++;
                if ((getRAM() < config.lagRestart_lowMemory_maxMemUsage) && checks_failed > 0) checks_failed--;

                // debug
                if (config.debug) Bukkit.getServer().getConsoleSender().sendMessage("[ramRestart.ramManager] ram used: " + getRAM() + "; checksfailed: " + checks_failed);

                // stop server if checks have exceeded the threshold
                if (checks_failed > config.lagRestart_lowMemory_checks) { stopServer(); break; }

                // wait 3 seconds between checks
                try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt();}
            }
        });
        ramRestart.start();
    }
    private void stopServer() {
        // send low memory message and shut down server
        if (config.lagRestart_lowMemory_message != null)
           format.sendMessage( config.lagRestart_lowMemory_message.replaceAll("%MEM", ""+getRAM() ));
        stop_server.shutdown();
    }
    public double getRAM() {
        try {
            return (r.totalMemory() - r.freeMemory()) / 1048576F;
        } catch (Exception e) { System.out.println("[AdvancedRestart.ramRestart] Unable to read server RAM\n" + e); }
        return 34404;
    }
}
