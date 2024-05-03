package net.txsla.advancedrestart;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedRestart extends JavaPlugin {
    @Override
    public void onEnable() {
    saveDefaultConfig();
        getLogger().info("Loading configs...");
        if ( getConfig().getBoolean("inactiveRestart.enabled") ) { getLogger().info("inactiveRestart Enabled"); getServer().getPluginManager().registerEvents(new inactiveRestart(this),this); }
        if ( getConfig().getBoolean("periodicRestart.enabled") ) { getLogger().info("periodicRestart Enabled");  new periodicRestart(this);}
        getLogger().info("Config load complete!");
    }
    @Override
    public void onDisable() {
        getLogger().info("[AdvancedRestart.onDisable] plugin disabled");
    }
}
