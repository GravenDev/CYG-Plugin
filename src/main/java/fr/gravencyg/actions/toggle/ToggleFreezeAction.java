package fr.gravencyg.actions.toggle;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ToggleFreezeAction extends Action {

    public ToggleFreezeAction(CYG main) {
        super(main, CLevel.LEVEL3);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "ToggleFreeze()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Géler/Deleger un joueur";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        player.sendMessage("§bfreeze()");

        if(main.getGameManager().isPlayerInGame(player).size() != 0) {
            main.toggleGameFreeze(player);
        }
    }
}
