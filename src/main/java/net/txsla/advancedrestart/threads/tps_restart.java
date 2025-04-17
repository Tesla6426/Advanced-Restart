package net.txsla.advancedrestart.threads;

import net.txsla.advancedrestart.config;
import net.txsla.advancedrestart.format;
import org.bukkit.Bukkit;

public class tps_restart {
    public static int checks_failed;
    Thread tpsRestart;
    public tps_restart() {
        if (config.debug) { Bukkit.getServer().getConsoleSender().sendMessage("[tpsRestart.tpsRestart] top");}
        tpsManager();
    }
    public void tpsManager() {
        tpsRestart = new Thread(()->
        {
            checks_failed = 0;
            while (true) {
                // increment or decrement checks counter depending on tps
                // optimise later with if-else
                if (getTPS() < config.lagRestart_lowTPS_minTPS ) checks_failed++;
                if ((getTPS() > config.lagRestart_lowTPS_minTPS ) && checks_failed > 0) checks_failed--;

                // debug
                if (config.debug) { Bukkit.getServer().getConsoleSender().sendMessage("[tpsRestart.tpsManager] tps: " + getTPS() + "; checksfailed: " + checks_failed);}

                // restart server if counter exceeds threshold
                if (checks_failed > config.lagRestart_lowTPS_checks) { stopServer(); break; }

                // wait 3 seconds between checks
                try { Thread.sleep(3000); } catch (Exception e) { Thread.currentThread().interrupt();}
            }
        });
        tpsRestart.start();
    }
    private void stopServer() {
        // send tps restart message and stop server
        try {
        if (config.lagRestart_lowTPS_message != null)
            format.sendMessage( config.lagRestart_lowTPS_message.replaceAll("%TPS", ""+getTPS() ));
        } catch (Exception e) {
            if (config.debug) System.out.println(e);
        }
        stop_server.shutdown();
    }
    public double getTPS() {
        return Bukkit.getServer().getTPS()[0];
    }
}