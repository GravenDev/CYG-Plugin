package fr.gravencyg.actions.inventory;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ClearAction extends Action {

    public ClearAction(CYG main) {
        super(main, CLevel.LEVEL1);
    }

    @Override
    public String title() {
        return ChatColor.BLUE +"Clear()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY +"Vider l'inventaire du joueur";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        player.getInventory().clear();
    }

}
