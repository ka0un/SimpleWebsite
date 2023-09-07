package org.kasun.website.Server;

import org.bukkit.plugin.Plugin;
import org.kasun.website.Config.MainConfig;
import org.kasun.website.SimpleWebsite;

public class WebServerManager {
    WebServer ws;

    public WebServerManager(){
        SimpleWebsite plugin = SimpleWebsite.getInstance();
        MainConfig mainConfig = new MainConfig(plugin);

        ws = new WebServer("default", mainConfig.indexFile, mainConfig.port, mainConfig.useSSL, mainConfig.keyStorePassword);
        ws.start();

    }

    public WebServer getWebServer() {
        return ws;
    }


}
