package net.txsla.advancedrestart;

import net.txsla.advancedrestart.command.main_command;
import net.txsla.advancedrestart.threads.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedRestart extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        // bstats
        int pluginId = 21811;
        Metrics metrics = new Metrics(this, pluginId);

        getLogger().warning("You are currently running a dev build of Advanced Restart");

        // load configs
        getLogger().info("Loading configs...");
        config.plugin = this;
        if (config.load()) {
            getLogger().info("Config load complete!");
        }
        else {
            getLogger().warning("PLUGIN FAILED TO PROPERLY LOAD CONFIGS - CHECK /plugins/AdvancedRestart/config.yml FOR MISTAKES!");
        }


        // let user know debug is enabled
        if ( config.debug ) { Bukkit.getServer().getConsoleSender().sendMessage("[AdvancedRestart] §4Debug Mode Enabled"); }

        // start necessary processes
        getLogger().info("Starting threads...");
        if (start()) {
            getLogger().info("Threads started!");
        }
        else {
            getLogger().warning("PLUGIN FAILED TO PROPERLY LOAD THREADS - CHECK /plugins/AdvancedRestart/config.yml FOR MISTAKES!");
        }

        getCommand("advancedrestart").setExecutor(new main_command());

    }
    public boolean start() {
        // Start/Load the different restarting classes
        try {
            if ( config.inactiveRestart_enabled ) { getLogger().info("inactiveRestart Enabled"); getServer().getPluginManager().registerEvents(new inactive_restart(),this); }
            if ( config.periodicRestart_enabled ) { getLogger().info("periodicRestart Enabled");  new periodic_restart();}
            if ( config.lagRestart_lowTPS_enabled ) { getLogger().info("tpsRestart Enabled");  new tps_restart();}
            if ( config.lagRestart_lowMemory_enabled ) { getLogger().info("ramRestart Enabled");  new ram_restart();}
            if ( config.scheduledRestart_enabled ) { getLogger().info("scheduledRestart Enabled");  new schedule_restart();}
        } catch (Exception e) {
            // throw error if you cannot load the classes
            getLogger().warning("[Advanced Restart] Failed to start threads\n" + e + "\n\n");
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
        getLogger().info("plugin disabled");
    }
}
