package org.kasun.website.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.kasun.website.SimpleWebsite;

public class SWCommand implements CommandExecutor {

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
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "============================================");

        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "");

        //Info
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "SimpleWebsite v" + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "Author: " + plugin.getDescription().getAuthors());
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "Website: " + plugin.getDescription().getWebsite());

        //discord
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "Discord: https://dsc.gg/sundevs");

        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "");

        //Commands
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "Commands:");
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "/sw help - View This info");
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "/sw view - View your website");
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "/sw reload - Reload the plugin");

        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "");
        //permissions
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "Permissions:");
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "sw-admin - Access to all commands");

        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "");

        //copyright text
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "SimpleWebsite is licensed under the MIT License");
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "SimpleWebsite@Sundevs 2023");

        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "");

        //Border
        sender.sendMessage(ChatColor.GREEN + "[SimpleWebsite] " + ChatColor.YELLOW + "============================================");
    }


}
