package net.txsla.advancedrestart;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class tpsRestart{
    private final AdvancedRestart plugin;
    Thread tpsRestart;
    public void sendMessage(String message) { for (Player p : Bukkit.getOnlinePlayers()) { p.sendMessage(message);} }
    public tpsRestart(AdvancedRestart plugin) {
        this.plugin = plugin;
        if (this.plugin.getConfig().getBoolean("dev")) { Bukkit.getServer().getConsoleSender().sendMessage("[tpsRestart.tpsRestart] top");}
        tpsManager();
    }
    public void tpsManager() {
        int minTPS = this.plugin.getConfig().getInt("lagRestart.lowTPS.minTPS");
        int maxChecks = this.plugin.getConfig().getInt("lagRestart.lowTPS.checks");
        boolean dev = this.plugin.getConfig().getBoolean("dev");
        tpsRestart = new Thread(()->
        {
            int checkFailed = 0;
            if (dev) {Bukkit.getServer().getConsoleSender().sendMessage("[tpsRestart.tpsManager] thread.start" + getTPS());}
            while (true) {
                if (getTPS() < minTPS) checkFailed++;
                if ((getTPS() > minTPS) && checkFailed > 0) checkFailed--;
                if (dev) { Bukkit.getServer().getConsoleSender().sendMessage("[tpsRestart.tpsManager] tps: " + getTPS() + "; checksfailed: " + checkFailed);}
                if (checkFailed > maxChecks) { stopServer(); break; }
                try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt();}
            }
        });
        if (dev) {Bukkit.getServer().getConsoleSender().sendMessage("[tpsRestart.tpsManager] thread.stop; tps: "+getTPS());}
        tpsRestart.start();
    }
    private void stopServer() {
        if (this.plugin.getConfig().getBoolean("dev")) sendMessage("[tpsRestart.tpsManager] server stopping; tps: "+getTPS());
        if (this.plugin.getConfig().getString("lagRestart.lowTPS.message") != null) sendMessage( (this.plugin.getConfig().getString("lagRestart.lowTPS.message")).replace('&', '§').replaceAll("%TPS", ""+getTPS() ));
        try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt();}
        if (this.plugin.getConfig().getString("shutdownMessage") != null) sendMessage( (this.plugin.getConfig().getString("shutdownMessage")).replace('&', '§') );
        switch (this.plugin.getConfig().getInt("shutdownMethod"))
        {
            case 2:
                Bukkit.spigot().restart();
                break;
            case 3:
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop");
                break;
            case 4: ;
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "restart");
                break;
            case 1:
            default:
                Bukkit.shutdown();
                break;
        }
    }
    public double getTPS() {
        return Bukkit.getServer().getTPS()[0];
    }
}