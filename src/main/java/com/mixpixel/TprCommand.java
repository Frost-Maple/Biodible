package com.mixpixel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TprCommand implements CommandExecutor {
    public boolean isBlockAir(World world, int x, int y, int z){
        Block block = world.getBlockAt(x,y,z);
        return block.getType().equals(Material.AIR);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 0){
            sender.sendMessage("This command doesn't come with Arguments.");
            return false;
        }
        Player player = (Player) sender;
        Random random = new Random();
        Random Ran = new Random();
        int Rand = random.nextInt(1000);
        int Rand2 = Ran.nextInt(1000);
        int x = Rand - 500;
        int z = Rand2 - 500;
        List<Integer> list = new ArrayList<>();
        for (int i = 256; i>0; i--){
            World world = player.getWorld();
            if (isBlockAir(world, x, i, z)){
                list.add(i);
            }
        }
        if (list.isEmpty()){
            sender.sendMessage("出现了点错误，请重新输入/tpr");
            return false;
        }else{
            int y = Collections.min(list);
            Location location = new Location(player.getWorld(), x, y, z);
            player.teleport(location);
        }
        return false;
    }
}
