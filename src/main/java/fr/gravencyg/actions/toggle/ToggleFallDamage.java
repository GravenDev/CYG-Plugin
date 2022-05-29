package fr.gravencyg.actions.toggle;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ToggleFallDamage extends Action {

    public ToggleFallDamage(CYG main) {
        super(main, CLevel.LEVEL3);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "ToggleFallDamage()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Activer / Desactiver dégats de chute";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        if(main.getGameManager().isPlayerInGame(player).size() != 0)
        {
            CYGame game = main.getGameManager().getCurrentGame(player);
            game.toggleFallDamage();

            if(game.canFight()) {
                main.getGameManager().broadcast(game, "Dégat de chute Active !");
            }
            else
            {
                main.getGameManager().broadcast(game, "Dégat de chute Inactif !");
            }
        }
        else
        {
            player.sendMessage("§eDegat de chute Active !");
        }
    }
}
