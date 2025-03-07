package net.txsla.advancedrestart;

import org.bukkit.Bukkit;

public class tpsRestart{
    int checks_failed;
    Thread tpsRestart;
    public tpsRestart() {
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
        if (config.lagRestart_lowTPS_message != null)
            format.sendMessage( config.lagRestart_lowMemory_message.replaceAll("%TPS", ""+getTPS() ));
        stopServer.shutdown();
    }
    public double getTPS() {
        return Bukkit.getServer().getTPS()[0];
    }
}