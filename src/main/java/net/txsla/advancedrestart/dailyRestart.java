package net.txsla.advancedrestart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

public class dailyRestart {
    Thread dailyRestart;
    private final AdvancedRestart plugin;
    public void sendMessage(String message) { for (Player p : Bukkit.getOnlinePlayers()) { p.sendMessage(message);} }
    private String getTime() { return (DateTimeFormatter.ofPattern("HH:mm")).format(LocalDateTime.now()); }
    private String getDay() { return LocalDateTime.now().format(DateTimeFormatter.ofPattern("E")).toUpperCase(); }
    public String[][] schedule;
    public dailyRestart(AdvancedRestart plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getConsoleSender().sendMessage("[AdvancedRestart] §4SYNC §cServer day/time: "+getDay()+" "+getTime() );
        parseSchedule();
        scheduleManager();
    }
    private void scheduleManager() {
        boolean dev = this.plugin.getConfig().getBoolean("dev");
        if(dev){for(int i=0;schedule.length>i;i++){try{Bukkit.getServer().getConsoleSender().sendMessage("[dailyRestart.scheduleManager] schedule list ["+i+"] :"+schedule[i][0]+" "+schedule[i][1] );}catch(Exception e){ break;}}}
        dailyRestart = new Thread(()->
        {
            boolean restart = false;
            while (!restart) {
                try {Thread.sleep(15000);} catch (Exception e) {Thread.currentThread().interrupt();}
                for (String[] strings : schedule) {
                    if (strings[0].matches(getDay()+"|ALL")&&strings[1].matches(getTime())) {restart = true; break; }
                    if(dev){Bukkit.getServer().getConsoleSender().sendMessage("[dailyRestart.scheduleManager.thread] checking time: "+strings[0]+" "+strings[1]);}
                }
            }
            stopServer();
        });
        dailyRestart.start();
    }

    private void parseSchedule() {
        List<String> uf = this.plugin.getConfig().getStringList("scheduledRestart.schedule");
        schedule = new String[uf.size()][2];
        for (int i = 0; i < uf.size(); i++)
        {
            schedule[i][0] = uf.get(i).toUpperCase().replaceAll("-.*","");
            schedule[i][1] = uf.get(i).toUpperCase().replaceAll("^[^-]*-", "");
        }
    }
    private void stopServer() {
        if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.getServer().getConsoleSender().sendMessage("[dailyRestart.scheduleManager] server stopping;");}
        boolean dev = this.plugin.getConfig().getBoolean("dev");
        int minutes = this.plugin.getConfig().getInt("restartWarning.minuteWarn.minutes");
        int seconds = this.plugin.getConfig().getInt("restartWarning.secondsWarn.seconds");
        String periodicMessage = this.plugin.getConfig().getString("periodicRestart.message");
        String minuteMessage = this.plugin.getConfig().getString("restartWarning.minuteWarn.message");
        boolean minuteEnabled = this.plugin.getConfig().getBoolean("restartWarning.minuteWarn.enabled");
        String secondsMesssage = this.plugin.getConfig().getString("restartWarning.secondsWarn.message");
        boolean secondsEnabled = this.plugin.getConfig().getBoolean("restartWarning.secondsWarn.enabled");
        boolean minuteCountdown = this.plugin.getConfig().getBoolean("restartWarning.minuteWarn.countdown");
        boolean secondsCountdown = this.plugin.getConfig().getBoolean("restartWarning.secondsWarn.countdown");
        if (this.plugin.getConfig().getString("scheduledRestart.message") != null) { sendMessage( (this.plugin.getConfig().getString("scheduledRestart.message")).replace('&', '§') ); }
        //minuteWarn
        if (minuteEnabled)
        {
            if (minuteCountdown) {
                for (int i = minutes; i > 0; i--) {
                    if (minuteMessage != null) { sendMessage((minuteMessage).replace('&', '§').replaceAll("%M", Integer.toString(i))); }
                    try { Thread.sleep(60000); } catch (InterruptedException ignore) {  }
                }
            }else {
                if (minuteMessage != null) { sendMessage((minuteMessage).replace('&', '§').replaceAll("%M", Integer.toString(minutes) )); }
                try { Thread.sleep((long) (minutes * 60000.0) ); } catch (InterruptedException ignore) {  }
            }
        }
        //secondsWarn
        if (secondsEnabled)
        {
            if (secondsCountdown) {
                for (int i = seconds; i > 0; i--) {
                    if (secondsMesssage != null) { sendMessage((secondsMesssage).replace('&', '§').replaceAll("%S", Integer.toString(i))); }
                    try { Thread.sleep(1000); } catch (InterruptedException ignore) { }
                }
            }else {
                if (secondsMesssage != null) sendMessage((secondsMesssage).replace('&', '§').replaceAll("%S", Integer.toString(seconds) ));
                try { Thread.sleep((long) seconds * 1000); } catch (InterruptedException ignore) { }
            }
        }
        //shutdown
        if (this.plugin.getConfig().getString("shutdownMessage") != null) { sendMessage( (this.plugin.getConfig().getString("shutdownMessage")).replace('&', '§') ); }
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
}
