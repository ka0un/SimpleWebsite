package org.kasun.website.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasun.website.SimpleWebsite;

public class WebsiteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SimpleWebsite plugin = SimpleWebsite.getInstance();
        StringBuilder sb = new StringBuilder();
        String Domain;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            //should send the link that this website hosted on
            player.sendMessage("Website link: www.example.com");
        }else {
            plugin.getLogger().info("Website link: www.example.com");
        }
        return false;
    }
}
