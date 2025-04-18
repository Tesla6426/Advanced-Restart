package net.txsla.advancedrestart.threads;
import net.txsla.advancedrestart.config;
import net.txsla.advancedrestart.format;
import org.bukkit.Bukkit;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

public class schedule_restart {
    Thread dailyRestart;
    private static String getTime() { return (DateTimeFormatter.ofPattern("HH:mm")).format(LocalDateTime.now()); }
    private static String getDay() { return LocalDateTime.now().format(DateTimeFormatter.ofPattern("E")).toUpperCase(); }
    public static String[][] schedule;
    public schedule_restart() {
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
                    // skip null values
                    if (strings[0] != null) {
                        // restart if a match is found
                        if (strings[0].matches(getDay() + "|ALL") && strings[1].matches(getTime())) {
                            restart = true; break;
                        }
                        if (config.debug) Bukkit.getServer().getConsoleSender().sendMessage("[dailyRestart.scheduleManager.thread] checking time: " + strings[0] + " " + strings[1]);
                    }
                }
            }
            stopServer();
        });
        dailyRestart.start();
    }

    private void parseSchedule() {
        // I can prob write this more efficiently later - remind me
        List<String> uf = config.scheduledRestart_schedule;
        schedule = new String[uf.size()][2];
        for (int i = 0; i < uf.size(); i++)
        {
            // verify input to pass fuzz
            if (uf.get(i).matches("^[A-Za-z]{3}-[0-2][0-9]:[0-5][0-9]$")) {
                // parse schedule into list
                schedule[i][0] = uf.get(i).toUpperCase().replaceAll("-.*", "");
                schedule[i][1] = uf.get(i).toUpperCase().replaceAll("^[^-]*-", "");
            }else {
                System.out.println("[Advanced Restart] Error parsing " + uf.get(i) + ". Check Schedule format in config");
                // skip this element (checker can handle a null value)
            }
        }
    }
    private void stopServer() {
        if (config.debug) {System.out.println("[dailyRestart.scheduleManager] server stopping;");}

        try {
            format.sendMessage(config.scheduledRestart_message);
        } catch (Exception e) {
            if (config.debug) System.out.println(e);
        }

        //minuteWarn
        if (config.restartWarning_minuteWarn_enabled)
        {
            if (config.restartWarning_minuteWarn_countdown) {
                stop_server.send_message_and_sleep_recursively(
                        60000,
                        config.restartWarning_minuteWarn_minutes,
                        config.restartWarning_minuteWarn_message
                );
            }
            else {
                try {
                    stop_server.send_message_and_sleep(
                            config.restartWarning_minuteWarn_minutes * 60000,
                            config.restartWarning_minuteWarn_message.replaceAll("%M", "" + config.restartWarning_minuteWarn_minutes)
                    );
                } catch (Exception e) {if (config.debug) System.out.println(e);}
            }
        }
        //secondsWarn
        if (config.restartWarning_secondsWarn_enabled)
        {
            if (config.restartWarning_secondsWarn_countdown) {
                stop_server.send_message_and_sleep_recursively(
                        1000,
                        config.restartWarning_secondsWarn_seconds,
                        config.restartWarning_secondsWarn_message
                        );
            }
            else {
                try {
                    stop_server.send_message_and_sleep(
                            config.restartWarning_secondsWarn_seconds * 1000,
                            config.restartWarning_secondsWarn_message.replaceAll("%S", "" + config.restartWarning_secondsWarn_seconds)
                    );
                } catch (Exception e) {if (config.debug) System.out.println(e);}
            }
        }

        stop_server.shutdown();
    }
}
