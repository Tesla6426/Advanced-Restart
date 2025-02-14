package net.txsla.advancedrestart;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedRestart extends JavaPlugin {
    @Override
    public void onEnable() {
    saveDefaultConfig();
    int pluginId = 21811;
    Metrics metrics = new Metrics(this, pluginId);

        getLogger().warning("You are currently running a dev build of Advanced Restart");

        getLogger().info("Loading configs...");
        if ( getConfig().getBoolean("dev") ) { Bukkit.getServer().getConsoleSender().sendMessage("[AdvancedRestart] §4Dev Mode Enabled"); }
        if ( getConfig().getBoolean("inactiveRestart.enabled") ) { getLogger().info("inactiveRestart Enabled"); getServer().getPluginManager().registerEvents(new inactiveRestart(this),this); }
        if ( getConfig().getBoolean("periodicRestart.enabled") ) { getLogger().info("periodicRestart Enabled");  new periodicRestart(this);}
        if ( getConfig().getBoolean("lagRestart.lowTPS.enabled") ) { getLogger().info("tpsRestart Enabled");  new tpsRestart(this);}
        if ( getConfig().getBoolean("lagRestart.lowMemory.enabled") ) { getLogger().info("ramRestart Enabled");  new ramRestart(this);}
        if ( getConfig().getBoolean("scheduledRestart.enabled") ) { getLogger().info("scheduledRestart Enabled");  new dailyRestart(this);}
        getLogger().info("Config load complete!");

    }
    @Override
    public void onDisable() {
        getLogger().info("[AdvancedRestart.onDisable] plugin disabled");
    }
}
