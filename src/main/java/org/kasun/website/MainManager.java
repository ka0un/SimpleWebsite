package org.kasun.website;

import org.kasun.website.Config.ConfigManager;
import org.kasun.website.Server.WebServerManager;
import org.kasun.website.Utils.UpdateChecker;

public class MainManager {
    public ConfigManager configManager;
    public WebServerManager webServerManager;
    private UpdateChecker updateChecker;

    public MainManager(){
        configManager = new ConfigManager();
        webServerManager = new WebServerManager();
        updateChecker = new UpdateChecker(SimpleWebsite.getInstance(), "https://raw.githubusercontent.com/ka0un/SimpleWebsite/master/ver.txt", SimpleWebsite.getInstance().getDescription().getVersion());
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
