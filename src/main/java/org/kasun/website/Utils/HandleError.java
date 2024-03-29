package org.kasun.website.Utils;

import org.kasun.website.SimpleWebsite;

public class HandleError {
    private static SimpleWebsite plugin;
    public static void handleError(String code){
        plugin = SimpleWebsite.getInstance();
        plugin.getLogger().severe("=========================================================");
        plugin.getLogger().severe("An error occurred.");
        plugin.getLogger().severe("Error Code : " + code);
        plugin.getLogger().severe("Please check the error and report it to the developer.");
        plugin.getLogger().severe("Discord Server : https://dsc.gg/sundevs");
        plugin.getLogger().severe("=========================================================");
    }
}
