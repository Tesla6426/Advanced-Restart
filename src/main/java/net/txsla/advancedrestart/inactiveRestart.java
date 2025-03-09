package net.txsla.advancedrestart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
public class inactiveRestart implements Listener {
    Thread inactiveRestart;

    public inactiveRestart() { }
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

                    stopServer.send_message_and_sleep(3000, config.inactiveRestart_message);
                    stopServer.shutdown();

                }else {
                    if (config.debug) {Bukkit.getServer().getConsoleSender().sendMessage("[inactiveRestart.SetTimer] players online - no restart"); }
                    setTimer();
                }
            }
        });
        inactiveRestart.start();
    }
}
