package org.kasun.website.Config;

import org.bukkit.plugin.Plugin;
import org.kasun.website.SimpleWebsite;
public class ConfigManager {

    private MainConfig mainConfig;
    public ConfigManager(){
        Plugin plugin = SimpleWebsite.getInstance();
        mainConfig = new MainConfig(SimpleWebsite.getInstance());

    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }
}
