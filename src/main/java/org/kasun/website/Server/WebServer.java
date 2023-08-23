package org.kasun.website.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.bukkit.plugin.Plugin;
import org.kasun.website.SimpleWebsite;
import org.kasun.website.Utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WebServer {
    private HttpServer server;
    private Plugin plugin;
    private String name;
    private String websiteFolder;
    private static WebServer instance;
    private int port;

    public WebServer(String websiteFolder, int port) {
        this.plugin = SimpleWebsite.getInstance();
        this.name = name;
        this.websiteFolder = websiteFolder;
        this.port = port;
        instance = this;
    }
    public void start() {

        // Check if the example HTML file needs to be created
        File exampleHtml = new File(plugin.getDataFolder() + "/public/" + websiteFolder + "/index.html");

        if (!exampleHtml.exists()) {
            System.out.println("index.html not found, creating..." + exampleHtml);
            FileUtils fileUtils = new FileUtils();

            try {
                File webFolder = new File(plugin.getDataFolder() + "/public/" + websiteFolder);
                webFolder.mkdir();
                File destinationFolder = new File(plugin.getDataFolder() + "/public/" + websiteFolder);
                fileUtils.copyFileFromResources("index.html", destinationFolder);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // Set up the HTTP server
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new WebsiteHandler(websiteFolder));
            server.setExecutor(null); // Use the default executor
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

        if (server != null) {
            server.stop(0);
        }
    }

    private class WebsiteHandler implements HttpHandler {
        String webfolder;

        public WebsiteHandler(String webfolder) {
            this.webfolder = webfolder;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestPath = exchange.getRequestURI().getPath();
            if (requestPath.equals("/")) {
                requestPath = "index.html"; // Default file to serve
            }

            String websitefilepath = plugin.getDataFolder() + "/public/" + webfolder + "/" + requestPath;

            File websiteFile = new File(websitefilepath);

            if (websiteFile.exists() && !websiteFile.isDirectory()) {
                byte[] fileBytes = Files.readAllBytes(Paths.get(websiteFile.getAbsolutePath()));

                exchange.sendResponseHeaders(200, fileBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(fileBytes);
                }
            } else {
                String response = "File not found";
                exchange.sendResponseHeaders(404, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }
}
