package org.kasun.website.Commands;

import org.kasun.website.MainManager;
import org.kasun.website.SimpleWebsite;

public class CommandsManager {

    private final MainManager mainManager;

    public CommandsManager(MainManager mainManager) {
        this.mainManager = mainManager;
        SimpleWebsite plugin = SimpleWebsite.getInstance();
        plugin.getCommand("sw").setExecutor(new SWCommand());
        plugin.getCommand("website").setExecutor(new WebsiteCommand());
    }

    public MainManager getMainManager() {
        return mainManager;
    }
}