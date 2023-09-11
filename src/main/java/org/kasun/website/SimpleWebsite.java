package org.kasun.website;

import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.website.Commands.WebsiteCommand;
import org.kasun.website.Utils.Metrics;

public class SimpleWebsite extends JavaPlugin {
    private MainManager mainManager;
    private static SimpleWebsite instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Website Plugin has been enabled!");
        mainManager = new MainManager();
        getCommand("website").setExecutor(new WebsiteCommand());
        int pluginId = 19784;
        Metrics metrics = new Metrics(this, pluginId);

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
//test
}

