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
      event.joinMessage(Component.text(n+"，你学不学德宝学？"));
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
                player.sendMessage("学德宝学，学了：" + player.getLevel() + "天了。");
                player.sendMessage("玩原神，玩了：" + player.getTicksLived() / 200 + "天了。");
                event.setCancelled(true);
            }
            case 5 -> {
                if (player.isOp()) {
                    ItemStack item = new ItemStack(Material.BOOK, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("§f生物书（1星）");
                    meta.setLore(List.of("§f一本生物书", "§f有着署名：杨德宝老师"));
                    item.setItemMeta(meta);
                    event.setCancelled(true);
                } else {
                    player.sendMessage("不玩原神，不能拿。");
                    event.setCancelled(true);
                }
            }
            case 22 -> {
                ItemStack orgItem = inventory.getItem(19);
                if (orgItem == null) {
                    player.sendMessage("你需要先放入待升星的物品！");
                    event.setCancelled(true);
                }

                ItemMeta originItemMeta = orgItem.getItemMeta();
                String displayName = originItemMeta.getDisplayName();
                Optional<Recipe> recipe1 = ConfigData.recipeList.stream().filter(recipe -> recipe.name().equals(displayName)).findFirst();
                if (recipe1.isEmpty()) {
                    player.sendMessage("此物品不可强化。");
                    event.setCancelled(true);
                    return;
                }
                Recipe recipe = recipe1.get();

                if (orgItem.getAmount() != 1) {
                    player.sendMessage("一次只能强化1个物品！");
                    inventory.clear(19); // FIRST DO IT
                    player.getInventory().addItem(orgItem); // AND THEN
                    event.setCancelled(true);
                    return;
                }
                if (inventory.getItem(25) != null) {
                    player.sendMessage("请先拿走强化产物后再继续操作！");
                    event.setCancelled(true);
                    return;
                }

                int winPossibility = recipe.winProbability();
                int thisWinRandInt = (int) (Math.random() * 101);
                if (recipe.useShard()) {

                    ItemStack shard = inventory.getItem(31);
                    if (shard == null) {
                        player.sendMessage("强化此物品需要配件。");
                        event.setCancelled(true);
                        return;
                    }

                    String providedShardName = shard.getItemMeta().getDisplayName();
                    if (providedShardName.equals(recipe.useShardName())) {
                        int shardAmount = shard.getAmount();
                        if (shardAmount >= recipe.useShardAmount()) {
                            shard.setAmount(shardAmount - recipe.useShardAmount());
                        } else {
                            player.sendMessage("强化所需的配件不足。");
                            event.setCancelled(true);
                            return;
                        }
                    } else {
                        player.sendMessage("强化所需的配件不正确！");
                        event.setCancelled(true);
                        return;
                    }
                }
                if (thisWinRandInt < winPossibility) {
                    originItemMeta.setDisplayName(recipe.winItemName());
                    originItemMeta.setLore(recipe.winItemLore());
                    orgItem.setItemMeta(originItemMeta);
                    player.sendMessage("恭喜你，强化成功！");
                    inventory.clear(19);
                    inventory.setItem(25, orgItem);
                    event.setCancelled(true);
                    return;
                }
                player.sendMessage("很遗憾，强化失败。");
                if (inventory.getItem(13) == null) {
                    inventory.clear(19);
                    event.setCancelled(true);
                    return;
                }
                if (recipe.useProtection() &&
                        inventory.getItem(13).getItemMeta().getDisplayName().equals(recipe.useProtectionName()) &&
                        inventory.getItem(13).getAmount() >= recipe.useProtectionAmount()) {
                    player.sendMessage("保险剂起作用成功！左侧物品未被消耗，你可以继续强化。");
                    ItemStack protection = inventory.getItem(13);
                    protection.setAmount(protection.getAmount() - recipe.useProtectionAmount());
                } else {
                    player.sendMessage("此物品无法使用此保险剂，或投入的保险剂数目不足。");
                    player.sendMessage("左侧物品正常消耗，保险剂未被消耗。");
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
