package fr.gravencyg.actions.basic;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.DaylightDetector;
import org.bukkit.entity.Player;

public class RestorePlayer extends Action {

    public RestorePlayer(CYG main) {
        super(main, CLevel.LEVEL12);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "Restore()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Soigne et remplit la faim";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        player.setHealth(20);
        player.setFoodLevel(20);
    }

}
