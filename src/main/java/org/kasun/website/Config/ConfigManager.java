package org.kasun.website.Config;

import org.bukkit.plugin.Plugin;
import org.kasun.website.SimpleWebsite;
import org.kasun.website.Utils.FileUtils;

import java.io.File;

public class ConfigManager {

    private MainConfig mainConfig;
    public ConfigManager(){
        Plugin plugin = SimpleWebsite.getInstance();
        mainConfig = new MainConfig(SimpleWebsite.getInstance());

        //copy public folder from jar
        FileUtils fileUtils = new FileUtils();

        File publicFolder = new File(plugin.getDataFolder() + "/public");
        if (!publicFolder.exists()) {
            plugin.getLogger().info("public folder not found, creating...");
            try{
                File defaultFolder = new File(plugin.getDataFolder() + "/public/default");
                publicFolder.mkdir();
                defaultFolder.mkdir();
                fileUtils.copyFileFromResources("keystore.p12", plugin.getDataFolder());
                fileUtils.copyFileFromResources("default.html", defaultFolder);
                boolean renamed = fileUtils.renameFile(defaultFolder + "/default.html", "index.html");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mainConfig = new MainConfig(SimpleWebsite.getInstance());

    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }
}
