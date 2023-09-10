package org.kasun.website.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.kasun.website.Utils.PlaceholderAPIIntegration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class WebsiteHandler implements HttpHandler {
    private String webfolder;
    private String indexFile;
    private Plugin plugin;

    public WebsiteHandler(String webfolder, String indexFile, Plugin plugin) {
        this.webfolder = webfolder;
        this.indexFile = indexFile;
        this.plugin = plugin;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();

        if (requestPath.startsWith("/api/random/")) {
            handleRandomApi(exchange);
        }else if (requestPath.startsWith("/api/status/")) {
            handleStatusApi(exchange);
        }else if (requestPath.startsWith("/api/online/")) {
            handleOnlineApi(exchange);
        }else if (requestPath.startsWith("/api/max/")) {
            handleMaxApi(exchange);
        }else if (requestPath.startsWith("/api/players/")){
            handlePlayers(exchange);
        }else if (requestPath.startsWith("/api/placeholder/")) {
            handlePlaceholderApi(exchange);
        }else if(requestPath.startsWith("/api/")){
            String response = "Invalid API endpoint";
            exchange.sendResponseHeaders(404, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }else{
            serveFiles(exchange);
        }




    }

    private void serveFiles(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();

        if (requestPath.equals("/")) {
            requestPath = indexFile; // Default file to serve
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

    private void handleRandomApi(HttpExchange exchange) throws IOException {
        Random random = new Random();
        int randomInt = random.nextInt(1000);
        // Set the response content type to JSON
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            // Return the random number as JSON
            String jsonResponse = "{\"value\": " + randomInt + "}";
            os.write(jsonResponse.getBytes());
        }
    }

    private void handlePlaceholderApi(HttpExchange exchange) throws IOException {

        // Parse the length parameter from the URL
        String requestPath = exchange.getRequestURI().getPath();
        String id = requestPath.substring("/api/placeholder/".length());

        if (id.equals("") || id == null) {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(400, 0);

            try (OutputStream os = exchange.getResponseBody()) {
                // Return the random number as JSON
                String jsonResponse = "{\"error\": \"No placeholder id provided\", \"example\": \"/api/placeholder/server_online\"}";
                os.write(jsonResponse.getBytes());
            }
        }

        String placeholderValue = PlaceholderAPIIntegration.getPlaceholderValue(id);

        // Set the response content type to JSON
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            String jsonResponse = "{\"value\": " + placeholderValue + "}";
            os.write(jsonResponse.getBytes());
        }
    }

    private void handlePlayers(HttpExchange exchange) throws IOException {

        // Parse the length parameter from the URL
        String requestPath = exchange.getRequestURI().getPath();
        String type = requestPath.substring("/api/players/".length());
        StringBuilder jsonResponse = new StringBuilder("");
        int responseCode = 200;

        if (type.equalsIgnoreCase("online/")) {
            Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
            if (players.length == 0) {
                jsonResponse.append("{\"error\": \"No players online\"}");
                responseCode = 400;
            }else{
                for (Player player : players) {
                    jsonResponse.append("{\n")
                            .append("    \"name\": \"").append(player.getName()).append("\",\n")
                            .append("    \"uuid\": \"").append(player.getUniqueId()).append("\"\n")
                            .append("},\n");
                }
            }
        }else if (type.equalsIgnoreCase("offline/")) {
            OfflinePlayer[] players = Bukkit.getOfflinePlayers();
            for (OfflinePlayer player : players) {
                jsonResponse.append("{\n")
                        .append("    \"name\": \"").append(player.getName()).append("\",\n")
                        .append("    \"uuid\": \"").append(player.getUniqueId()).append("\"\n")
                        .append("},\n");
            }
        }else{
            jsonResponse.append("{\"error\": \"Invalid type provided\", \"example\": \"/api/players/online/\"}");
            responseCode = 400;
        }

        // Set the response content type to JSON
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(responseCode, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            String Response = jsonResponse.toString();
            os.write(Response.getBytes());
        }
    }

    private void handleOnlineApi(HttpExchange exchange) throws IOException {
        Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
        int onlinePlayers = players.length;
        // Set the response content type to JSON
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            // Return the random number as JSON
            String jsonResponse = "{\"value\": " + onlinePlayers + "}";
            os.write(jsonResponse.getBytes());
        }
    }

    private void handleMaxApi(HttpExchange exchange) throws IOException {
        int maxPlayers = Bukkit.getServer().getMaxPlayers();
        // Set the response content type to JSON
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            // Return the random number as JSON
            String jsonResponse = "{\"value\": " + maxPlayers + "}";
            os.write(jsonResponse.getBytes());
        }
    }

    private void handleStatusApi(HttpExchange exchange) throws IOException {
        String status = Bukkit.getServer().getMotd();
        // Set the response content type to JSON
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            // Return the random number as JSON
            String jsonResponse = "{\"value\": \"" + status + "\"}";
            os.write(jsonResponse.getBytes());
        }
    }





}

