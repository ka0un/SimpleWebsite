package org.kasun.website;


import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.website.Utils.Metrics;
import org.kasun.website.Utils.StaticLogger;




public class SimpleWebsite extends JavaPlugin {
    private MainManager mainManager;
    private static SimpleWebsite instance;

    @Override
    public void onEnable() {
        instance = this;
        StaticLogger.setLogger(getLogger());
        StaticLogger.info("============================================");
        StaticLogger.info("Simple Website Plugin has been enabled!");
        mainManager = new MainManager();
        int pluginId = 19784;
        Metrics metrics = new Metrics(this, pluginId);
        StaticLogger.info("============================================");
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

