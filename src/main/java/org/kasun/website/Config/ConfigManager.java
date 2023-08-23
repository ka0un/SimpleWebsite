package org.kasun.website.Config;

import org.bukkit.plugin.Plugin;
import org.kasun.website.SimpleWebsite;
import org.kasun.website.Utils.FileUtils;

import java.io.File;

public class ConfigManager {
    public ConfigManager(){
        //copy config.yml
        Plugin plugin = SimpleWebsite.getInstance();
        plugin.saveDefaultConfig();

        //copy messages.yml
        File exampleHtml = new File(plugin.getDataFolder() + "messages.yml");
        if (!exampleHtml.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        //copy public folder from jar
        FileUtils fileUtils = new FileUtils();

        File publicFolder = new File(plugin.getDataFolder() + "/public");
        if (!publicFolder.exists()) {
            plugin.getLogger().info("public folder not found, creating...");
            try{
                File defaultFolder = new File(plugin.getDataFolder() + "/public/default");
                publicFolder.mkdir();
                defaultFolder.mkdir();
                fileUtils.copyFileFromResources("default.html", defaultFolder);
                boolean renamed = fileUtils.renameFile(defaultFolder + "/default.html", "index.html");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
