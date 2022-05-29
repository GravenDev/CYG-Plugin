package fr.gravencyg.actions.message;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.model.Plot;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarAction extends Action {

    public BossBarAction(CYG main) {
        super(main, CLevel.LEVEL9);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "SendBossBar()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Envoyer un message en bar de boss ";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        Plot plot = main.getDataManager().getPlotFromWorldLocationOf(player);

        if(plot.getMessageSet().containsKey(LocationUtils.fromLocToString(block.getLocation()))) {
            String messageLocName = plot.getMessageSet().get(LocationUtils.fromLocToString(block.getLocation()));
            String message = plot.getMessages().get(messageLocName)
                    .replace("&", "§");

            BarColor barColor = BarColor.BLUE;

            Location top = block.getLocation().clone().add(0, 1, 0);

            BossBar bossBar = Bukkit.createBossBar(message, barColor, BarStyle.SOLID);
            bossBar.setProgress(1);

            bossBar.addPlayer(player);
            main.getBarActions().add(bossBar);
        }
    }
}
