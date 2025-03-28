package net.txsla.advancedrestart;

import java.util.ArrayList;

public class config {
    public static boolean allow_restart;
    public static double disable_timer;
    public static boolean reload() { return load(); } // :)

    public static boolean load() {
        // load all configs (also make work with /config reload later on)
        try {
            allow_restart = true;

            //this.plugin.getConfig().getBoolean("restartWarning.secondsWarn.enabled");



        }
        // throw error if you cannot load the configs
        catch (Exception e) {
            System.out.println("\n\n[Advanced Restart] Failed to load configs\n" + e + "\n\n");
            return false;
        }
        return true;
    }

    public static boolean scheduledRestart_enabled;
    public static ArrayList<String> scheduledRestart_schedule;
    public static String scheduledRestart_message;


    public static boolean periodicRestart_enabled;
    public static double periodicRestart_duration;
    public static String periodicRestart_message;

    public static boolean restartWarning_secondsWarn_enabled;
    public static int restartWarning_secondsWarn_seconds;
    public static boolean restartWarning_secondsWarn_countdown;
    public static String restartWarning_secondsWarn_message;

    public static boolean restartWarning_minuteWarn_enabled;
    public static int restartWarning_minuteWarn_minutes;
    public static boolean restartWarning_minuteWarn_countdown;
    public static String restartWarning_minuteWarn_message;

    public static boolean inactiveRestart_enabled;
    public static double inactiveRestart_timer;
    public static String inactiveRestart_message;

    public static boolean lagRestart_lowTPS_enabled;
    public static double lagRestart_lowTPS_minTPS;
    public static int lagRestart_lowTPS_checks;
    public static String lagRestart_lowTPS_message;

    public static boolean lagRestart_lowMemory_enabled;
    public static double lagRestart_lowMemory_maxMemUsage;
    public static int lagRestart_lowMemory_checks;
    public static String lagRestart_lowMemory_message;

    public static int shutdownMethod;
    public static String shutdownMessage;

    public static ArrayList<String> shutdownCommands;


    public static boolean debug;

}
