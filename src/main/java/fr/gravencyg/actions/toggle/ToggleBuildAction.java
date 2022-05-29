package fr.gravencyg.actions.toggle;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ToggleBuildAction extends Action {

    public ToggleBuildAction(CYG main) {
        super(main, CLevel.LEVEL4);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "ToggleBuild()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Activer / Desactiver la construction";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        if(main.getGameManager().isPlayerInGame(player).size() != 0)
        {
            CYGame game = main.getGameManager().getCurrentGame(player);
            game.toggleBuild();

            if(game.canFight()) {
                main.getGameManager().broadcast(game, "Construction Active !");
            }
            else
            {
                main.getGameManager().broadcast(game, "Construction Inactif !");
            }
        }
        else
        {
            player.sendMessage("§eConstruction Active !");
        }
    }


}
