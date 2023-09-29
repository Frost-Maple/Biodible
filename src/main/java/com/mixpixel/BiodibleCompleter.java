package com.mixpixel;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BiodibleCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("ydb")||command.getName().equalsIgnoreCase("biodible")) {
            if (args.length != 1) return null;
            completions.add("debug");
            if (sender instanceof Player) {
                completions.add("cd");
                if (sender.isOp()) {
                    completions.add("reload");
                }
            }
        }

        return completions;
    }
}
