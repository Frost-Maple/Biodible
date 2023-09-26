package com.mixpixel;

import com.mixpixel.config.ConfigData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class BiodibleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //if (args.length == x)
        if (args.length == 0) {
            sender.sendMessage("打开菜单 /ydb cd（Client Only）");
            sender.sendMessage("测试插件 /ydb debug");
            sender.sendMessage("重载插件 /ydb reload（OP Only）");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("格式错误！");
            return false;
        }
        if (sender instanceof Player p) {
            if (args[0].equals("cd")) {
                Inventory inv = Bukkit.createInventory(p, 45, "Biodible Menu");

                ItemStack i = new ItemStack(Material.BLUE_ICE, 1);
                ItemMeta meta = i.getItemMeta();
                meta.setDisplayName("§lStart Process");
                meta.setLore(List.of("§f你的生物学学习的怎么样呢？！", "§f杨德宝老师为你提供了测试！", "§f现在就开始吧！"));
                i.setItemMeta(meta);

                ItemStack i0 = new ItemStack(Material.BOOK, 1);
                ItemMeta meta0 = i0.getItemMeta();
                meta0.setLore(List.of("§f你为生物学学习做好准备了吗？！", "§f杨德宝老师为你提供了生物书！", "§f现在就领取吧！（仅限OP领取，不玩原神就算了）"));
                i0.setItemMeta(meta0);

                ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
                ItemMeta metaPlaceholder = placeholder.getItemMeta();
                metaPlaceholder.setDisplayName("§fBiodible Menu");
                placeholder.setItemMeta(metaPlaceholder);

                ItemStack arrow = new ItemStack(Material.ARROW, 1);
                ItemMeta meta1 = arrow.getItemMeta();
                meta1.setDisplayName("§e升星按钮");
                meta1.setLore(List.of("§f左侧请放入原材料", "§f下方请放入升星配件", "§f上方可放入保险剂"));
                arrow.setItemMeta(meta1);

                inv.setItem(3, i);
                inv.setItem(5, i0);
                inv.setItem(22, arrow);

                for (Integer integer : ConfigData.placeHolderList) {
                    inv.setItem(integer, placeholder);
                }
                p.openInventory(inv);
                return true;
            }
        }
        if (args[0].equals("debug")) {
            sender.sendMessage("Biodible for MixPixel is running normally.");
            return true;
        }
        if (args[0].equals("reload")) {
            if (sender.isOp()) {
                JavaPlugin.getPlugin(Biodible.class).reloadConfiguration();
                sender.sendMessage("§fBiodible Has Reloaded.");
            } else {
                sender.sendMessage("§fBiodible Warning: You don't have the permission for this!");
            }
            return true;
        } else {
            sender.sendMessage("This is invalid for Biodible for MixPixel.");
        }
        return false;
    }
}
