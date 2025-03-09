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
        if (config.load()) getLogger().info("Config load complete!");
        if ( config.debug ) { Bukkit.getServer().getConsoleSender().sendMessage("[AdvancedRestart] §4Debug Mode Enabled"); }

        getLogger().info("Starting threads...");
        if (start()) getLogger().info("Threads started!");

    }
    public boolean start() {
        // Start/Load the different restarting classes
        try {
            if ( config.inactiveRestart_enabled ) { getLogger().info("inactiveRestart Enabled"); getServer().getPluginManager().registerEvents(new inactiveRestart(),this); }
            if ( config.periodicRestart_enabled ) { getLogger().info("periodicRestart Enabled");  new periodicRestart();}
            if ( config.lagRestart_lowTPS_enabled ) { getLogger().info("tpsRestart Enabled");  new tpsRestart();}
            if ( config.lagRestart_lowMemory_enabled ) { getLogger().info("ramRestart Enabled");  new ramRestart();}
            if ( config.scheduledRestart_enabled ) { getLogger().info("scheduledRestart Enabled");  new dailyRestart();}
        } catch (Exception e) {
            // throw error if you cannot load the classes
            System.out.println("[Advanced Restart] Failed to start threads\n" + e + "\n\n");
            return false;
        }
        return true;
    }
    public boolean stop() {
        // kill all threads
        return false;
    }
    @Override
    public void onDisable() {
        getLogger().info("[AdvancedRestart.onDisable] plugin disabled");
    }
}
