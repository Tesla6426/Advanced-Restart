package net.txsla.advancedrestart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class periodicRestart {
    Thread periodicRestart;
    public void sendMessage(String message) { for (Player p : Bukkit.getOnlinePlayers()) { p.sendMessage(message);} }
    public periodicRestart() {
        setTimer();
    }
    public void setTimer()
    {
        double startTime = System.currentTimeMillis();

        if (config.debug) Bukkit.getServer().getConsoleSender().sendMessage("[periodicRestart.setTimer] startTime = " + startTime);

        periodicRestart = new Thread(()->
        {

            // sleep until the current time is greater than the startTime - ( current_time + duration )
            while (System.currentTimeMillis() - startTime < (config.periodicRestart_duration * 60000)) {
                try {Thread.sleep(1000);} catch(InterruptedException e) {Thread.currentThread().interrupt();}
            }

            // is this if statement unnecessarily redundant?
            if (System.currentTimeMillis() - startTime >= (config.periodicRestart_duration * 60000))
            {
                if (config.debug) Bukkit.getServer().getConsoleSender().sendMessage("[periodicRestart.setTimer] starting shutdown sequence");

                // send restart message and replace placeholder with runtime
                if (config.periodicRestart_message != null)
                    format.sendMessage(config.periodicRestart_message.replaceAll("%RUNTIME", ""+( (int) (System.currentTimeMillis() - startTime)/60000)));

                //minuteWarn
                if (config.restartWarning_minuteWarn_enabled)
                {
                    // if countdown is enabled then run countdown
                    if (config.restartWarning_minuteWarn_countdown) {
                        stopServer.send_message_and_sleep_recursively(
                                60000,
                                config.restartWarning_minuteWarn_minutes,
                                config.restartWarning_minuteWarn_message
                        );
                    }
                    else {
                        // send message and sleep
                        stopServer.send_message_and_sleep(
                                config.restartWarning_minuteWarn_minutes*60000,
                                config.restartWarning_minuteWarn_message.replaceAll("%M", ""+config.restartWarning_minuteWarn_minutes)
                        );
                    }
                }

                // secondsWarn
                if (config.restartWarning_secondsWarn_enabled)
                {
                    if (config.restartWarning_secondsWarn_countdown) {
                        // send message every second if countdown is enabled
                        stopServer.send_message_and_sleep_recursively(
                                1000,
                                config.restartWarning_secondsWarn_seconds,
                                config.restartWarning_secondsWarn_message
                        );
                    }
                    else {
                        // send message and sleep
                        stopServer.send_message_and_sleep(
                                config.restartWarning_secondsWarn_seconds * 1000,
                                config.restartWarning_secondsWarn_message.replaceAll("%S", ""+config.restartWarning_secondsWarn_seconds)
                        );
                    }
                }
                stopServer.shutdown();
            }
        });
        periodicRestart.start();
    }
}
