package org.kasun.website.Utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderAPIIntegration {

    public static String getPlaceholderValue(String id) {

        if (Bukkit.getServer().getOnlinePlayers().isEmpty()){
            OfflinePlayer[] players = Bukkit.getOfflinePlayers();
            return PlaceholderAPI.setPlaceholders(players[0], "%" + id + "%");
        }else{
            Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
            return PlaceholderAPI.setPlaceholders(players[0], "%" + id + "%");
        }

    }
}
