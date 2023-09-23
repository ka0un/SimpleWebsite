package org.kasun.website.Server;

import org.kasun.website.SimpleWebsite;
import org.kasun.website.Utils.FileUtils;

import java.io.File;

public class DefaultLandingCreator {
    private SimpleWebsite plugin = SimpleWebsite.getInstance();

    public DefaultLandingCreator() {

        FileUtils fileUtils = new FileUtils();
        File publicFolder = new File(plugin.getDataFolder() + "/public");
        if (!publicFolder.exists()) {
            plugin.getLogger().info("public folder not found, creating...");
            try{

                //creating public/default folder
                File defaultFolder = new File(plugin.getDataFolder() + "/public/default");
                publicFolder.mkdir();
                defaultFolder.mkdir();

                //copying default files
                fileUtils.copyFileFromResources("index_default.html", defaultFolder);
                fileUtils.copyFileFromResources("LICENSE.txt", defaultFolder);
                fileUtils.copyFileFromResources("minecraft.jpg", defaultFolder);
                fileUtils.copyFileFromResources("favicon.ico", defaultFolder);

                File imgFolder = new File(plugin.getDataFolder() + "/public/default/img");
                imgFolder.mkdir();

                fileUtils.copyFileFromResources("logo.png", imgFolder);
                fileUtils.copyFileFromResources("store.png", imgFolder);
                fileUtils.copyFileFromResources("vote.png", imgFolder);
                fileUtils.copyFileFromResources("forums.png", imgFolder);

                boolean renamed = fileUtils.renameFile(defaultFolder + "/index_default.html", "index.html");

                //fileUtils.copyFileFromResources("default.html", defaultFolder);
                //boolean renamed = fileUtils.renameFile(defaultFolder + "/default.html", "index.html");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
