package com.mixpixel;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.List;

public final class Biodible extends JavaPlugin {

    static Biodible main;
    public static class lists
    {
        public static List<String> recipeList = new ArrayList<>();
        public static List<Component> nameList = new ArrayList<>();
        public static List<String> nameElist = new ArrayList<>();
        public static List<String> useShardNameList = new ArrayList<>();
        public static List<Integer> useShardAmountList = new ArrayList<>();
        public static List<Boolean>useShardList = new ArrayList<>();
        public static List<Integer> winProbablityList = new ArrayList<>();
        public static List<Component> winItemNameList = new ArrayList<>();
        public static List<List<String>> winItemLoreList = new ArrayList<>();
        public static List<Boolean> useProtectionList = new ArrayList<>();
        public static List<String> useProtectionNameList = new ArrayList<>();
        public static List<Integer> useProtectionAmountList = new ArrayList<>();
        public static List<Integer> placeHolderList = new ArrayList<>();

    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        System.out.println("Biodible for MixPixel has loaded successfully.");
        Objects.requireNonNull(Bukkit.getPluginCommand("biodible")).setExecutor(new MyCommand());
        Bukkit.getPluginManager().registerEvents(new A1(),this);
        main = this;
        //以下为意义不明部分：导入Config.yml
        loadConfigurations();
        //System.out.println("已导入的配方原料：(For Debug Use)"+lists.nameList);
        //System.out.println("已导入的配方产物：(For Debug Use)"+lists.winItemNameList);
        //System.out.println("已导入的产物名称：(For Debug Use)"+lists.winItemLoreList);

    }

    public void loadConfigurations() {
        Biodible.main.reloadConfig();
        lists.winItemLoreList.clear();
        lists.winProbablityList.clear();
        lists.recipeList.clear();
        lists.nameElist.clear();
        lists.useShardAmountList.clear();
        lists.winItemNameList.clear();
        lists.nameList.clear();
        lists.useShardNameList.clear();
        lists.placeHolderList = getConfig().getIntegerList("PlaceholderSlots");
        Set<String> allFormulas = Biodible.main.getConfig().getKeys(false);
        Iterator<String> iterator1 = allFormulas.iterator();
        System.out.println("Biodible 正在加载 Config 中的" + allFormulas);
        while (iterator1.hasNext()) {
            String thisFormula = iterator1.next();
            if (Objects.equals(thisFormula, "PlaceholderSlots")){
                System.out.println("已成功加载Placeholders！");
            }
            else {
                System.out.println(thisFormula + "已成功导入 By Biodible。");
                lists.recipeList.add(thisFormula);
                Component thisName = Component.text(Objects.requireNonNull(getConfig().getString(thisFormula + ".Name").replace("&","§")));
                lists.nameList.add(thisName);
                String thisUseShard1 = getConfig().getString(thisFormula + ".UseShardName").replace("&","§");
                lists.useShardNameList.add(thisUseShard1);
                int thisUseShardAmount = getConfig().getInt(thisFormula + ".UseShardAmount");
                lists.useShardAmountList.add(thisUseShardAmount);
                int thisWinProbability = getConfig().getInt(thisFormula + ".WinProbability");
                lists.winProbablityList.add(thisWinProbability);
                Component thisWinItemName = Component.text(Objects.requireNonNull(getConfig().getString(thisFormula + ".WinItemName").replace("&","§")));
                lists.winItemNameList.add(thisWinItemName);
                List<String> WinItemLore = getConfig().getStringList(thisFormula + ".WinItemLore");
                List<String> thisWinItemLore = new ArrayList<>();
                for(String str: WinItemLore){
                    String reStr = str.replaceAll("&","§");
                    thisWinItemLore.add(reStr);
                }
                lists.winItemLoreList.add(thisWinItemLore);
                lists.useShardList.add(getConfig().getBoolean(thisFormula+".UseShard"));
                lists.nameElist.add(getConfig().getString(thisFormula+".Name").replace("&","§"));
                lists.useProtectionList.add(getConfig().getBoolean(thisFormula+".useProtection"));
                lists.useProtectionNameList.add(getConfig().getString(thisFormula+".useProtectionName"));
                lists.useProtectionAmountList.add(getConfig().getInt(thisFormula+".useProtectionAmount"));
            }
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("""
                Thank you for using Biodible.
                Build 0.0.9(84)
                By Lettuce
                With help from OPenAI's ChatGPT
                On 23 Aug '23
                Bye!""");
    }
}
