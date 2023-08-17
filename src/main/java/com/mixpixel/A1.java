package com.mixpixel;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class A1 implements Listener {
    /*
    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
      Player q = event.getPlayer();
      String n = q.getName();
      event.joinMessage(Component.text(n+"，你学不学德宝学？"));
    */

    //}
    @EventHandler
    public void click(InventoryClickEvent event){
        Player a = (Player) event.getWhoClicked();
        boolean placeholderClick = Biodible.lists.placeHolderList.contains(event.getRawSlot());
        if(event.getView().title().equals(Component.text("Biodible Menu", TextColor.color(13,71,91)))){
            switch(event.getRawSlot()){
                case 3:
                    String l = String.valueOf(a.getLevel());
                    String l1 =  String.valueOf(a.getTicksLived()/200);
                    a.sendMessage("学德宝学，学了："+l+"天了。");
                    a.sendMessage("玩原神，玩了："+l1+"天了。");
                    //p.getInventory().addItem(itemX)
                    //System.out.println("Biodible for Server: CD.1 was clicked.");
                    event.setCancelled(true);
                break;
                case 5:
                    if(a.isOp()) {
                        ItemStack item = new ItemStack(Material.BOOK, 1);
                        ItemMeta meta = item.getItemMeta();
                        meta.displayName(Component.text("§f生物书（1星）"));
                        List<Component> lore0 = new ArrayList<>();
                        lore0.add(Component.text("§f一本生物书"));
                        lore0.add(Component.text("§f有着署名：杨德宝老师"));
                        meta.lore(lore0);
                        item.setItemMeta(meta);
                        a.getInventory().addItem(item);
                        //System.out.println("Biodible for Server: CD.2 was clicked.");
                        event.setCancelled(true);
                    }
                    else {
                        a.sendMessage("不玩原神，不能拿。");
                        event.setCancelled(true);
                    }
                break;

                case 22:
                    Inventory inventory = event.getInventory();
                    ItemStack orgItem = inventory.getItem(19);
                    if (orgItem != null) {
                        ItemMeta meta = orgItem.getItemMeta();
                        Component name = meta.displayName();
                        String nameE = meta.getDisplayName();
                        //System.out.println("Debug:你放入了" + name + nameE);
                        //System.out.println("Debug:现在的列表为" + Biodible.lists.nameElist);
                        int e = Biodible.lists.nameElist.indexOf(nameE);
                        //System.out.println(e);
                        if (e == -1) {
                        a.sendMessage(Component.text("此物品不可强化。"));
                        event.setCancelled(true);
                        } else if (Objects.equals(nameE, Biodible.lists.nameElist.get(e))) {
                            int itemAmount = orgItem.getAmount();
                            if (itemAmount !=1){
                                a.sendMessage(Component.text("一次只能强化1个物品！"));
                                a.getInventory().addItem(orgItem);
                                event.setCancelled(true);
                                inventory.clear(19);
                                return;
                            }
                            int winPossibility = Biodible.lists.winProbablityList.get(e);
                            Random random = new Random();
                            int thisWinRandInt = random.nextInt(100);
                            if (Biodible.lists.useShardList.get(e)) {
                                ItemStack shard = inventory.getItem(31);
                                if (shard == null){
                                    a.sendMessage(Component.text("强化此物品需要配件。"));
                                }
                                else {
                                    ItemMeta metaS = shard.getItemMeta();
                                    String providedShardName = metaS.getDisplayName();
                                    if (providedShardName.equals(Biodible.lists.useShardNameList.get(e))){
                                        int shardAmount = shard.getAmount();
                                        if (shardAmount >= Biodible.lists.useShardAmountList.get(e)){
                                            ItemStack takeStack = shard.clone();
                                            takeStack.setAmount(Biodible.lists.useShardAmountList.get(e));
                                            inventory.removeItem(takeStack);
                                        }
                                        else{
                                            a.sendMessage(Component.text("强化所需的配件不足。"));
                                        }
                                    }
                                }
                            }
                            if (thisWinRandInt < winPossibility) {
                                meta.displayName(Biodible.lists.winItemNameList.get(e));
                                List<String> lore0 = Biodible.lists.winItemLoreList.get(e);
                                meta.setLore(lore0);
                                orgItem.setItemMeta(meta);
                                a.sendMessage("恭喜你，强化成功！");
                                inventory.clear(19);
                                inventory.setItem(25, orgItem);
                                event.setCancelled(true);
                            } else {
                                a.sendMessage("很遗憾，强化失败。");
                                inventory.clear(19);
                                event.setCancelled(true);
                            }
            }}

            else {
                a.sendMessage("你需要先放入待升星的物品！");
                event.setCancelled(true);
            }
            break;
        }
        if(event.getView().title().equals(Component.text("Biodible Menu", TextColor.color(13,71,91)))&&placeholderClick){
            event.setCancelled(true);
        }
}}
    @EventHandler
    public void close(InventoryCloseEvent event){
        if(event.getView().title().equals(Component.text("Biodible Menu", TextColor.color(13,71,91)))){
            Inventory inventory = event.getInventory();
            ItemStack itemIn19 = inventory.getItem(19);
            if(itemIn19!=null) {
                Player player = (Player) event.getPlayer();
                player.getInventory().addItem(itemIn19);
            }
            ItemStack itemIn25 = inventory.getItem(25);
            if(itemIn25!=null) {
                Player player = (Player) event.getPlayer();
                player.getInventory().addItem(itemIn25);
            }
            ItemStack itemIn31 = inventory.getItem(31);
            if(itemIn31!=null) {
                Player player = (Player) event.getPlayer();
                player.getInventory().addItem(itemIn31);
            }
        }
}
}
