package net.txsla.advancedrestart;

import org.bukkit.Bukkit;

public class periodicRestart {
    Thread periodicRestart;
    private final AdvancedRestart plugin;
    public periodicRestart(AdvancedRestart plugin) {
        this.plugin = plugin;
        if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.broadcastMessage("[periodicRestart.periodicRestart] top"); }
        setTimer();
    }
    public void setTimer()
    {
        // this is prob the worst code I have ever written -- but it works
        long startTime = System.currentTimeMillis();
        if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.broadcastMessage("[periodicRestart.setTimer] startTime = " + startTime); }
        int duration = this.plugin.getConfig().getInt("periodicRestart.duration") * 60;
        periodicRestart = new Thread(()->
        {
            if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.broadcastMessage("[periodicRestart.setTimer] thread.start"); }
            while (System.currentTimeMillis() - startTime < duration * 1000.0) {
                try {Thread.sleep(1000);} catch (InterruptedException e) { break;}
            }
            if (System.currentTimeMillis() - startTime >= duration * 1000.0)
            {
                if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.broadcastMessage("[periodicRestart.setTimer] starting shutdown sequence"); }
                int runtime = (int) (System.currentTimeMillis() - startTime)/60000;
                if (this.plugin.getConfig().getString("periodicRestart.message") != null) { Bukkit.broadcastMessage((this.plugin.getConfig().getString("periodicRestart.message")).replace('&', '§').replaceAll("%RUNTIME", Integer.toString(runtime))); }
                //minuteWarn
                if (this.plugin.getConfig().getBoolean("restartWarning.minuteWarn.enabled"))
                {
                    if (this.plugin.getConfig().getBoolean("restartWarning.minuteWarn.countdown")) {
                        for (int i = this.plugin.getConfig().getInt("restartWarning.minuteWarn.minutes"); i > 0; i--) {
                            if (this.plugin.getConfig().getString("restartWarning.minuteWarn.message") != null) { Bukkit.broadcastMessage((this.plugin.getConfig().getString("restartWarning.minuteWarn.message")).replace('&', '§').replaceAll("%M", Integer.toString(i))); }
                            try { Thread.sleep(60000); } catch (InterruptedException e) { if (this.plugin.getConfig().getBoolean("dev")) Bukkit.broadcastMessage("[periodicRestart.setTimer] error?" + e); }
                        }
                    }else {
                        if (this.plugin.getConfig().getString("restartWarning.minuteWarn.message") != null) { Bukkit.broadcastMessage((this.plugin.getConfig().getString("restartWarning.minuteWarn.message")).replace('&', '§').replaceAll("%M", Integer.toString(this.plugin.getConfig().getInt("restartWarning.minuteWarn.minutes")) )); }
                        try { Thread.sleep((long) (this.plugin.getConfig().getInt("restartWarning.minuteWarn.minutes") * 60000.0) ); } catch (InterruptedException e) {  if (this.plugin.getConfig().getBoolean("dev")) Bukkit.broadcastMessage("[periodicRestart.setTimer] error?" + e); }
                    }
                }
                //secondsWarn
                if (this.plugin.getConfig().getBoolean("restartWarning.secondsWarn.enabled"))
                {
                    if (this.plugin.getConfig().getBoolean("restartWarning.secondsWarn.countdown")) {
                        for (int i = this.plugin.getConfig().getInt("restartWarning.secondsWarn.seconds"); i > 0; i--) {
                            if (this.plugin.getConfig().getString("restartWarning.secondsWarn.message") != null) { Bukkit.broadcastMessage((this.plugin.getConfig().getString("restartWarning.secondsWarn.message")).replace('&', '§').replaceAll("%S", Integer.toString(i))); }
                            try { Thread.sleep(1000); } catch (InterruptedException e) { if (this.plugin.getConfig().getBoolean("dev")) Bukkit.broadcastMessage("[periodicRestart.setTimer] error?" + e); }
                        }
                    }else {
                        if (this.plugin.getConfig().getString("restartWarning.secondsWarn.message") != null) { Bukkit.broadcastMessage((this.plugin.getConfig().getString("restartWarning.secondsWarn.message")).replace('&', '§').replaceAll("%S", Integer.toString(this.plugin.getConfig().getInt("restartWarning.secondsWarn.seconds")) )); }
                        try { Thread.sleep((long) this.plugin.getConfig().getInt("restartWarning.secondsWarn.seconds") * 1000); } catch (InterruptedException e) {  if (this.plugin.getConfig().getBoolean("dev")) Bukkit.broadcastMessage("[periodicRestart.setTimer] error?" + e); }
                    }
                }
                //shutdown
                if (this.plugin.getConfig().getString("shutdownMessage") != null) { Bukkit.broadcastMessage( (this.plugin.getConfig().getString("shutdownMessage")).replace('&', '§') ); }
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
        });
        if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.broadcastMessage("[periodicRestart.setTimer] thread.end"); }
        periodicRestart.start();
    }
}
