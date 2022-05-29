package fr.gravencyg.actions.message;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.model.Plot;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.Map;

public class ResetBossBarAction extends Action {

    public ResetBossBarAction(CYG main) {
        super(main, CLevel.LEVEL9);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "ResetBossBar()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Supprime toutes les bars de boss du joueur";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        for(BossBar bossBarEntry : main.getBarActions())
        {
            if(bossBarEntry.getPlayers().contains(player))
            {
                bossBarEntry.removeAll();
            }
        }
    }
}
