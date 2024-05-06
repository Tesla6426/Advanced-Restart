package net.txsla.advancedrestart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
public class inactiveRestart implements Listener {
    Thread inactiveRestart;
    private final AdvancedRestart plugin;
    public inactiveRestart(AdvancedRestart plugin) {this.plugin = plugin;}
    public void sendMessage(String message) { for (Player p : Bukkit.getOnlinePlayers()) { p.sendMessage(message);} Bukkit.getServer().getConsoleSender().sendMessage(message);}
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        // if inactiveRestart is enabled, (re)start timer
        if (this.plugin.getConfig().getBoolean("dev")) {Bukkit.getServer().getConsoleSender().sendMessage("[inactiveRestart.onPlayerJoin] Player Joined"); }
            setTimer();
    }
    public void setTimer() {
        boolean dev = this.plugin.getConfig().getBoolean("dev");
        String inactiveMessage = this.plugin.getConfig().getString("inactiveRestart.message");
        String shutdownMessage = this.plugin.getConfig().getString("shutdownMessage");
        int timer = this.plugin.getConfig().getInt("inactiveRestart.timer");
        if(inactiveRestart!= null) inactiveRestart.interrupt();

        inactiveRestart = new Thread(()->{
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < timer * 1000.0) {
                try { Thread.sleep(1000);} catch (InterruptedException ignore) {}
            }
            if (System.currentTimeMillis() - startTime >= timer * 1000.0) {
                if (Bukkit.getServer().getOnlinePlayers().isEmpty()) {
                    if (inactiveMessage != null) sendMessage( (inactiveMessage).replace('&', '§') );
                    try {Thread.sleep(3000);} catch (InterruptedException ignored) {}
                    if (shutdownMessage != null) { sendMessage( (shutdownMessage).replace('&', '§') ); }
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
                    if (dev) {Bukkit.getServer().getConsoleSender().sendMessage("[inactiveRestart.SetTimer] players online - no restart"); }
                    setTimer();
                }
            }
        });
        inactiveRestart.start();
    }
}
