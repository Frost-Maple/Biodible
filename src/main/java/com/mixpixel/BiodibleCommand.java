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
            sender.sendMessage("�򿪲˵� /ydb cd��Client Only��");
            sender.sendMessage("���Բ�� /ydb debug");
            sender.sendMessage("���ز�� /ydb reload��OP Only��");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("��ʽ����");
            return false;
        }
        if (sender instanceof Player p) {
            if (args[0].equals("cd")) {
                Inventory inv = Bukkit.createInventory(p, 45, "Biodible Menu");

                ItemStack i = new ItemStack(Material.BLUE_ICE, 1);
                ItemMeta meta = i.getItemMeta();
                meta.setDisplayName("��lStart Process");
                meta.setLore(List.of("��f�������ѧѧϰ����ô���أ���", "��f��±���ʦΪ���ṩ�˲��ԣ�", "��f���ھͿ�ʼ�ɣ�"));
                i.setItemMeta(meta);

                ItemStack i0 = new ItemStack(Material.BOOK, 1);
                ItemMeta meta0 = i0.getItemMeta();
                meta0.setLore(List.of("��f��Ϊ����ѧѧϰ����׼�����𣿣�", "��f��±���ʦΪ���ṩ�������飡", "��f���ھ���ȡ�ɣ�������OP��ȡ������ԭ������ˣ�"));
                i0.setItemMeta(meta0);

                ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
                ItemMeta metaPlaceholder = placeholder.getItemMeta();
                metaPlaceholder.setDisplayName("��fBiodible Menu");
                placeholder.setItemMeta(metaPlaceholder);

                ItemStack arrow = new ItemStack(Material.ARROW, 1);
                ItemMeta meta1 = arrow.getItemMeta();
                meta1.setDisplayName("��e���ǰ�ť");
                meta1.setLore(List.of("��f��������ԭ����", "��f�·�������������", "��f�Ϸ��ɷ��뱣�ռ�"));
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
                sender.sendMessage("��fBiodible Has Reloaded.");
            } else {
                sender.sendMessage("��fBiodible Warning: You don't have the permission for this!");
            }
            return true;
        } else {
            sender.sendMessage("This is invalid for Biodible for MixPixel.");
        }
        return false;
    }
}
