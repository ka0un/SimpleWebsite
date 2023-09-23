package org.kasun.website;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.kasun.website.Commands.CommandsManager;
import org.kasun.website.Config.ConfigManager;
import org.kasun.website.Server.DefaultLandingCreator;
import org.kasun.website.Server.WebServerManager;
import org.kasun.website.Utils.UpdateChecker;

public class MainManager {
    public ConfigManager configManager;
    public WebServerManager webServerManager;
    public DefaultLandingCreator defaultLandingCreator;
    public CommandsManager commandsManager;
    private SimpleWebsite plugin;

    public MainManager(){
        plugin = SimpleWebsite.getInstance();
        configManager = new ConfigManager();
        commandsManager = new CommandsManager(this);
        defaultLandingCreator = new DefaultLandingCreator();
        webServerManager = new WebServerManager();
        UpdateChecker updateChecker = new UpdateChecker(SimpleWebsite.getInstance(), "https://raw.githubusercontent.com/ka0un/SimpleWebsite/master/ver.txt", SimpleWebsite.getInstance().getDescription().getVersion());
    }

    public void reload() {


        webServerManager.getWebServer().stop();
        HandlerList.unregisterAll(plugin);
        Bukkit.getScheduler().cancelTasks(plugin);
        setConfigManager(new ConfigManager());
        setCommandsManager(new CommandsManager(this));
        setDefaultLandingCreator(new DefaultLandingCreator());
        setWebServerManager(new WebServerManager());

    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void setWebServerManager(WebServerManager webServerManager) {
        this.webServerManager = webServerManager;
    }

    public void setDefaultLandingCreator(DefaultLandingCreator defaultLandingCreator) {
        this.defaultLandingCreator = defaultLandingCreator;
    }

    public void setCommandsManager(CommandsManager commandsManager) {
        this.commandsManager = commandsManager;
    }





    // getters and setters
    public ConfigManager getConfigManager() {
        return configManager;
    }

    public WebServerManager getWebServerManager() {
        return webServerManager;
    }

    public DefaultLandingCreator getDefaultLandingCreator() {
        return defaultLandingCreator;
    }

    public CommandsManager getCommandsManager() {
        return commandsManager;
    }

    public SimpleWebsite getPlugin() {
        return plugin;
    }
}
