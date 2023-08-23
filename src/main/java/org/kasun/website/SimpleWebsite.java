package org.kasun.website;

import org.bukkit.plugin.java.JavaPlugin;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleWebsite extends JavaPlugin {

    private HttpServer server;
    private static SimpleWebsite instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Website Plugin has been enabled!");
        MainManager mainManager = new MainManager();

    }

    @Override
    public void onDisable() {
        getLogger().info("Website Plugin has been disabled!");

    }

    public static SimpleWebsite getInstance() {
        return instance;
    }

}

