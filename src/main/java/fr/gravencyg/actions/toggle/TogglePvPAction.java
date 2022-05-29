package fr.gravencyg.actions.toggle;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CEvent;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class TogglePvPAction extends Action {

    public TogglePvPAction(CYG main) {
        super(main, CLevel.LEVEL1);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "TogglePvp()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Activer / Desactiver les combats";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        if(main.getGameManager().isPlayerInGame(player).size()!=0)
        {
            CYGame game = main.getGameManager().getCurrentGame(player);
            game.togglePvP();

            if(game.canFight()) {
                main.getGameManager().broadcast(game, "Combat Actif !");
            }
            else
            {
                main.getGameManager().broadcast(game, "Combat Inactif !");
            }
        }
        else
        {
            player.sendMessage("§eCombat Actif !");
        }
    }

}
