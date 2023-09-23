package org.kasun.website.Server;

import com.sun.net.httpserver.*;
import org.bukkit.plugin.Plugin;
import org.kasun.website.Config.MainConfig;
import org.kasun.website.SimpleWebsite;
import org.kasun.website.Utils.FileUtils;
import org.kasun.website.Utils.HandleError;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.CertificateException;

public class WebServer {
    private HttpServer server;
    private HttpsServer httpsServer;
    private final Plugin plugin;
    private String name;
    private final String websiteFolder;
    private static WebServer instance;
    private int port;
    private boolean useSSL, blockStart = false;
    private String indexFile, keyStorePassword;

    private MainConfig mainConfig;

    private boolean apiOnly;

    public WebServer(String websiteFolder, String indexFile, int port, boolean useSSL, String keyStorePassword) {
        this.plugin = SimpleWebsite.getInstance();
        this.name = name;
        this.websiteFolder = websiteFolder;
        this.port = port;
        this.useSSL = useSSL;
        this.indexFile = indexFile;
        this.keyStorePassword = keyStorePassword;
        instance = this;
        mainConfig = new MainConfig(SimpleWebsite.getInstance());
        apiOnly = mainConfig.apiOnly;


        if (useSSL) {
            setupSSL();
        }
    }


    private void setupSSL() {

        try {
            char[] keystorePassword = keyStorePassword.toCharArray();
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try{
                keyStore.load(Files.newInputStream(Paths.get(plugin.getDataFolder() + "/keystore.p12")), keystorePassword);
            }catch (Exception e){
                e.printStackTrace();
                plugin.getLogger().warning("==============================================================");
                plugin.getLogger().warning("[!] keystore.p12 issue found, please check the file and try again.");
                plugin.getLogger().warning("or disable SSL in config.yml");
                plugin.getLogger().warning("Possible Reasons:");
                if (e instanceof CertificateException){
                    plugin.getLogger().warning(" - Certificate in keystore Could not be loaded");
                }else if (e instanceof IOException) {
                    plugin.getLogger().warning(" - keystore.p12 file could not be found");
                    plugin.getLogger().warning(" - keystore.p12 file could not be opened");
                    plugin.getLogger().warning(" - wrong keystore password in config.yml");
                }else{
                    plugin.getLogger().warning(" - Incorrect Keystore Algorithm");
                    plugin.getLogger().warning(" - unknown error");
                }
                plugin.getLogger().warning("==============================================================");
                blockStart = true;
                return;
            }



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
            HandleError.handleError("WebServer-127");
        }
    }

    public void start() {
        if (blockStart) {
            return;
        }
        // Check if the example HTML file needs to be created
        File exampleHtml = new File(plugin.getDataFolder() + "/public/" + websiteFolder + "/" + indexFile);

        if (!exampleHtml.exists()) {
            plugin.getLogger().info(indexFile + " not found, creating..." + exampleHtml);
            FileUtils fileUtils = new FileUtils();

            try {
                File webFolder = new File(plugin.getDataFolder() + "/public/" + websiteFolder);
                webFolder.mkdir();
                File destinationFolder = new File(plugin.getDataFolder() + "/public/" + websiteFolder);
                fileUtils.copyFileFromResources("index.html", destinationFolder);
                boolean renamed = fileUtils.renameFile(destinationFolder + "/index.html", indexFile);
            } catch (Exception e) {
                e.printStackTrace();
                HandleError.handleError("WebServer-147");
            }
        }

        // Set up the HTTP/HTTPS server
        if(useSSL) {

            httpsServer.createContext("/", new WebsiteHandler(websiteFolder,"index.html", plugin, apiOnly));
            httpsServer.setExecutor(null); // Use the default executor
            httpsServer.start();

        } else {
            try {
                server = HttpServer.create(new InetSocketAddress(port), 0);
                server.createContext("/", new WebsiteHandler(websiteFolder,"index.html", plugin, apiOnly));
                server.setExecutor(null); // Use the default executor
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
                HandleError.handleError("WebServer-163");
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

}







