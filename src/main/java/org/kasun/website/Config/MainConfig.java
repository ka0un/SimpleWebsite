package org.kasun.website.Config;

import org.bukkit.configuration.ConfigurationOptions;
import org.bukkit.configuration.ConfigurationSection;
import org.kasun.website.SimpleWebsite;

public class MainConfig {
    public String indexFile, keyStorePassword;
    public int port;
    public boolean useSSL;
    private ConfigurationSection config;
    private SimpleWebsite plugin;

    public MainConfig(SimpleWebsite plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();
        ConfigurationOptions options = config.getRoot().options();
        options.copyDefaults(true);
        plugin.saveConfig();
        loadConfig();
    }

    private void loadConfig() {
        indexFile = config.getString("indexFile");
        keyStorePassword = config.getString("keyStorePassword");
        port = config.getInt("port");
        useSSL = config.getBoolean("useSSL");
    }

}
