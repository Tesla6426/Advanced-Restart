package net.txsla.advancedrestart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class periodicRestart {
    Thread periodicRestart;
    private final AdvancedRestart plugin;
    public void sendMessage(String message) { for (Player p : Bukkit.getOnlinePlayers()) { p.sendMessage(message);} }
    public periodicRestart(AdvancedRestart plugin) {
        this.plugin = plugin;
        if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.broadcastMessage("[periodicRestart.periodicRestart] top"); }
        setTimer();
    }
    public void setTimer()
    {
        long startTime = System.currentTimeMillis();
        boolean minuteCountdown = this.plugin.getConfig().getBoolean("restartWarning.minuteWarn.countdown");
        boolean secondsCountdown = this.plugin.getConfig().getBoolean("restartWarning.secondsWarn.countdown");
        boolean minuteEnabled = this.plugin.getConfig().getBoolean("restartWarning.minuteWarn.enabled");
        boolean secondsEnabled = this.plugin.getConfig().getBoolean("restartWarning.secondsWarn.enabled");
        String secondsMesssage = this.plugin.getConfig().getString("restartWarning.secondsWarn.message");
        String minuteMessage = this.plugin.getConfig().getString("restartWarning.minuteWarn.message");
        String periodicMessage = this.plugin.getConfig().getString("periodicRestart.message");
        int seconds = this.plugin.getConfig().getInt("restartWarning.secondsWarn.seconds");
        int minutes = this.plugin.getConfig().getInt("restartWarning.minuteWarn.minutes");
        int duration = this.plugin.getConfig().getInt("periodicRestart.duration") * 60;

        boolean dev = this.plugin.getConfig().getBoolean("dev");


        if (dev) {Bukkit.getServer().getConsoleSender().sendMessage("[periodicRestart.setTimer] startTime = " + startTime); }
        periodicRestart = new Thread(()->
        {
            if (dev) {Bukkit.getServer().getConsoleSender().sendMessage("[periodicRestart.setTimer] thread.start"); }
            while (System.currentTimeMillis() - startTime < duration * 1000.0) { try {Thread.sleep(1000);}catch(InterruptedException e) {Thread.currentThread().interrupt();} }
            if (System.currentTimeMillis() - startTime >= duration * 1000.0)
            {
                if (dev) {Bukkit.getServer().getConsoleSender().sendMessage("[periodicRestart.setTimer] starting shutdown sequence"); }
                int runtime = (int) (System.currentTimeMillis() - startTime)/60000;
                if (periodicMessage != null) {sendMessage(((periodicMessage).replace('&', '§')).replaceAll("%RUNTIME", Integer.toString(runtime))); }
                //minuteWarn
                if (minuteEnabled)
                {
                    if (minuteCountdown) {
                        for (int i = minutes; i > 0; i--) {
                            if (minuteMessage != null) sendMessage((minuteMessage).replace('&', '§').replaceAll("%M", Integer.toString(i)));
                            try { Thread.sleep(60000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                        }
                    }else {
                        if (minuteMessage != null) sendMessage((minuteMessage).replace('&', '§').replaceAll("%M", Integer.toString(minutes) ));
                        try { Thread.sleep((long) (minutes * 60000.0) ); } catch (InterruptedException e) {  if (dev) Bukkit.getServer().getConsoleSender().sendMessage("[periodicRestart.setTimer] error?" + e); }
                    }
                }
                //secondsWarn
                if (secondsEnabled)
                {
                    if (secondsCountdown) {
                        for (int i = seconds; i > 0; i--) {
                            if (secondsMesssage != null) sendMessage((secondsMesssage).replace('&', '§').replaceAll("%S", Integer.toString(i)));
                            try { Thread.sleep(1000); } catch (InterruptedException e) { if (dev) Bukkit.getServer().getConsoleSender().sendMessage("[periodicRestart.setTimer] error?" + e); }
                        }
                    }else {
                        if (secondsMesssage != null) sendMessage((secondsMesssage).replace('&', '§').replaceAll("%S", Integer.toString(seconds)));
                        try { Thread.sleep((long) seconds * 1000); } catch (InterruptedException e) {  if (dev) Bukkit.getServer().getConsoleSender().sendMessage("[periodicRestart.setTimer] error?" + e); }
                    }
                }
                //shutdown
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
        });
        if (dev) {Bukkit.getServer().getConsoleSender().sendMessage("[periodicRestart.setTimer] thread.end"); }
        periodicRestart.start();
    }
}
