package net.txsla.advancedrestart;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
public class inactiveRestart implements Listener {
    Thread inactiveRestart;
    private final AdvancedRestart plugin;
    public inactiveRestart(AdvancedRestart plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        // if inactiveRestart is enabled, (re)start timer
        if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.broadcastMessage("[inactiveRestart.onPlayerJoin] Player Joined"); }
        if ( this.plugin.getConfig().getBoolean("inactiveRestart.enabled") ) {
            setTimer();
        }
    }
    public void setTimer() {
        if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.broadcastMessage("[inactiveRestart.SetTimer] top"); }
        int timer = this.plugin.getConfig().getInt("inactiveRestart.timer");
        if(inactiveRestart!= null) inactiveRestart.interrupt();

        inactiveRestart = new Thread(()->{
            if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.broadcastMessage("[inactiveRestart.SetTimer] thread.start"); }
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < timer * 1000.0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
            if (System.currentTimeMillis() - startTime >= timer * 1000.0) {
                if (Bukkit.getServer().getOnlinePlayers().isEmpty()) {
                    if (this.plugin.getConfig().getString("inactiveRestart.message") != null) { Bukkit.broadcastMessage( (this.plugin.getConfig().getString("inactiveRestart.message")).replace('&', '§') ); }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ignored) {

                    }
                    if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.broadcastMessage("[inactiveRestart.SetTimer] shutdown"); }
                    if (this.plugin.getConfig().getString("shutdownMessage") != null) { Bukkit.broadcastMessage( (this.plugin.getConfig().getString("shutdownMessage")).replace('&', '§') ); }
                    switch (this.plugin.getConfig().getInt("shutdownMethod"))
                    {
                        case 2:
                            Bukkit.spigot().restart();
                            break;
                        case 3:
                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop");
                            break;
                        case 4:
                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "restart");
                            break;
                        case 1:
                        default:
                            Bukkit.shutdown();
                            break;
                    }
                }else {
                    if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.broadcastMessage("[inactiveRestart.SetTimer] players online - no restart"); }
                    setTimer();
                }
            }
        });
        if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.broadcastMessage("[inactiveRestart.SetTimer] thread.end"); }
        inactiveRestart.start();
    }
}
