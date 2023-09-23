package org.kasun.website;

import org.kasun.website.Config.ConfigManager;
import org.kasun.website.Server.DefaultLandingCreator;
import org.kasun.website.Server.WebServerManager;
import org.kasun.website.Utils.UpdateChecker;

public class MainManager {
    public ConfigManager configManager;
    public WebServerManager webServerManager;
    public DefaultLandingCreator defaultLandingCreator;

    public MainManager(){
        configManager = new ConfigManager();
        defaultLandingCreator = new DefaultLandingCreator();
        webServerManager = new WebServerManager();
        UpdateChecker updateChecker = new UpdateChecker(SimpleWebsite.getInstance(), "https://raw.githubusercontent.com/ka0un/SimpleWebsite/master/ver.txt", SimpleWebsite.getInstance().getDescription().getVersion());
    }

    // getters and setters
    public ConfigManager getConfigManager() {
        return configManager;
    }

}
