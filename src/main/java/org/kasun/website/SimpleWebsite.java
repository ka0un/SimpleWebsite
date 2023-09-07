package org.kasun.website;

import org.bukkit.plugin.java.JavaPlugin;

public class SimpleWebsite extends JavaPlugin {
    private MainManager mainManager;
    private static SimpleWebsite instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Website Plugin has been enabled!");
        mainManager = new MainManager();

    }

    @Override
    public void onDisable() {
        getLogger().info("Website Plugin has been disabled!");

    }

    public static SimpleWebsite getInstance() {
        return instance;
    }

    public MainManager getMainManager() {
        return mainManager;
    }

}

