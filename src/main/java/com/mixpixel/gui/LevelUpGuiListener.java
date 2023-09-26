package com.mixpixel.gui;

import com.mixpixel.config.ConfigData;
import com.mixpixel.config.Recipe;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class LevelUpGuiListener implements Listener {
    /*
    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
      Player q = event.getPlayer();
      String n = q.getName();
      event.joinMessage(Component.text(n+"����ѧ��ѧ�±�ѧ��"));
    */

    //}
    @EventHandler
    public void click(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Biodible Menu")) return;
        if (ConfigData.placeHolderList.contains(event.getRawSlot())) {
            event.setCancelled(true);
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        switch (event.getRawSlot()) {
            case 3 -> {
                player.sendMessage("ѧ�±�ѧ��ѧ�ˣ�" + player.getLevel() + "���ˡ�");
                player.sendMessage("��ԭ�����ˣ�" + player.getTicksLived() / 200 + "���ˡ�");
                event.setCancelled(true);
            }
            case 5 -> {
                if (player.isOp()) {
                    ItemStack item = new ItemStack(Material.BOOK, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("��f�����飨1�ǣ�");
                    meta.setLore(List.of("��fһ��������", "��f������������±���ʦ"));
                    item.setItemMeta(meta);
                    event.setCancelled(true);
                } else {
                    player.sendMessage("����ԭ�񣬲����á�");
                    event.setCancelled(true);
                }
            }
            case 22 -> {
                ItemStack orgItem = inventory.getItem(19);
                if (orgItem == null) {
                    player.sendMessage("����Ҫ�ȷ�������ǵ���Ʒ��");
                    event.setCancelled(true);
                }

                ItemMeta originItemMeta = orgItem.getItemMeta();
                String displayName = originItemMeta.getDisplayName();
                Optional<Recipe> recipe1 = ConfigData.recipeList.stream().filter(recipe -> recipe.name().equals(displayName)).findFirst();
                if (recipe1.isEmpty()) {
                    player.sendMessage("����Ʒ����ǿ����");
                    event.setCancelled(true);
                    return;
                }
                Recipe recipe = recipe1.get();

                if (orgItem.getAmount() != 1) {
                    player.sendMessage("һ��ֻ��ǿ��1����Ʒ��");
                    inventory.clear(19); // FIRST DO IT
                    player.getInventory().addItem(orgItem); // AND THEN
                    event.setCancelled(true);
                    return;
                }
                if (inventory.getItem(25) != null) {
                    player.sendMessage("��������ǿ��������ټ���������");
                    event.setCancelled(true);
                    return;
                }

                int winPossibility = recipe.winProbability();
                int thisWinRandInt = (int) (Math.random() * 101);
                if (recipe.useShard()) {

                    ItemStack shard = inventory.getItem(31);
                    if (shard == null) {
                        player.sendMessage("ǿ������Ʒ��Ҫ�����");
                        event.setCancelled(true);
                        return;
                    }

                    String providedShardName = shard.getItemMeta().getDisplayName();
                    if (providedShardName.equals(recipe.useShardName())) {
                        int shardAmount = shard.getAmount();
                        if (shardAmount >= recipe.useShardAmount()) {
                            shard.setAmount(shardAmount - recipe.useShardAmount());
                        } else {
                            player.sendMessage("ǿ�������������㡣");
                            event.setCancelled(true);
                            return;
                        }
                    } else {
                        player.sendMessage("ǿ��������������ȷ��");
                        event.setCancelled(true);
                        return;
                    }
                }
                if (thisWinRandInt < winPossibility) {
                    originItemMeta.setDisplayName(recipe.winItemName());
                    originItemMeta.setLore(recipe.winItemLore());
                    orgItem.setItemMeta(originItemMeta);
                    player.sendMessage("��ϲ�㣬ǿ���ɹ���");
                    inventory.clear(19);
                    inventory.setItem(25, orgItem);
                    event.setCancelled(true);
                    return;
                }
                player.sendMessage("���ź���ǿ��ʧ�ܡ�");
                if (inventory.getItem(13) == null) {
                    inventory.clear(19);
                    event.setCancelled(true);
                    return;
                }
                if (recipe.useProtection() &&
                        inventory.getItem(13).getItemMeta().getDisplayName().equals(recipe.useProtectionName()) &&
                        inventory.getItem(13).getAmount() >= recipe.useProtectionAmount()) {
                    player.sendMessage("���ռ������óɹ��������Ʒδ�����ģ�����Լ���ǿ����");
                    ItemStack protection = inventory.getItem(13);
                    protection.setAmount(protection.getAmount() - recipe.useProtectionAmount());
                } else {
                    player.sendMessage("����Ʒ�޷�ʹ�ô˱��ռ�����Ͷ��ı��ռ���Ŀ���㡣");
                    player.sendMessage("�����Ʒ�������ģ����ռ�δ�����ġ�");
                    inventory.clear(19);
//                        player.getInventory().addItem(inventory.getItem(13));
//                        inventory.clear(13);

                }
                event.setCancelled(true);

            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (event.getView().getTitle().equals("Biodible Menu")) {
            HumanEntity e = event.getPlayer();
            ItemStack itemIn13 = inventory.getItem(13);
            if (itemIn13 != null) {
                e.getInventory().addItem(itemIn13);
            }
            ItemStack itemIn19 = inventory.getItem(19);
            if (itemIn19 != null) {
                e.getInventory().addItem(itemIn19);
            }
            ItemStack itemIn25 = inventory.getItem(25);
            if (itemIn25 != null) {
                e.getInventory().addItem(itemIn25);
            }
            ItemStack itemIn31 = inventory.getItem(31);
            if (itemIn31 != null) {
                e.getInventory().addItem(itemIn31);
            }
        }
    }
}
