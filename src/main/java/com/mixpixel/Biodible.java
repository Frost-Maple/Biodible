package com.mixpixel;

import com.mixpixel.config.ConfigData;
import com.mixpixel.config.Recipe;
import com.mixpixel.gui.LevelUpGuiListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Biodible extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        System.out.println("Biodible for MixPixel has loaded successfully.");
        Bukkit.getPluginCommand("biodible").setExecutor(new BiodibleCommand()); // config.yml已经配置，断言非null
        Bukkit.getPluginManager().registerEvents(new LevelUpGuiListener(), this);
        Bukkit.getPluginCommand("biodible").setTabCompleter(new BiodibleCompleter());
        /*
        以下为意义不明部分：导入Config.yml
        System.out.println("已导入的配方原料：(For Debug Use)"+ConfigData.nameList);
        System.out.println("已导入的配方产物：(For Debug Use)"+ConfigData.winItemNameList);
        System.out.println("已导入的产物名称：(For Debug Use)"+ConfigData.winItemLoreList);
        */
        FileConfiguration config = getConfig();
        loadConfig(config);
    }
    public void reloadConfiguration() {
        this.reloadConfig();
        loadConfig(getConfig());
    }

    private void loadConfig(FileConfiguration config) {
        // placeholder
        ConfigData.placeHolderList = getConfig().getIntegerList("PlaceholderSlots");
        System.out.println("菜单界面配置已成功导入！");
        // recipes
        for (String key : getConfig().getKeys(false)) {
            if (key.equals("PlaceholderSlots")) continue;
            System.out.println("配方 "+key+" 已成功导入！");
            ConfigurationSection section = config.getConfigurationSection(key);
            List<String> list = new ArrayList<>();
            section.getStringList("WinItemLore").forEach(str -> list.add(ChatColor.translateAlternateColorCodes('&',str)));
            if (section.getString("Name") == null || section.getString("UseShardName") == null || section.getString("UseProtectionName") == null||section.getString("WinItemName") == null||section.getString("WinItemName") == null) throw new RuntimeException("Config.yml中配方 "+key+" 配置有误！");
            ConfigData.recipeList.add(new Recipe(
                    ChatColor.translateAlternateColorCodes('&',section.getString("Name")), section.getBoolean("UseShard"),
                    ChatColor.translateAlternateColorCodes('&',section.getString("UseShardName")), section.getInt("UseShardAmount"),
                    section.getBoolean("UseProtection"), ChatColor.translateAlternateColorCodes('&',section.getString("UseProtectionName")),
                    section.getInt("UseProtectionAmount"), section.getInt("WinProbability"),
                    ChatColor.translateAlternateColorCodes('&',section.getString("WinItemName")), list
                    ));

        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("""
                Thank you for using Biodible.
                Build 0.4.0(180)
                Initially by Lettuce
                A warm thank-you to ZuLa
                With help from OPenAI's ChatGPT
                Initially on 30 Aug '23
                Edited on 22 Sep '23
                On 29 Sep '23
                Bye!""");
    }
}
