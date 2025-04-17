package net.txsla.advancedrestart.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.txsla.advancedrestart.command.sub.*;

import java.util.ArrayList;
import java.util.List;

public class main_command implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // if no args, then return version
        if (args.length == 0) {
            sender.sendMessage("Advanced Restart v1.2.0");
            return true;
        }

        // send args to subcommand
        switch (args[0]) {
            case "disable":
                disable.run(args, sender);
                break;
            case "enable":
                enable.run(args, sender);
                break;
            case "status":
                status.run(args, sender);
                break;
            case "soft-reload":
                soft_reload.run(args, sender);
                break;
            case "debug":
                debug.run(args, sender);
                break;
            case "config":
                debug.run(args, sender);
                break;
            default:
                return false;
        }
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // select subcommand
        if (args.length <= 1) {
            List<String> list = new ArrayList<>();
            list.add("disable");
            list.add("enable");
            list.add("status");
            list.add("soft-reload");
            list.add("debug");
            list.add("config");
            return list;
        }

        // return data from subcommand
        switch (args[0]) {
            case "disable":
                return disable.tab(args);
            case "enable":
                return enable.tab(args);
            case "status":
                return status.tab(args);
            case "soft-reload":
                return soft_reload.tab(args);
            case "debug":
                return debug.tab(args);
            case "config":
                return config.tab(args);
            default:
                return null;
        }
    }
}
