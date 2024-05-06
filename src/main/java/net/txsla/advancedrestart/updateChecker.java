package net.txsla.advancedrestart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class updateChecker {
    Thread updateChecker;
    private final static String API_URL = "https://api.github.com/repos/Tesla6426/Advanced-Restart/releases/latest";
    private final static String RELEASES_URL = "https://modrinth.com/plugin/advanced-restart/";
    public void sendMessage(String message) { for (Player p : Bukkit.getOnlinePlayers()) { p.sendMessage(message);} Bukkit.getServer().getConsoleSender().sendMessage(message);}
    private final AdvancedRestart plugin;
    public updateChecker(AdvancedRestart plugin) {
        this.plugin = plugin;
        updateChecker = new Thread(() -> {
            String[] latest;
            String pluginVersion;
            int ma, mi, pa;
            int latestMa, latestMi, latestPa;
            try {
                latest = parseResponse(checkGithubVer());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pluginVersion = Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDescription().getVersion();
            latestMa = Integer.parseInt(latest[1].replaceAll("^[^0-9]+", "").replaceAll("\\..*$", ""));
            latestMi = Integer.parseInt(latest[1].replaceAll("^[^0-9]*[0-9]+[^0-9]*", "").replaceAll("\\..*$", ""));
            latestPa = Integer.parseInt(latest[1].replaceAll("^[^.]*\\.[0-9]+\\.", "").replaceAll("[^0-9]+$", ""));
            ma = Integer.parseInt(pluginVersion.replaceAll("^[^0-9]*", "").replaceAll("\\..*$", ""));
            mi = Integer.parseInt(pluginVersion.replaceAll("^[^0-9]*[0-9]+[^0-9]*", "").replaceAll("\\..*$", ""));
            pa = Integer.parseInt(pluginVersion.replaceAll("^[^.]*\\.[0-9]+\\.", "").replaceAll("[^0-9]+$", ""));
            //skip checks if versions match
            if (!(latestMa + "." + latestMi + "." + latestPa).matches(ma + "." + mi + "." + pa)) {
                plugin.getLogger().warning("[==================================]");
                if (latestMa > ma) {
                    plugin.getLogger().warning("[ Advanced Restart is " + (latestMa-ma) + " MAJOR versions behind");
                } else if (latestMi > mi) {
                    plugin.getLogger().warning("[ Advanced Restart is " + (latestMi-mi) + " minor versions behind");
                } else if (latestPa > pa) {
                    plugin.getLogger().warning("[ Advanced Restart is " + (latestPa-pa) + " patches behind");
                }else {
                    plugin.getLogger().severe("Update verification error: Please check for latest plugin version!");
                }
                plugin.getLogger().warning("[ The latest version is "+latestMa+"."+latestMi+"."+latestPa+" and you are running " + pluginVersion);
                plugin.getLogger().warning("[ Download the latest release at:");
                plugin.getLogger().warning("[ "+RELEASES_URL);
                plugin.getLogger().warning("[==================================]");
            } else {
                plugin.getLogger().info("[=======================================]");
                plugin.getLogger().info("[ Advanced Restart is fully up to date! ]");
                plugin.getLogger().info("[=======================================]");
            }
         }); updateChecker.start();
        }
    private String checkGithubVer() throws  IOException{
        URLConnection connect = new URL(API_URL).openConnection();
        connect.addRequestProperty("User-Agent", "Mozilla/4.0");
        String apiResponse = new BufferedReader(new InputStreamReader(connect.getInputStream())).readLine();
        return apiResponse.replaceAll(".*tag_name\":\"", "").replaceAll(",\"created_.*", "");
    }
    private String[] parseResponse(String input) {
        String[] parsed = new String[4] ;
        String oper, sto = " ";
        input = input.replaceAll("main[^:]*[:\"]+", "");
        for (int i = 0; 4 > i; i++) {
            oper = input.replaceAll("[\",:]{2,}.*", "");
            input = input.replaceAll("^[^:]*[:\"]+", "");
            parsed[i] = oper;
            if (i==3 && (sto.matches("true") | oper.matches("true")) ) parsed[2] = "true";
                else parsed[2] = "false";
            sto = oper;
        }
        return parsed;
    }
}
