package net.txsla.advancedrestart;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ramRestart{
    private final AdvancedRestart plugin;
    Runtime r = Runtime.getRuntime();
    public void sendMessage(String message) { for (Player p : Bukkit.getOnlinePlayers()) { p.sendMessage(message);} }
    Thread ramRestart;
    public ramRestart(AdvancedRestart plugin) {
        this.plugin = plugin;
        if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.getServer().getConsoleSender().sendMessage("[ramRestart.ramRestart] top");}
        ramManager();
    }
    public void ramManager() {
        int maxMem = this.plugin.getConfig().getInt("lagRestart.lowMemory.maxMemUsage");
        int maxChecks = this.plugin.getConfig().getInt("lagRestart.lowMemory.checks");
        boolean dev = this.plugin.getConfig().getBoolean("dev");
        ramRestart = new Thread(()->
        {
            int checkFailed = 0;
            if (dev) {Bukkit.getServer().getConsoleSender().sendMessage("[ramRestart.ramManager] thread.start" + getRAM());}
            while (true) {
                if (getRAM() > maxMem) checkFailed++;
                if ((getRAM() < maxMem) && checkFailed > 0) checkFailed--;
                if (dev) { Bukkit.getServer().getConsoleSender().sendMessage("[ramRestart.ramManager] ram used: " + getRAM() + "; checksfailed: " + checkFailed);}
                if (checkFailed > maxChecks) { stopServer(); break; }
                try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt();}
            }
        });
        if (dev) {Bukkit.getServer().getConsoleSender().sendMessage("[ramRestart.ramManager] thread.stop; ram used: "+getRAM());}
        ramRestart.start();
    }
    private void stopServer() {
        if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.getServer().getConsoleSender().sendMessage("[ramRestart.ramManager] server stopping; ram used: "+getRAM());}
        if (this.plugin.getConfig().getString("lagRestart.lowMemory.message") != null) sendMessage( (this.plugin.getConfig().getString("lagRestart.lowMemory.message")).replace('&', '§').replaceAll("%MEM", ""+getRAM() ));
        try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt();}
        if (this.plugin.getConfig().getString("shutdownMessage") != null) sendMessage( (this.plugin.getConfig().getString("shutdownMessage")).replace('&', '§'));
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
    public double getRAM() {
        double RAM;
        try { RAM = (r.totalMemory() - r.freeMemory()) / 1048576F;
        } catch (IllegalArgumentException e) { RAM = 34404; }
        return RAM;
    }
}
