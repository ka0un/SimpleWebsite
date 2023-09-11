package org.kasun.website.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasun.website.SimpleWebsite;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class WebsiteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        SimpleWebsite plugin = SimpleWebsite.getInstance();

        StringBuilder sb = new StringBuilder();
        String domain = plugin.getMainManager().getConfigManager().getMainConfig().domain;
        String http = "http://";

        if (domain.equals("localhost") || domain.equals("")) {
            try {
                URL url = new URL("https://api64.ipify.org?format=json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = reader.readLine();
                reader.close();
                domain = line.split(":")[1].split("\"")[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (plugin.getMainManager().getConfigManager().getMainConfig().useSSL){
            http = "https://";
        }

        sb.append(http);
        sb.append(domain);
        sb.append(":");
        sb.append(plugin.getMainManager().getConfigManager().getMainConfig().port);

        if (sender instanceof Player) {
            Player player = (Player) sender;
            try{
                Desktop.getDesktop().browse(new URI(sb.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eWebsite : &f" + sb));
        }else {
            plugin.getLogger().info(sb.toString());
        }
        return true;

    }
}
