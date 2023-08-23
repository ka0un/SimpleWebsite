package org.kasun.website;

import org.kasun.website.Config.ConfigManager;
import org.kasun.website.Server.WebServerManager;

public class MainManager {
    public ConfigManager configManager;
    public WebServerManager webServerManager;

    public MainManager(){
        configManager = new ConfigManager();
        webServerManager = new WebServerManager();
    }

    // getters and setters
    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public WebServerManager getWebServerManager() {
        return webServerManager;
    }

    public void setWebServerManager(WebServerManager webServerManager) {
        this.webServerManager = webServerManager;
    }
}
