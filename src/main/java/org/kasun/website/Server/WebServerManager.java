package org.kasun.website.Server;

public class WebServerManager {
    public WebServerManager(){
        //start web server

        WebServer webServer = new WebServer("default", 8081, true);
        webServer.start();
        WebServer webServer2 = new WebServer("web2", 8082, false);
        webServer2.start();
    }


}
