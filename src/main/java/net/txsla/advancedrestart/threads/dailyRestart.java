package net.txsla.advancedrestart.threads;
import net.txsla.advancedrestart.config;
import net.txsla.advancedrestart.format;
import org.bukkit.Bukkit;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

public class dailyRestart {
    Thread dailyRestart;
    private String getTime() { return (DateTimeFormatter.ofPattern("HH:mm")).format(LocalDateTime.now()); }
    private String getDay() { return LocalDateTime.now().format(DateTimeFormatter.ofPattern("E")).toUpperCase(); }
    public String[][] schedule;
    public dailyRestart() {
        Bukkit.getServer().getConsoleSender().sendMessage("[AdvancedRestart] §4SYNC §cServer day/time: "+getDay()+" "+getTime() );
        parseSchedule();
        scheduleManager();
    }
    private void scheduleManager() {

        // print schedule list to console if debug is enabled
        if(config.debug) {for(int i = 0; schedule.length>i; i++){try{Bukkit.getServer().getConsoleSender().sendMessage("[dailyRestart.scheduleManager] schedule list ["+i+"] :"+schedule[i][0]+" "+schedule[i][1] );}catch(Exception e){ break;}}}

        dailyRestart = new Thread(()->
        {
            boolean restart = false;
            while (!restart) {
                try {Thread.sleep(15000);} catch (Exception e) {Thread.currentThread().interrupt();}
                for (String[] strings : schedule) {
                    if (strings[0].matches(getDay()+"|ALL")&&strings[1].matches(getTime())) {restart = true; break; }
                    if(config.debug){Bukkit.getServer().getConsoleSender().sendMessage("[dailyRestart.scheduleManager.thread] checking time: "+strings[0]+" "+strings[1]);}
                }
            }
            stopServer();
        });
        dailyRestart.start();
    }

    private void parseSchedule() {

        List<String> uf = config.scheduledRestart_schedule;
        schedule = new String[uf.size()][2];
        for (int i = 0; i < uf.size(); i++)
        {
            schedule[i][0] = uf.get(i).toUpperCase().replaceAll("-.*","");
            schedule[i][1] = uf.get(i).toUpperCase().replaceAll("^[^-]*-", "");
        }
    }
    private void stopServer() {
        if (config.debug) {Bukkit.getServer().getConsoleSender().sendMessage("[dailyRestart.scheduleManager] server stopping;");}

        format.sendMessage(config.scheduledRestart_message);

        //minuteWarn
        if (config.restartWarning_minuteWarn_enabled)
        {
            if (config.restartWarning_minuteWarn_countdown) {
                stopServer.send_message_and_sleep_recursively(
                        60000,
                        config.restartWarning_minuteWarn_minutes,
                        config.restartWarning_minuteWarn_message
                );
            }
            else {
                stopServer.send_message_and_sleep(
                        config.restartWarning_minuteWarn_minutes * 60000,
                        config.restartWarning_minuteWarn_message.replaceAll("%M", ""+config.restartWarning_minuteWarn_minutes)
                );
            }
        }
        //secondsWarn
        if (config.restartWarning_secondsWarn_enabled)
        {
            if (config.restartWarning_secondsWarn_countdown) {
                stopServer.send_message_and_sleep_recursively(
                        1000,
                        config.restartWarning_secondsWarn_seconds,
                        config.restartWarning_secondsWarn_message
                        );
            }
            else {
                stopServer.send_message_and_sleep(
                        config.restartWarning_secondsWarn_seconds * 1000 ,
                        config.restartWarning_secondsWarn_message.replaceAll("%S", ""+config.restartWarning_secondsWarn_seconds)
                );
            }
        }

        stopServer.shutdown();
    }
}
