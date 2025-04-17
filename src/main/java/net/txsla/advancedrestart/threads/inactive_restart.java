package net.txsla.advancedrestart.threads;
import net.txsla.advancedrestart.config;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class inactive_restart implements Listener {
    Thread inactiveRestart;

    public inactive_restart() { }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        // if inactiveRestart is enabled, (re)start timer
        if (config.debug) {Bukkit.getServer().getConsoleSender().sendMessage("[inactiveRestart.onPlayerJoin] Player Joined"); }
            setTimer();
    }
    public void setTimer() {

        if(inactiveRestart!=null) inactiveRestart.interrupt();

        inactiveRestart = new Thread(()->{
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < config.inactiveRestart_timer * 1000.0) {
                try { Thread.sleep(1000);} catch (InterruptedException ignore) {}
            }
            if (System.currentTimeMillis() - startTime >= config.inactiveRestart_timer * 1000.0) {
                if (Bukkit.getServer().getOnlinePlayers().isEmpty()) {

                    stop_server.send_message_and_sleep(3000, config.inactiveRestart_message);
                    stop_server.shutdown();

                }else {
                    if (config.debug) {Bukkit.getServer().getConsoleSender().sendMessage("[inactiveRestart.SetTimer] players online - no restart"); }
                    setTimer();
                }
            }
        });
        inactiveRestart.start();
    }
}
