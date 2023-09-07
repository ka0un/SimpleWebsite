package org.kasun.website.Server;

import com.sun.net.httpserver.*;
import org.bukkit.plugin.Plugin;
import org.kasun.website.SimpleWebsite;
import org.kasun.website.Utils.FileUtils;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;

public class WebServer {
    private HttpServer server;
    private HttpsServer httpsServer;
    private Plugin plugin;
    private String name;
    private String websiteFolder;
    private static WebServer instance;
    private int port;
    private boolean useSSL;

    public WebServer(String websiteFolder, int port, boolean useSSL) {
        this.plugin = SimpleWebsite.getInstance();
        this.name = name;
        this.websiteFolder = websiteFolder;
        this.port = port;
        this.useSSL = useSSL;
        instance = this;

        if (useSSL) {
            setupSSL();
        }
    }

    private void setupSSL() {
        try {
            // Load your keystore here (replace "keystore.p12" and "keystore_password" with your values)
            char[] keystorePassword = "test".toCharArray();
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(plugin.getDataFolder() + "/keystore.p12"), keystorePassword);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, keystorePassword);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // Create HTTPS context and configurator
            HttpsConfigurator configurator = new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    SSLParameters sslParameters = getSSLContext().getDefaultSSLParameters();
                    params.setSSLParameters(sslParameters);
                }
            };

            httpsServer = HttpsServer.create(new InetSocketAddress(port), 0);
            httpsServer.setHttpsConfigurator(configurator);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        // Set up the HTTP/HTTPS server
        if(useSSL) {

            httpsServer.createContext("/", new WebsiteHandler(websiteFolder));
            httpsServer.setExecutor(null); // Use the default executor
            httpsServer.start();

        } else {
            try {
                server = HttpServer.create(new InetSocketAddress(port), 0);
                server.createContext("/", new WebsiteHandler(websiteFolder));
                server.setExecutor(null); // Use the default executor
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }
        if (httpsServer != null) {
            httpsServer.stop(0);
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





