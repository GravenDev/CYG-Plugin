package fr.gravencyg.listeners;

import fr.gravencyg.CYG;
import fr.gravencyg.utils.CRank;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatListener implements Listener {

    private CYG main;

    public ChatListener(CYG main) {
        this.main = main;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        String message = event.getMessage().replace("%","");

        // Set join message
        CRank rank = CRank.JOUEUR;
        boolean verifyGame = false;
        int level = 0;
        int trophys = 0;

        rank = main.getDataManager().getRank(player);
        level =  main.getDataManager().getLevel(player.getName());
        trophys = main.getDataManager().getTrophys(player.getName());

        String msg = rank.getColor() + ""
                + rank.getName() + " §r| Lvl "
                + rank.getColor()
                + level
                + " §r- §r §e"
                + trophys
                + " T §r|§r "
                + player.getName();

        msg += " §7>> " + rank.getColor() + "" + message;

        event.setFormat(msg);

    }


}
