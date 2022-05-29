package fr.gravencyg.actions.block;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class RegenMapAction extends Action {

    public RegenMapAction(CYG main) {
        super(main, CLevel.LEVEL5);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "RegenMap()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Régénerer les blocs de la partie";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        if(main.getGameManager().isPlayerInGame(player).size() != 0)
        {
            CYGame game = main.getGameManager().getCurrentGame(player);
            main.getGameManager().regenMap(game);
            main.getGameManager().broadcast(game, "§eRégéneration de la carte");
        }
        else{
            player.sendMessage("§eRegeneration de la carte !");
        }
    }
}
