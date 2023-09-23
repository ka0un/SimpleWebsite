package org.kasun.website.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.kasun.website.SimpleWebsite;

import java.util.ArrayList;
import java.util.List;

public class SWCommand implements TabExecutor {

    private final SimpleWebsite plugin = SimpleWebsite.getInstance();
    private CommandsManager commandsManager;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if(!sender.hasPermission("sw-admin")) {
                sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.RED + "No Permission ! [sw-admin]");
                return true;
            }
            sendhelp(sender);
            return true;
        }

        //View Command
        if (args[0].equalsIgnoreCase("view")) {
            if(!sender.hasPermission("sw-admin")) {
                sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.RED + "No Permission ! [sw-admin]");
                return true;
            }

            WebsiteCommand websiteCommand = new WebsiteCommand();
            websiteCommand.onCommand(sender, cmd, label, args);

        }

        //Help Command
        if (args[0].equalsIgnoreCase("help")) {
            if(!sender.hasPermission("sw-admin")) {
                sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.RED + "No Permission ! [sw-admin]");
                return true;
            }
            sendhelp(sender);
        }

        //Reload Command
        if (args[0].equalsIgnoreCase("reload")) {
            if(!sender.hasPermission("sw-admin")) {
                sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.RED + "No Permission ! [sw-admin]");
                return true;
            }
            commandsManager = plugin.getMainManager().getCommandsManager();
            commandsManager.getMainManager().reload();
            sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "Webserver Restarted!");
            sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "Config Reloaded!");
            sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "Plugin Reloaded!");
        }
        return true;
    }

    private void sendhelp(CommandSender sender) {
        //Border
        sender.sendMessage(ChatColor.YELLOW + "============================================");

        sender.sendMessage(ChatColor.YELLOW + "");

        //Info
        sender.sendMessage(ChatColor.GREEN +  "SimpleWebsite v" + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.YELLOW + "Author: " + ChatColor.WHITE +  plugin.getDescription().getAuthors());
        sender.sendMessage(ChatColor.YELLOW + "Website: " + ChatColor.WHITE + plugin.getDescription().getWebsite());

        //discord
        sender.sendMessage(ChatColor.YELLOW + "Discord: "+ ChatColor.WHITE +" https://dsc.gg/sundevs");

        sender.sendMessage(ChatColor.YELLOW + "");

        //Commands
        sender.sendMessage(ChatColor.GOLD + "Commands:");
        sender.sendMessage(ChatColor.YELLOW + "/sw help "+ ChatColor.WHITE +"- View This info");
        sender.sendMessage(ChatColor.YELLOW + "/sw view "+ ChatColor.WHITE +"- View your website");
        sender.sendMessage(ChatColor.YELLOW + "/sw reload "+ ChatColor.WHITE +"- Reload the plugin");

        sender.sendMessage(ChatColor.YELLOW + "");
        //permissions
        sender.sendMessage(ChatColor.GOLD + "Permissions:");
        sender.sendMessage(ChatColor.YELLOW + "sw-admin "+ ChatColor.WHITE +"- Access to all commands");

        sender.sendMessage(ChatColor.YELLOW + "");

        //copyright text
        sender.sendMessage(ChatColor.YELLOW + "SimpleWebsite is licensed under the MIT License");
        sender.sendMessage(ChatColor.YELLOW + "SimpleWebsite@Sundevs 2023");

        sender.sendMessage(ChatColor.YELLOW + "");

        //Border
        sender.sendMessage(ChatColor.YELLOW + "============================================");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("help");
            arguments.add("view");
            arguments.add("reload");
            return arguments;
        }
        return null;
    }
}
